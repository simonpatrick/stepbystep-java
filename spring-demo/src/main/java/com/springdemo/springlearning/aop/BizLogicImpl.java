package com.springdemo.springlearning.aop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BizLogicImpl implements BizLogic {

    private static final Logger logger = LogManager.getLogger(BizLogicImpl.class.getName());

    @Override
    public String save() {

        logger.info("start save biz logic");
        return "biz logic is saved";
    }

    @Override
    public void delete() {
        logger.info("start delete:");
    }
}
