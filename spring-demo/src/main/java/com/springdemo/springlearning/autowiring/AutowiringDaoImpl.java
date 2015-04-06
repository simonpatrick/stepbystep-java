package com.springdemo.springlearning.autowiring;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AutowiringDaoImpl implements AutowiringDao {
    private static final Logger logger = LogManager.getLogger(AutowiringDaoImpl.class.getName());

    @Override
    public void save() {
        logger.info("this is autowiring dao result!");
    }
}
