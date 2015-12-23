package com.hedwig.benchmark.offheap;

public class HeapAllocation {
    public static void main(String[] args) {
        while (true) {
            Integer[] a = new Integer[1000000];
        }
    }
}