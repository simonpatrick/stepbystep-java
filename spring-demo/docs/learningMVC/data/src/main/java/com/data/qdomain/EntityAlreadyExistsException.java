package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

public class EntityAlreadyExistsException extends EntityException {

    private static final long serialVersionUID = 128570505719595764L;

    /**
     * Constructs a new entity exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public EntityAlreadyExistsException() {
        super();
    }

    /**
     * Constructs a new entity exception with the specified {@code code}.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param code the exception code.
     */
    public EntityAlreadyExistsException(Integer code) {
        super(code);
    }

    /**
     * Constructs a new entity exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EntityAlreadyExistsException(String message) {
        super(message);
    }

    /**
     * Constructs a new entity exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param code    the exception code.
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public EntityAlreadyExistsException(Integer code, String message) {
        super(code, message);
    }
}
