package com.hedwig.stepbystep.javatutorial.testng.testmodel;

import org.testng.ITestResult;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */


public class TestngAdaptor {

    public static TestCase covertToTestCase(TestCase tc,ITestResult result){
        tc.setStartedMills(result.getStartMillis());
        tc.setEndMills(result.getEndMillis());
        tc.setParameters(result.getParameters());
        tc.setTestClassName(result.getTestClass().getRealClass().getSimpleName());
        tc.setTestRealClassName(result.getTestClass().getRealClass().getName());
        tc.setStatus(result.getStatus());
        tc.setTestMethodName(result.getMethod().getMethodName());
        tc.setErrors(result.getThrowable());

        if(result.getMethod().getDescription()==null){
            tc.setTestDescription(result.getMethod().getMethodName());
        }else{
            tc.setTestDescription(result.getMethod().getDescription());
        }
        return tc;
    }
}
