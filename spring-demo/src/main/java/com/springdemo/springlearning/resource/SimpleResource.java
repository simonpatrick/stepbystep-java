package com.springdemo.springlearning.resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

public class SimpleResource implements ApplicationContextAware{
    private static final Logger logger = LogManager.getLogger(SimpleResource.class.getName());
    private ApplicationContext application;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.application =applicationContext;
    }

    public void getResource(){

        Resource resource = application.getResource("config.properties");
        logger.info("file name:"+resource.getFilename());
    }
}
