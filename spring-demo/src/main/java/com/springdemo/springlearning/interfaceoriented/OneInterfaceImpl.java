package com.springdemo.springlearning.interfaceoriented;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OneInterfaceImpl implements OneInterface {
    private static final Logger logger = LogManager.getLogger(OneInterfaceImpl.class.getName());

    @Override
    public void hello() {
        logger.info("hello world!");
    }
}
