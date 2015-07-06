package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkArgument;

public final class RandomNumbers {
    private RandomNumbers() {}

    private static final int LONG_MAX_LENGTH = String.valueOf(Long.MAX_VALUE).length();
    private static final int INT_MAX_LENGTH = String.valueOf(Integer.MAX_VALUE).length();
    private static final int SHORT_MAX_LENGTH = String.valueOf(Short.MAX_VALUE).length();

    private static void checkLength(int length, int maxLength) {
        checkArgument(length > 0 && length <= maxLength, "The given number length must greater than 0.");
    }

    /**
     * Returns a random integer in the range of 1000-9999.
     */
    @Nonnull
    public static Integer randomInt4() {
        return randomInt(4);
    }

    /**
     * Returns a random long specified length.
     * <pre>
     * randomLong(0) =&gt; throw new IllegalArgumentException
     * randomLong(5) =&gt; 10000  ~ 99999
     * randomLong(6) =&gt; 100000 ~ 999999
     * randomLong(19)=&gt; 1000000000000000000 ~ 9223372036854775807L
     * randomLong(20)=&gt; throw new IllegalArgumentException
     * </pre>
     *
     * @param length long length (1 ~ 19).
     * @return a random long specified length.
     * @throws IllegalArgumentException if the {@code length <= 0} or
     * {@code length > String.valueOf(Long.MAX_VALUE).length}.
     */
    @Nonnull
    public static Long randomLong(int length) {
        checkLength(length, LONG_MAX_LENGTH);
        long min = getMin(length), max = getMax(length);
        return (long) (Math.random() * (max - min) + min);
    }

    /**
     * Returns a random integer specified length.
     * <pre>
     * randomInt(0) =&gt; throw new IllegalArgumentException
     * randomInt(5)  =&gt; 10000  ~ 99999
     * randomInt(6)  =&gt; 100000 ~ 999999
     * randomInt(10) =&gt; 1000000000 ~ 2147483647
     * randomInt(11) =&gt; throw new IllegalArgumentException
     * </pre>
     *
     * @param length integer length (1 ~ 10).
     * @return a random integer specified length.
     * @throws IllegalArgumentException if the {@code length <= 0} or
     * {@code length > String.valueOf(Integer.MAX_VALUE).length}.
     */
    @Nonnull
    public static Integer randomInt(int length) {
        checkLength(length, INT_MAX_LENGTH);
        long min = getMin(length), max = getMax(length);
        if (min > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("The given length greater than Integer.MAX_VALUE.");
        }
        if (max > Integer.MAX_VALUE) {
            max = Integer.MAX_VALUE;
        }
        return (int) (Math.random() * (max - min) + min);
    }

    /**
     * Returns a random short specified length.
     * <pre>
     * randomShort(0) =&gt; throw new IllegalArgumentException
     * randomShort(2) =&gt; 10  ~ 99
     * randomShort(3) =&gt; 100 ~ 999
     * randomShort(5) =&gt; 10000 ~ 32767
     * randomShort(6) =&gt; throw new IllegalArgumentException
     * </pre>
     *
     * @param length short length (1 ~ 5).
     * @return a random short specified length.
     * @throws IllegalArgumentException if the {@code length <= 0} or
     * {@code length > String.valueOf(Short.MAX_VALUE).length}.
     */
    @Nonnull
    public static Short randomShort(int length) {
        checkLength(length, SHORT_MAX_LENGTH);
        long min = getMin(length), max = getMax(length);
        if (min > Short.MAX_VALUE) {
            throw new IllegalArgumentException("The given length greater than Short.MAX_VALUE.");
        }
        if (max > Short.MAX_VALUE) {
            max = Short.MAX_VALUE;
        }
        return (short) (Math.random() * (max - min) + min);
    }

    private static Long getMin(int length) {
        return (long) Math.pow(10, (length - 1));
    }

    private static Long getMax(int length) {
        if (length == LONG_MAX_LENGTH) return Long.MAX_VALUE;
        return (long) Math.pow(10, length) - 1;
    }

    /**
     * Returns a specified range ({@code min}-{@code max}) random long.
     *
     * @param min min value.
     * @param max max value.
     * @return a specified range ({@code min}-{@code max}) random long.
     */
    public static Long inRange(long min, long max) {
        if (min > max) {
            long temp = min;
            min = max;
            max = temp;
        }
        return (long) (Math.random() * (max - min) + min);
    }

    /**
     * Returns a specified range ({@code min}-{@code max}) random integer.
     *
     * @param min min value.
     * @param max max value.
     * @return a specified range ({@code min}-{@code max}) random integer.
     */
    public static Integer inRange(int min, int max) {
        if (min > max) {
            int temp = min;
            min = max;
            max = temp;
        }
        return (int) (Math.random() * (max - min) + min);
    }
}