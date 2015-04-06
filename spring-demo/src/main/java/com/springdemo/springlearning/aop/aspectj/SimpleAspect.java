package com.springdemo.springlearning.aop.aspectj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class SimpleAspect {
    private static final Logger logger = LogManager.getLogger(SimpleAspect.class.getName());

    @Pointcut("execution(* com.springdemo.springlearning.aop.aspectj.*.*(..))")
    public void pointcut(){
        logger.info("this is pointcut");
    }

    @Pointcut("within(com.springdemo.springlearning.aop.aspectj.*)")
    public void bizPointCut(){
        logger.info("this is biz point cut advice of aspectj");
    }

    @Before("pointcut()")
    public void before(){
        logger.info("Before...");
    }

    @Before("pointcut()&&args(arg)")
    public void BeforeWithParameters(String arg){
        logger.info("Before parameters:{}",arg);
    }

    @Before("pointcut() && @annotation(simpleMethod)")
    public void beforeAnnotation(SimpleMethod simpleMethod){
        logger.info("Before SimpleMethod={} Annotation!",simpleMethod.value());
    }

    @AfterReturning(pointcut = "bizPointCut()",returning = "returnValue")
    public void afterReturning(Object returnValue){
        logger.info("after returning: returnValue={}",returnValue);
    }

    @AfterThrowing(pointcut = "pointcut()",throwing = "re")
    public void afterThrowing(RuntimeException re){
        logger.info("After Throwing, exception ={}",re.getMessage());
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("start round 1:......");
        Object object = pjp.proceed();
        logger.info("start round 2:......");
        logger.info("object={}",object);
        return object;
    }

}

