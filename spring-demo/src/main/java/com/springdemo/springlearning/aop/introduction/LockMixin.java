package com.springdemo.springlearning.aop.introduction;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

public class LockMixin extends DelegatingIntroductionInterceptor implements Lockable {
    private static final Logger logger = LogManager.getLogger(LockMixin.class.getName());
    private boolean locked;
    private static final long serialVerionUID=6943163819932660450L;

    @Override
    public void lock() {
        this.locked=true;
    }

    @Override
    public void unlock() {
        this.locked=false;
    }

    @Override
    public boolean locked() {
        return this.locked;
    }

    /**
     * not locaked and set method, can be invoked
     * @param invocation
     * @return
     * @throws Throwable
     */
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (locked() && invocation.getMethod().getName().indexOf("set") == 0) {
            throw new RuntimeException();
        }
        return super.invoke(invocation);
    }

}
