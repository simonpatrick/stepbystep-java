package com.springdemo.learningMVC.common.src.main.java.com.common.ret;

import com.google.common.base.MoreObjects;

import java.io.Serializable;

public class FieldError implements Serializable {

    private static final long serialVersionUID = -4774409079455107916L;

    public static FieldError of(Integer code, String field, String message) {
        return new FieldError(code, field, message);
    }

    public static FieldError of(String field, String message) {
        return new FieldError(null, field, message);
    }

    public static FieldError of(String field) {
        return new FieldError(null, field, null);
    }

    private final Integer code;
    private final String field;
    private final String message;

    protected FieldError(Integer code, String field, String message) {
        this.code = code;
        this.field = field;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FieldError)) return false;

        FieldError that = (FieldError) o;

        return !(code != null ? !code.equals(that.code) : that.code != null) &&
                !(field != null ? !field.equals(that.field) : that.field != null) &&
                !(message != null ? !message.equals(that.message) : that.message != null);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (field != null ? field.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", getCode())
                .add("field", getField())
                .add("message", getMessage())
                .toString();
    }
}
