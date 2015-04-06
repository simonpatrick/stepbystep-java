package com.hedwig.stepbystep.javatutorial.junit.executionorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//@FixMethodOrder(MethodSorters.JVM)
@FixMethodOrder(MethodSorters.DEFAULT)
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExecutionOrderTests {
    private static final Logger logger = LogManager.getLogger(ExecutionOrderTests.class.getName());

    @Test
    public void test_t(){
        logger.info("test_t");
    }


    @Test
    public void test_a(){
        logger.info("test_a");
    }


    @Test
    public void test_m(){
        logger.info("test_m");
    }
}
