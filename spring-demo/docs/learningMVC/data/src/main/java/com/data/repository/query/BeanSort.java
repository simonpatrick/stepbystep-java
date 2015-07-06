package com.springdemo.learningMVC.data.src.main.java.com.data.repository.query;

import org.apache.commons.beanutils.PropertyUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.Collator;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

public class BeanSort {

    // chinese text comparator
    private static final Comparator<Object> DEFAULT_COMPARATOR = Collator.getInstance(Locale.CHINESE);

    public static <T> BeanComparator<T> beanComparator(Sort sort) {
        return new BeanComparator<>(sort);
    }

    public static class BeanComparator<T> implements Comparator<T> {
        private final Sort sort;
        private final Comparator<Object> defaultComparator;

        public BeanComparator(Sort sort) {
            this(sort, DEFAULT_COMPARATOR);
        }

        public BeanComparator(Sort sort, Comparator<Object> defaultComparator) {
            this.sort = sort;
            this.defaultComparator = defaultComparator;
        }

        @Override
        public int compare(T o1, T o2) {
            if (o1 == null || o2 == null) {
                return -1;
            }

            Iterator<Sort.Order> orderIterator = sort.iterator();
            Object value1, value2;

            int modulus, result;
            while (orderIterator.hasNext()) {
                Sort.Order order = orderIterator.next();

                modulus = order.getDirection() == Sort.Direction.ASC ? 1 : -1;

                value1 = getFieldValue(o1, order.getProperty());
                value2 = getFieldValue(o2, order.getProperty());

                if (value1 == null) {
                    if (value2 != null) {
                        return -1;
                    }
                } else {
                    if (value2 == null) {
                        return 1;
                    } else {
                        result = compareValue(value1, value2, modulus);
                        if (result != 0) {
                            return result;
                        }
                    }
                }
            }
            return 0;
        }

        private Object getFieldValue(Object javaBean, String propName) {
            try {
                return PropertyUtils.getProperty(javaBean, propName);
            } catch (Exception ex) {
                return null;
            }
        }

        private int compareValue(Object value1, Object value2, int modulus) {
            Class<?> o1Class = value1.getClass(), o2Class = value2.getClass();
            if (value1 instanceof Number && value2 instanceof Number) {
                if ((o1Class == Integer.class && o2Class == Integer.class) ||
                        (o1Class == Long.class && o2Class == Long.class) ||
                        (o1Class == Short.class && o2Class == Short.class) ||
                        (o1Class == Byte.class && o2Class == Byte.class)) {
                    return (int) (modulus * (((Number) value1).longValue() - ((Number) value2).longValue()));
                } else if (o1Class == BigDecimal.class && o2Class == BigDecimal.class) {
                    return modulus * ((BigDecimal) value1).subtract((BigDecimal) value2).intValue();
                } else if (o1Class == BigInteger.class && o2Class == BigInteger.class) {
                    return modulus * ((BigInteger) value2).subtract((BigInteger) value2).intValue();
                } else if (o1Class == Double.class && o2Class == Double.class) {
                    return modulus * ((Double) value1).compareTo((Double) value2);
                } else if (o1Class == Float.class && o2Class == Float.class) {
                    return modulus * ((Float) value1).compareTo((Float) value2);
                } else {
                    return modulus * (((Number) value1).intValue() - ((Number) value2).intValue());
                }
            } else if (value1 instanceof CharSequence && value2 instanceof CharSequence) {
                return modulus * (String.valueOf(value1).compareTo(String.valueOf(value2)));
            } else if (value1 instanceof DateTime && value2 instanceof DateTime) {
                return modulus * ((DateTime) value1).compareTo((DateTime) value2);
            } else if (value1 instanceof Date && value2 instanceof Date) {
                return modulus * (((Date) value1).compareTo((Date) value2));
            } else {
                return modulus * defaultComparator.compare(value1, value2);
            }
        }

        public Sort getSort() {
            return sort;
        }

        public Comparator<?> getDefaultComparator() {
            return defaultComparator;
        }
    }
}
