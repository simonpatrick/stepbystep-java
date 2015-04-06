
package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.Range;


public class RandomIntSupplier extends RandomNumberSupplier<Integer> {

    public RandomIntSupplier(Range<Integer> range) {
        super(range);
    }

    public RandomIntSupplier(Integer min, Integer max) {
        super(min, max);
    }

    @Override
    public Integer randomNext(Integer min, Integer max) {
        return (int) (Math.floor(Math.random() * (max - min)) + min);
    }
}
