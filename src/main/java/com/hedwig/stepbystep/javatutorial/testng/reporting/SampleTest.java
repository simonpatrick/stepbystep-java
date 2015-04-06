package com.hedwig.stepbystep.javatutorial.testng.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SampleTest {
    private static final Logger logger = LogManager.getLogger(SampleTest.class.getName());
    @Test
    public void testMethod_1(){
        logger.info("running sample testing method 1");
        Assert.assertTrue(true);
    }
    @Test
    public void testMethodTwo(){
        Assert.assertTrue(false);
    }

    @Test(dependsOnMethods={"testMethodTwo"})
    public void testMethodThree(){
        Assert.assertTrue(true);
    }

}
