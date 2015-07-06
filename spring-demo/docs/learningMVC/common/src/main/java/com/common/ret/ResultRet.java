package com.springdemo.learningMVC.common.src.main.java.com.common.ret;

/**
 * RESTFul 或 Ajax 请求成功后的响应实体。
 *
 */
public class ResultRet<T> extends Ret {

    public static final String PROP_RESULT = "result";

    private static final long serialVersionUID = 3901661368863185704L;

    private T result;

    protected ResultRet(String message) {
        this(null, message);
    }

    protected ResultRet(T result, String message) {
        super(SUCCESS_CODE, message);
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultRet)) return false;
        if (!super.equals(o)) return false;

        ResultRet resultRet = (ResultRet) o;

        return !(result != null ? !result.equals(resultRet.result) : resultRet.result != null);

    }

    @Override
    public int hashCode() {
        int result1 = super.hashCode();
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }

    @Override
    public String toString() {
        return toStringHelper().add(PROP_RESULT, getResult()).toString();
    }
}
