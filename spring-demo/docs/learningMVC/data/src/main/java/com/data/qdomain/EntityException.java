package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

public class EntityException extends RuntimeException {

    private static final long serialVersionUID = -5488142200270650085L;

    private Integer code;

    /**
     * Constructs a new entity exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public EntityException() {
        super();
    }

    /**
     * Constructs a new entity exception with the specified {@code code}.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param code the exception code.
     */
    public EntityException(Integer code) {
        this(code, null);
    }

    /**
     * Constructs a new entity exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EntityException(String message) {
        this(null, message);
    }

    /**
     * Constructs a new entity exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param code the exception code.
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EntityException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public EntityException(Throwable cause) {
        super(cause);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
