package com.springdemo.springlearning.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class BizMethodInterceptor implements MethodInterceptor {
    private static final Logger logger = LogManager.getLogger(BizMethodInterceptor.class.getName());

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        logger.info("AOPalliance method interceptor ={},static_part={}",methodInvocation.getMethod().getName()
                ,methodInvocation.getStaticPart().getClass().getName());
        Object o=methodInvocation.proceed();
        logger.info("returned value ={}",0);
        return o;
    }
}
