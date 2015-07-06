package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import com.common.base.EnumValue;

import javax.annotation.Nullable;

public enum RecordStatus implements EnumValue<Number> {

    /**
     * 记录已被删除（失效）。
     */
    DELETED(-1, "deleted"),

    /**
     * 记录正在被创建（已写入，未验证，未激活）。
     */
    CREATING(0, "creating"),

    /**
     * 记录已被创建（已写入，已验证，正常）。
     */
    CREATED(1, "created"),

    /**
     * 记录被锁定。
     */
    LOCKED(2, "locked");

    final short code;
    final String info;

    RecordStatus(int code, String info) {
        this.code = (short) code;
        this.info = info;
    }

    public short getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }

    @Override
    public Number getValue() {
        return (int) code;
    }

    public static <T extends Number> RecordStatus of(T code) {
        return of(code, CREATING);
    }

    /**
     * Retrieves a {@code RecordStatus} with given code, or {@code defVal} if
     * code none found or given {@code code} is null.
     *
     * @param code   status code value(maybe null).
     * @param defVal default RecordStatus returned(maybe null).
     * @param <T>    code number type.
     * @return a {@code RecordStatus} with given code, or {@code defVal} if
     * code none found or given {@code code} is null.
     */
    public static <T extends Number> RecordStatus of(
            @Nullable T code, @Nullable RecordStatus defVal) {
        if (code == null) {
            return defVal;
        }
        for (RecordStatus status : values()) {
            if (status.code == code.shortValue()) {
                return status;
            }
        }
        return defVal;
    }
}
