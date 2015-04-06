package com.springdemo.springlearning.ioc.lifecycle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class BeanLifeCycle implements InitializingBean,DisposableBean{
    private static final Logger logger = LogManager.getLogger(BeanLifeCycle.class.getName());
    public void defautInit() {
       logger.info("Bean default Init.");
    }

    public void defaultDestroy() {
        logger.info("Bean defaultDestroy.");
    }
    @Override
    public void destroy() throws Exception {
        logger.info("destroy the beans");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("after properties set");
    }

    public void start() {
        logger.info("Bean start .");
    }

    public void stop() {
        logger.info("Bean stop.");
    }
}
