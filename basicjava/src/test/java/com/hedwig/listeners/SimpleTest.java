package com.hedwig.listeners;

import com.hedwig.testlogs.TestNGEnhancedTest;
import com.hedwig.testlogs.TestResultLogger;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/11/16.
 */
public class SimpleTest extends TestNGEnhancedTest{

    @Test(invocationCount = 50,threadPoolSize = 50)
    public void test_logging(){
        TestResultLogger.log("this is step 1");
        TestResultLogger.log("this is step 2");
        TestResultLogger.log("this is step 3");
        TestResultLogger.log("this is step 4");
        TestResultLogger.log("this is step 5");
    }

    @Test(invocationCount = 50,threadPoolSize = 50)
    public void test_logging_2() {
        TestResultLogger.log("this is step 1");
        TestResultLogger.log("this is step 2");
        TestResultLogger.log("this is step 3");
        TestResultLogger.log("this is step 4");
        TestResultLogger.log("this is step 5");
    }

}
