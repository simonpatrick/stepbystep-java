package com.hedwig.testng;

import com.hedwig.testng.globallogging.TestNGBaseTest;
import com.hedwig.testng.globallogging.TestResultLogger;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/11/16.
 */
public class SimpleTest extends TestNGBaseTest {

    @Test
    public void test_logging(){
        TestResultLogger.log("this is step 1");
        TestResultLogger.log("this is step 2");
        TestResultLogger.log("this is step 3");
        TestResultLogger.log("this is step 4");
        TestResultLogger.log("this is step 5");
    }

    @Test
    public void test_logging_2() {
        TestResultLogger.log("this is step 1");
        TestResultLogger.log("this is step 2");
        TestResultLogger.log("this is step 3");
        TestResultLogger.log("this is step 4");
        TestResultLogger.log("this is step 5");
    }

}
