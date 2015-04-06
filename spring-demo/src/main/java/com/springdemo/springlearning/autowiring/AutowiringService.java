package com.springdemo.springlearning.autowiring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutowiringService {
    private static final Logger logger = LogManager.getLogger(AutowiringService.class.getName());
    private AutowiringDao dao;

    public AutowiringService(AutowiringDao dao) {
        this.dao = dao;
    }

    public void setDao(AutowiringDao dao) {
        this.dao = dao;
    }

    public void post(String message){
        logger.info("the test result is {}",message);
        this.dao.save();
    }
}
