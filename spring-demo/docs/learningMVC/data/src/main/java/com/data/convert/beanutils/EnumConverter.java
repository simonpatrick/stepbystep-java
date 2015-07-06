package com.springdemo.learningMVC.data.src.main.java.com.data.convert.beanutils;

import com.common.base.EnumValue;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EnumConverter<E extends Enum<E>> implements Converter {

    private transient Logger logger;
    private final E defaultValue;
    private final E[] enums;

    public EnumConverter(Class<E> enumClass, E defaultValue) {
        this.defaultValue = defaultValue;
        this.enums = enumClass.getEnumConstants();
    }

    Logger getLogger() {
        if (logger == null) {
            logger = LoggerFactory.getLogger(getClass());
        }
        return logger;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T convert(Class<T> type, Object value) {
        if (value == null) {
            return defaultValue != null ? type.cast(defaultValue) : null;
        }
        if (value instanceof Number) {
            Number code = (Number) value;

            if (isEnumValue(type)) {
                for (E e : enums) {
                    if (((EnumValue<Number>) e).getValue().intValue() == code.intValue()) {
                        return type.cast(e);
                    }
                }
            } else {
                for (E e : enums) {
                    if (e.ordinal() == code.intValue()) {
                        return type.cast(e);
                    }
                }
            }
        } else if (value instanceof String) {
            String name = (String) value;

            for (E e : enums) {
                if (e.name().equals(name)) {
                    return type.cast(e);
                }
            }
        }
        String msg = String.format("%s cannot handle conversion to '%s'",
                getClass().getSimpleName(), type);
        if (getLogger().isWarnEnabled()) {

            getLogger().warn("    {}", msg);
        }

        throw new ConversionException(msg);
    }

    private boolean isEnumValue(Class<?> type) {
        Class<?>[] interfaces = type.getInterfaces();
        if (interfaces == null || interfaces.length == 0) {
            return false;
        }
        for (Class<?> i : interfaces) {
            if (EnumValue.class.equals(i)) {
                return true;
            }
        }
        return false;
    }
}
