package com.springdemo.learningMVC.data.src.main.java.com.data.convert.spring;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

public enum LongToDateTimeConverter implements Converter<Long, DateTime> {
    INSTANCE;

    /**
     * Returns a {@code LongToDateTimeConverter} instance.
     */
    public static LongToDateTimeConverter getInstance() {
        return INSTANCE;
    }

    /**
     * Convert the source of type S to target type T.
     *
     * @param source the source object to convert, which must be an instance of S
     * @return the converted object, which must be an instance of T
     * @throws IllegalArgumentException if the source could not be converted to the desired target type
     */
    @Override
    public DateTime convert(Long source) {
        return source != null && source > 0 ? new DateTime(source) : null;
    }
}