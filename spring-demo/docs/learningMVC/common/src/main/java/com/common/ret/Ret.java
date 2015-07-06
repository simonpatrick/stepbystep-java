package com.springdemo.learningMVC.common.src.main.java.com.common.ret;

import com.google.common.base.MoreObjects;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * RESTFul 或 Ajax 请求响应信息的基本实体。
 *
 */
public abstract class Ret implements Serializable {

    /**
     * 响应成功的返回码（与HttpStatus不同）。
     */
    public static final int SUCCESS_CODE = 0;

    /**
     * 通用失败（错误）码。返回此错误码时，必须给出{@code message} 信息。
     */
    public static final Integer ERROR_CODE = -1;

    public static final String PROP_CODE = "code";
    public static final String PROP_MESSAGE = "message";
    public static final String PROP_DESCRIPTION = "description";
    public static final String PROP_EXTRA = "extra";

    public static final Object[] EMPTY_OBJECTS = new Object[0];

    private static final long serialVersionUID = 1682502221564393172L;

    public static Object[] args(Object... args) {
        if (args.length == 0) {
            return EMPTY_OBJECTS;
        }
        Object[] result = new Object[args.length];
        System.arraycopy(args, 0, result, 0, args.length);
        return result;
    }

    protected static String format(Object message, Object... args) {
        if (args.length == 0) {
            return String.valueOf(message);
        }
        return String.format(String.valueOf(message), args);
    }

    public static Ret error(String message, FieldError fieldError, FieldError... fieldErrors) {
        ErrorRet errorRet = ErrorRet.of(null, message);
        if (fieldError != null) {
            errorRet.addFieldErrors(fieldError, fieldErrors);
        }
        return errorRet;
    }

    public static Ret fail(String message, Object... params) {
        return fail(message, null, params);
    }

    public static Ret fail(String message, Collection<FieldError> fieldErrors, Object... params) {
        return ErrorRet.of(ERROR_CODE, format(message, params), fieldErrors);
    }

    public static Ret ok(String message) {
        return ok(message, (Object[]) null);
    }

    public static Ret ok(String message, Object[] args) {
        return ok(null, message, args);
    }

    public static Ret ok(Object result, String message) {
        return ok(result, message, EMPTY_OBJECTS);
    }

    public static Ret ok(Object result, String message, Object[] args) {
        return new ResultRet<>(result, format(message, args));
    }

    private final Integer code;
    private final String message;
    private String description;
    private Map<String, Object> extra;

    protected Ret(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }

    protected Map<String, Object> initAndGetExtra() {
        if (extra == null) {
            extra = new HashMap<>();
        }
        return extra;
    }

    @SuppressWarnings("unchecked")
    public <T extends Ret> T addExtra(String key, Object value) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(value, "value");
        initAndGetExtra().put(key, value);
        return (T) this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Ret> T addExtras(Map<String, Object> map) {
        Objects.requireNonNull(map, "extraMap");
        if (map.isEmpty()) {
            return (T) this;
        }
        initAndGetExtra().putAll(map);
        return (T) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ret)) return false;

        Ret ret = (Ret) o;

        return !(code != null ? !code.equals(ret.code) : ret.code != null) &&
                !(description != null ? !description.equals(ret.description) : ret.description != null) &&
                !(extra != null ? !extra.equals(ret.extra) : ret.extra != null) &&
                !(message != null ? !message.equals(ret.message) : ret.message != null);

    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (extra != null ? extra.hashCode() : 0);
        return result;
    }

    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(getClass())
                .add(PROP_CODE, getCode())
                .add(PROP_MESSAGE, getMessage())
                .add(PROP_DESCRIPTION, getDescription())
                .add(PROP_EXTRA, getExtra());
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
