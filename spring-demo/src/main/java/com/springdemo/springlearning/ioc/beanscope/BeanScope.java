package com.springdemo.springlearning.ioc.beanscope;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BeanScope {
    private static final Logger logger = LogManager.getLogger(BeanScope.class.getName());
    public void sayHello(){
        logger.info("Bean Scope Learning");
    }
}
