package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.Range;

public class RandomLongSupplier extends RandomNumberSupplier<Long> {

    public RandomLongSupplier(Range<Long> range) {
        super(range);
    }

    public RandomLongSupplier(Long min, Long max) {
        super(min, max);
    }

    @Override
    public Long randomNext(Long min, Long max) {
        return (long) (Math.floor(Math.random() * (max - min)) + min);
    }
}
