
package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.Range;

import java.util.function.Supplier;


public abstract class RandomNumberSupplier<N extends Number & Comparable<N>>
        implements Supplier<N> {

    private final Range<N> range;

    public RandomNumberSupplier(Range<N> range) {
        this.range = range;
    }

    public RandomNumberSupplier(N min, N max) {
        if (min.compareTo(max) == 1) {
            this.range = Range.closed(max, min);
        } else {
            this.range = Range.closed(min, max);
        }
    }

    @Override
    public N get() {
        N min = range.lowerEndpoint();
        N max = range.upperEndpoint();
        return randomNext(min, max);
    }

    public abstract N randomNext(N min, N max);
}
