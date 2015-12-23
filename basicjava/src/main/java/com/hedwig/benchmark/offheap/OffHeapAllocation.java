package com.hedwig.benchmark.offheap;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class OffHeapAllocation {

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);
        } catch(Exception e) {
        }
    }

    public static void main(String[] args) {
        while (true) {
            long addr = unsafe.allocateMemory(8 * 1000000);
            unsafe.freeMemory(addr);
        }
    }
}