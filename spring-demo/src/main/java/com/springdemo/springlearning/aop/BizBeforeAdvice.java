package com.springdemo.springlearning.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.BeforeAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class BizBeforeAdvice implements MethodBeforeAdvice{
    private static final Logger logger = LogManager.getLogger(BizBeforeAdvice.class.getName());

    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        logger.info("before method={},in target_class={},for input={}",method.getName(),target.getClass().getName(),args);
    }
}
