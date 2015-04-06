package com.springdemo.learningMVC.common.src.main.java.com.common.ret;

import java.util.*;

/**
 * @author fuchun
 * @version $Id: ErrorRet.java 532 2014-11-25 08:33:50Z fuchun $
 * @since 2.0
 */
public class ErrorRet extends Ret {

    private static final long serialVersionUID = -1560627775105287958L;



    public static ErrorRet of(Integer code) {
        return of(code, null);
    }

    public static ErrorRet of(Integer code, String message) {
        return new ErrorRet(code, message);
    }

    public static ErrorRet of(Integer code, String message, Collection<FieldError> fieldErrors) {
        return new ErrorRet(code, message, fieldErrors);
    }

    private Set<FieldError> fieldErrors;

    protected ErrorRet(Integer code, String message) {
        super(code, message);
    }

    protected ErrorRet(Integer code, String message, Collection<FieldError> fieldErrors) {
        super(code, message);
        if (fieldErrors != null) {
            fieldErrors.addAll(fieldErrors);
        }
    }

    public Set<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(Set<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }

    protected Set<FieldError> initAndGetFieldErrors() {
        if (fieldErrors == null) {
            fieldErrors = new HashSet<>();
        }
        return fieldErrors;
    }

    public ErrorRet addFieldErrors(FieldError fieldError, FieldError... fieldErrors) {
        Objects.requireNonNull(fieldError, "fieldError");
        initAndGetFieldErrors().add(fieldError);
        if (fieldErrors.length > 0) {
            initAndGetFieldErrors().addAll(Arrays.asList(fieldErrors));
        }
        return this;
    }

    public ErrorRet addFieldErrors(Collection<FieldError> fieldErrors) {
        Objects.requireNonNull(fieldErrors, "fieldErrors");
        initAndGetFieldErrors().addAll(fieldErrors);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ErrorRet)) return false;
        if (!super.equals(o)) return false;

        ErrorRet errorRet = (ErrorRet) o;

        return !(fieldErrors != null ? !fieldErrors.equals(errorRet.fieldErrors) : errorRet.fieldErrors != null);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (fieldErrors != null ? fieldErrors.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return toStringHelper().add("fieldErrors", getFieldErrors()).toString();
    }
}
