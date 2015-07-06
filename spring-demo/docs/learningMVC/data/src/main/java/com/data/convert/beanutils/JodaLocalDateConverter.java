package com.springdemo.learningMVC.data.src.main.java.com.data.convert.beanutils;

import org.joda.time.LocalDate;

public class JodaLocalDateConverter extends JodaDateTimeConverter {

    public JodaLocalDateConverter() {
        super();
    }

    public JodaLocalDateConverter(LocalDate defaultValue) {
        super(defaultValue);
    }

    @Override
    protected Class<?> getDefaultType() {
        return LocalDate.class;
    }
}
