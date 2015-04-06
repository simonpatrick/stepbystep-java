package com.springdemo.springlearning.aop.introduction;

import org.aopalliance.aop.Advice;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

public class LockMixinAdvice extends DefaultIntroductionAdvisor{
    private static final Logger logger = LogManager.getLogger(LockMixinAdvice.class.getName());

    public LockMixinAdvice() {
        super(new LockMixin(),Lockable.class);
        logger.info("start init lockmixinadvice");
    }
}
