package com.springdemo.event.error;

import org.springframework.util.ErrorHandler;

public class MyErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable throwable) {
        System.out.println("error handle:"+throwable.getMessage());
    }

}