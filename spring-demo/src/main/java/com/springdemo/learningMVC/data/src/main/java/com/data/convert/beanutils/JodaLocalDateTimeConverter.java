package com.springdemo.learningMVC.data.src.main.java.com.data.convert.beanutils;

import org.joda.time.LocalDateTime;

public class JodaLocalDateTimeConverter extends JodaDateTimeConverter {

    protected JodaLocalDateTimeConverter() {
        super();
    }

    protected JodaLocalDateTimeConverter(Object defaultValue) {
        super(defaultValue);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalDateTime.class;
    }
}
