package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.Range;


public class RandomDoubleSupplier extends RandomNumberSupplier<Double> {

    public RandomDoubleSupplier(Range<Double> range) {
        super(range);
    }

    public RandomDoubleSupplier(Double min, Double max) {
        super(min, max);
    }

    @Override
    public Double randomNext(Double min, Double max) {
        return Math.floor(Math.random() * (max - min)) + min;
    }
}
