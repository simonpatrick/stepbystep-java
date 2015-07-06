package com.springdemo.learningMVC.common.src.test.java.com.common.base;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * {@link RandomNumbers} test case.
 *
 */
public class RandomNumbersTest {

    @Test
    public void testRandomInt4_benchmark() throws Exception {
        Integer n;
        for (int i = 0; i < 1000000; i++) {
            n = RandomNumbers.randomInt4();
            assertTrue(n >= 1000 && n <= 9999);
        }
    }

    @Test
    public void testRandomLong() throws Exception {
        int maxLen = String.valueOf(Long.MAX_VALUE).length();
        Long n;
        for (int i = 1; i <= maxLen; i++) {
            n = RandomNumbers.randomLong(i);
            assertTrue(String.valueOf(n).length() == i);
        }
    }

    @Test
    public void testRandomInt() throws Exception {
        int maxLen = String.valueOf(Integer.MAX_VALUE).length();
        Integer n;
        for (int i = 1; i <= maxLen; i++) {
            n = RandomNumbers.randomInt(i);
            assertTrue(String.valueOf(n).length() == i);
        }
    }

    @Test
    public void testRandomShort() throws Exception {
        int maxLen = String.valueOf(Short.MAX_VALUE).length();
        Short n;
        for (int i = 1; i <= maxLen; i++) {
            n = RandomNumbers.randomShort(i);
            assertTrue(String.valueOf(n).length() == i);
        }
    }

    @Test
    public void testInRangeInt_benchmark() throws Exception {
        int min = 333, max = 7777;
        Integer n;
        for (int i = 0; i < 1000000; i++) {
            n = RandomNumbers.inRange(min, max);
            assertTrue(n >= min && n <= max);
        }
    }

    @Test
    public void testInRangeLong_benchmark() throws Exception {
        long min = 100000000, max = 3999999999L;
        Long n;
        for (int i = 0; i < 1000000; i++) {
            n = RandomNumbers.inRange(min, max);
            assertTrue(n >= min && n <= max);
        }
    }
}