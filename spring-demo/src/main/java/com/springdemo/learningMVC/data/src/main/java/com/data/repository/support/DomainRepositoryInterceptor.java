package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;


import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.ProxyMethodInvocation;

import java.lang.reflect.Method;

public class DomainRepositoryInterceptor extends RepositoryInterceptor
        implements MethodInterceptor {

    public DomainRepositoryInterceptor() {
        super();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object proxy = getProxyOrTarget(invocation);
        Method method = invocation.getMethod();
        return doInjectRepository(proxy, method.getName(),
                () -> {
                    try {
                        return invocation.proceed();
                    } catch (Throwable ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    private Object getProxyOrTarget(MethodInvocation invocation) {
        if (invocation instanceof ProxyMethodInvocation) {
            return ((ProxyMethodInvocation) invocation).getProxy();
        }
        return invocation.getThis();
    }
}