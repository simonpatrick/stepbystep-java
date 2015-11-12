package com.hedwig.stepbystep.javatutorial.jackson.field;

public class MyDtoWithGetter {

    private String stringValue;
    private int intValue;

    public MyDtoWithGetter() {
        super();
    }

    public MyDtoWithGetter(final String stringValue, final int intValue) {
        super();

        this.stringValue = stringValue;
        this.intValue = intValue;
    }

    // API

    public String getStringValue() {
        return stringValue;
    }

}
