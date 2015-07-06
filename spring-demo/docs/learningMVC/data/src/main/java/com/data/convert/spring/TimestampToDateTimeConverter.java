package com.springdemo.learningMVC.data.src.main.java.com.data.convert.spring;

import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;

import java.sql.Timestamp;

public enum TimestampToDateTimeConverter implements Converter<Timestamp, DateTime> {

    INSTANCE;

    public static TimestampToDateTimeConverter getInstance() {
        return INSTANCE;
    }

    @Override
    public DateTime convert(Timestamp source) {
        return source == null ? null : new DateTime(source);
    }
}