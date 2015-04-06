package com.springdemo.springlearning.beanannotation.multibean;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class Bean1Impl  implements Bean{
    private static final Logger logger = LogManager.getLogger(Bean1Impl.class.getName());

    @Override
    public void name() {
        logger.info("this is {}",this.getClass().getName());
    }
}
