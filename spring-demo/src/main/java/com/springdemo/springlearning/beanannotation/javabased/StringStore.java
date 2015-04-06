package com.springdemo.springlearning.beanannotation.javabased;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StringStore implements Store<String> {
    private static final Logger logger = LogManager.getLogger(StringStore.class.getName());
    public void init(){
        logger.info("this is string store init");
    }

    public void destroy(){
        logger.info("this is string store destroy");
    }
}
