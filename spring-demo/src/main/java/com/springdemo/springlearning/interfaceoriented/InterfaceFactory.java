package com.springdemo.springlearning.interfaceoriented;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InterfaceFactory {
    private static InterfaceFactory ourInstance = new InterfaceFactory();
    private static final Logger logger = LogManager.getLogger(InterfaceFactory.class.getName());

    public InterfaceFactory getInstance(String message) {
        logger.info("init interface factory {}",message);
        return ourInstance;
    }

}
