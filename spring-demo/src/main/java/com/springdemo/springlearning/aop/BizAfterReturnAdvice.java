package com.springdemo.springlearning.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class BizAfterReturnAdvice implements AfterReturningAdvice {
    private static final Logger logger = LogManager.getLogger(BizAfterReturnAdvice.class.getName());

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        logger.info("Implement After Return Advice:{},target_class={}", method.getName(),target.getClass().getName()+returnValue);
    }
}
