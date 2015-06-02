package com.springdemo.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class MyAsyncExceptionHandler extends SimpleAsyncUncaughtExceptionHandler
        implements AsyncUncaughtExceptionHandler {

    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... args) {
        System.out.println("MyAsyncExceptionHandler happened, message : " + throwable.getMessage() + "method name:" + method.getName());
    }
}
