package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;


import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.aop.ProxyMethodInvocation;


public class DomainRepositoryJoinPoint extends RepositoryInterceptor {

    public DomainRepositoryJoinPoint() {
        super();
    }

    public Object injectRepository(ProceedingJoinPoint joinPoint) throws Throwable{
        Object proxy = getProxyOrTarget(joinPoint);
        String method = joinPoint.getSignature().getName();
        return doInjectRepository(proxy, method, () -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    protected Object getProxyOrTarget(ProceedingJoinPoint joinPoint) {
        if (joinPoint instanceof ProxyMethodInvocation) {
            return ((ProxyMethodInvocation) joinPoint).getProxy();
        }
        return joinPoint.getThis();
    }
}
