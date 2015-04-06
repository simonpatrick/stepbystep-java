package com.hedwig.stepbystep.javatutorial.testng.reporting;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class CustomLogging implements ITestListener {
    private static final Logger logger = LogManager.getLogger(CustomLogging.class.getName());

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info("TestMethod={} started,start_time={}",iTestResult.getName(),iTestResult.getStartMillis());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info("TestMethod={} is passed",iTestResult.getName());
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.info("TestMethod={} is passed",iTestResult.getName());
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {

        logger.info("TestMethod={} is skipped",iTestResult.getName());

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

        logger.info("TestMethod={} is failed percentage",iTestResult.getName());
    }

    @Override
    public void onStart(ITestContext iTestContext) {

        logger.info("Test in Suite started: {}",iTestContext.getName());
        logger.info(iTestContext);
    }

    @Override
    public void onFinish(ITestContext iTestContext) {

        logger.info("Test in Suite ended: {}",iTestContext.getName());
        logger.info(iTestContext);
    }
}
