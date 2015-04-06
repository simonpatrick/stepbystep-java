package com.springdemo.learningMVC.common.src.main.java.com.common.base;

import com.google.common.base.Strings;

public enum PhaseType implements EnumValue<Integer> {

    /**
     * 天
     */
    DAY(1, "天", "day"),

    /**
     * 周
     */
    WEEK(2, "周", "week"),

    /**
     * 月
     */
    MONTH(3, "月", "month"),

    /**
     * 年
     */
    YEAR(4, "年", "year");

    private final short code;
    private final String name;
    private final String alias;

    private PhaseType(int code, String name, String alias) {
        this.code = (short) code;
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

    public static <T extends Number> PhaseType of(T code) {
        if (code == null || code.intValue() < 1)
            return null;
        for (PhaseType t : values())
            if (t.code == code.shortValue())
                return t;
        return null;
    }

    public static PhaseType of(String name) {
        if (Strings.isNullOrEmpty(name))
            return null;
        for (PhaseType t : values())
            if (name.equals(t.name) || name.equals(t.alias))
                return t;
        return null;
    }
}
