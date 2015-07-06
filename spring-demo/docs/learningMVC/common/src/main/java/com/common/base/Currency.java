package com.springdemo.learningMVC.common.src.main.java.com.common.base;

public enum Currency implements EnumValue<Integer> {

    /**
     * 现金
     */
    CASH(1, "现金", "Cash"),

    /**
     * 诺币
     */
    NONOBANK(2, "货币", "Coin"),

    /**
     * 积分
     */
    SCORE(3, "积分", "Score");

    private final short code;
    private final String name;
    private final String alias;

    private Currency(int code, String name, String alias) {
        this.code = (short)code;
        this.name = name;
        this.alias = alias;
    }

    public short getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getAlias() {
        return alias;
    }

    @Override
    public Integer getValue() {
        return (int) code;
    }

    public static <N extends Number> Currency of(N code) {
        if (code == null) return null;
        for (Currency c: values()) {
            if (c.code == code.shortValue())
                return c;
        }
        return null;
    }
}
