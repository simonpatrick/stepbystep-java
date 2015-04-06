package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.Range;


public class RandomFloatSupplier extends RandomNumberSupplier<Float> {

    public RandomFloatSupplier(Range<Float> range) {
        super(range);
    }

    public RandomFloatSupplier(Float min, Float max) {
        super(min, max);
    }

    @Override
    public Float randomNext(Float min, Float max) {
        return (float) Math.floor(Math.random() * (max - min)) + min;
    }
}
