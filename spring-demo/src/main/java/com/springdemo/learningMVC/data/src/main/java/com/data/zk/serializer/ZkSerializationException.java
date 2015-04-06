package com.springdemo.learningMVC.data.src.main.java.com.data.zk.serializer;

public class ZkSerializationException extends RuntimeException {

    private static final long serialVersionUID = 2181553083329192778L;

    public ZkSerializationException() {
        super();
    }

    public ZkSerializationException(String message) {
        super(message);
    }

    public ZkSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ZkSerializationException(Throwable cause) {
        super(cause);
    }
}
