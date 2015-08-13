package com.hedwig.ut.samples.parallel;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class NameLogger extends TestListenerAdapter {

    @Override
    public void onTestSuccess(ITestResult tr) {
        System.out.println("\tOK: " + getName(tr));
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        System.out.println("\tFAILED: " + getName(tr));
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        System.out.println("\tSKIP: " + getName(tr));
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("START: " + getName(result));
    }

    private String getName(ITestResult result) {
        return result.getTestClass().getName() + ":" + result.getMethod().getMethodName();
    }
}
