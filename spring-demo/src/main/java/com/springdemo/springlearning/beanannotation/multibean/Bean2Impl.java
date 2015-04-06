package com.springdemo.springlearning.beanannotation.multibean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Order(2)
public class Bean2Impl implements Bean{
    private static final Logger logger = LogManager.getLogger(Bean2Impl.class.getName());

    @Override
    public void name() {
        logger.info("the class is {}",this.getClass().getCanonicalName());
    }
}
