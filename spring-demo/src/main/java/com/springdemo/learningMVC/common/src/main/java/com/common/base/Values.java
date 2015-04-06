package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.ImmutableList;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.IllegalFormatFlagsException;
import java.util.List;
import java.util.Objects;

public interface Values {
    List<String> BOOL_TRUE_SIGN = ImmutableList.of("true","1","yes","on","y");
    List<String> BOOL_FALSE_SIGN= ImmutableList.of("false","0","no","off","n");

    //todo learning java 8  lambada
    /**
     * null value instance of Values.
     */
    Values NULL_VALUES = ()->null;

    String getValue();
    default Long longValue() {
        return getValue() != null ? Long.valueOf(getValue()) : null;
    }

    default Integer toInt() {
        return getValue() != null ? Integer.parseInt(getValue()) : null;
    }

    default Short toShort() {
        return getValue() != null ? Short.parseShort(getValue()) : null;
    }

    default Byte toByte() {
        return getValue() != null ? Byte.parseByte(getValue()) : null;
    }

    default BigDecimal toDecimal() {
        return getValue() != null ? new BigDecimal(getValue()) : null;
    }

    default BigInteger toBigInt() {
        return getValue() != null ? new BigInteger(getValue()) : null;
    }

    default Boolean toBool() {
        if (getValue() == null) {
            return null;
        }
        final String v = getValue().toLowerCase();
        if (BOOL_TRUE_SIGN.contains(v)) {
            return Boolean.TRUE;
        } else if (BOOL_FALSE_SIGN.contains(v)) {
            return Boolean.FALSE;
        }
        throw new IllegalFormatFlagsException(getValue());
    }

    default DateTime toDateTime(String pattern) {
        return toDateTime(DateTimeFormat.forPattern(pattern));
    }

    default DateTime toDateTime(DateTimeFormatter formatter) {
        if (getValue() == null) {
            return null;
        }
        return DateTime.parse(getValue(), formatter);
    }

    default <E extends Enum<E>> E toEnum(Class<E> enumClass) {
        Objects.requireNonNull(enumClass, "enumClass");
        if (getValue() == null) {
            return null;
        }
        return Enum.valueOf(enumClass, getValue());
    }
}
