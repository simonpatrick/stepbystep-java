package com.springdemo.springlearning.beanannotation.jsr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class JsrDao {
    private static final Logger logger = LogManager.getLogger(JsrDao.class.getName());
    public void save(){
        logger.info("JsrDAO is invoked");
    }
}
