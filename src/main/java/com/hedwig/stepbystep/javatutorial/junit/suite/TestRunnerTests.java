package com.hedwig.stepbystep.javatutorial.junit.suite;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnit4.class)
public class TestRunnerTests {

    private static final Logger logger = LogManager.getLogger(TestRunnerTests.class.getName());


    @Test
    public void testRunnerTest(){
        logger.info("start running ...");
        assertThat("expected result is not correct","test", is("test"));
    }
}
