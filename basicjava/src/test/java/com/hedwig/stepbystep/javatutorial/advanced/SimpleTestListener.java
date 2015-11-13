package com.hedwig.stepbystep.javatutorial.advanced;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class SimpleTestListener implements ITestListener{
    private ThreadLocal<Integer> count = new ThreadLocal<Integer>();
    @Override
    public void onTestStart(ITestResult result) {
        Thread.currentThread().getId();
        System.out.println(this);
        count.set(1);
        System.out.println(count.get());
        System.out.println("test is started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("test is passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("test is failed");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("test is skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("test is passed with acceptable percentage!");
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("suite is started");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("suite is finished");
    }
}
