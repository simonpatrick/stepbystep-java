package com.springdemo.springlearning.aware;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SimpleApplicationContext implements ApplicationContextAware,BeanNameAware{
    private static final Logger logger = LogManager.getLogger(SimpleApplicationContext.class.getName());
    private String name ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.info("start setting application context:");
        logger.info("bean hash code: {}", applicationContext.getBean(this.name).hashCode());
    }

    @Override
    public void setBeanName(String s) {
        logger.info("start set bean name:{}",s);
        this.name=s;
    }
}
