package com.hedwig.exception;

/**
 * Created by patrick on 15/11/19.
 */
public class LoggingException {

    public static void main(String[] args) {
        Exception e = new Exception("this is exception");
        Throwable t = new Throwable("this is a error");
        System.out.println(e);
        for (StackTraceElement stackTraceElement : t.getStackTrace()) {
            System.out.println(stackTraceElement);
        }

        System.out.println(e.getCause());
    }
}
