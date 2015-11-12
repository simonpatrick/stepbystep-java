package com.hedwig.stepbystep.javatutorial.junit.timeout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TimeoutTest {
    private static final Logger logger = LogManager.getLogger(TimeoutTest.class.getName());

    @Test(timeout = 1000)
    public void testWithTimeOut(){
        try {
            Thread.sleep(100L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Rule  //this rule apply to all class
    public Timeout globalTimeout= new Timeout(10000);

    @Test
    public void testInfiniteLoop1(){
        for (; ; ) {
            logger.info("inifinite loop1");
        }
    }
}
