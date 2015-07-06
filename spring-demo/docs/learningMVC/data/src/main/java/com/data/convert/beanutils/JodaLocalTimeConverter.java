package com.springdemo.learningMVC.data.src.main.java.com.data.convert.beanutils;

import org.joda.time.LocalTime;

public class JodaLocalTimeConverter extends JodaDateTimeConverter {

    public JodaLocalTimeConverter() {
        super();
    }

    public JodaLocalTimeConverter(LocalTime defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalTime.class;
    }
}
