package com.springdemo.springlearning.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

public class BizThrowAdvice implements ThrowsAdvice {
    private static final Logger logger = LogManager.getLogger(BizThrowAdvice.class.getName());
    public void afterThrowable(Exception ex){
        logger.info("after throwable handle:{}",ex.getCause());
    }

    public void afterThrowing(Method method,Object[] args,Object target,Exception ex){

        logger.info("After throwable 2:method={},target_class={}",method.getName(),target.getClass().getName());
    }
}
