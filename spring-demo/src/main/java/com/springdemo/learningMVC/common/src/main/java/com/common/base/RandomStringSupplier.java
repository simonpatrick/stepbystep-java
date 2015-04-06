package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.collect.Range;

import java.util.function.Supplier;

/**
 */
public class RandomStringSupplier implements Supplier<String> {

    private final RandomIntSupplier length;
    private final boolean letters;
    private final boolean numbers;

    public RandomStringSupplier(Range<Integer> lengthRange, boolean letters, boolean numbers) {
        this.length = new RandomIntSupplier(lengthRange);
        this.letters = letters;
        this.numbers = numbers;
    }

    public RandomStringSupplier(int minLen, int maxLen, boolean letters, boolean numbers) {
        this.length = new RandomIntSupplier(minLen, maxLen);
        this.letters = letters;
        this.numbers = numbers;
    }

    @Override
    public String get() {
        return RandomStrings.random(length.get(), letters, numbers);
    }
}
