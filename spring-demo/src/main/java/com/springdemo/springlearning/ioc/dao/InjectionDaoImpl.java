package com.springdemo.springlearning.ioc.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InjectionDaoImpl implements InjectionDao {
    private static final Logger logger = LogManager.getLogger(InjectionDaoImpl.class.getName());

    @Override
    public void save() {
        logger.info("save inject dao");
    }
}
