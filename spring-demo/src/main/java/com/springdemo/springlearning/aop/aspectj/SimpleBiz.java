package com.springdemo.springlearning.aop.aspectj;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class SimpleBiz {
    private static final Logger logger = LogManager.getLogger(SimpleBiz.class.getName());

    @SimpleMethod(value="simple method sample")
    public String save(String args){
        logger.info("Simple Biz save {} successfully",args);
        return "Save completed!!!";
    }
}
