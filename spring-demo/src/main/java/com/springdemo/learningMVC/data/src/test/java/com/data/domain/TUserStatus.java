
package com.springdemo.learningMVC.data.src.test.java.com.data.domain;


import com.common.base.EnumValue;

public enum TUserStatus implements EnumValue<Short> {
    LOCKED(-1),
    CREATED(0),
    VALIDATED(1);

    final short status;

    TUserStatus(int status) {
        this.status = (short) status;
    }

    public short getStatus() {
        return status;
    }

    public static <T extends Number> TUserStatus of(T status) {
        if (status == null) {
            return CREATED;
        }
        for (TUserStatus s : values()) {
            if (s.status == status.shortValue()) {
                return s;
            }
        }
        return CREATED;
    }

    @Override
    public Short getValue() {
        return status;
    }
}
