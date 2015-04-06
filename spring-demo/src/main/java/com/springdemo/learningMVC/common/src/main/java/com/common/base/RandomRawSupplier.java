
package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;


public class RandomRawSupplier implements Supplier<byte[]> {

    private final RandomStringSupplier stringSupplier =
            new RandomStringSupplier(10, 20, true, true);

    @Override
    public byte[] get() {
        return stringSupplier.get().getBytes(StandardCharsets.UTF_8);
    }
}
