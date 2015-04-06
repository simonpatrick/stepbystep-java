package com.springdemo.learningMVC.common.src.main.java.com.common.base;

/**
 * Gets random Short value.
 */
public class RandomShortSupplier extends RandomNumberSupplier<Short> {

    public RandomShortSupplier(Short min, Short max) {
        super(min, max);
    }

    @Override
    public Short randomNext(Short min, Short max) {
        return (short) (Math.floor(Math.random() * (max - min)) + min);
    }
}
