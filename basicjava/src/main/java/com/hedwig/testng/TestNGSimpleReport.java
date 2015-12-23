package com.hedwig.testng;

import com.google.common.collect.Lists;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * Created by patrick on 15/10/16.
 */
public class TestNGSimpleReport implements ITestListener, IReporter {
    private List<String> testPassed = Lists.newArrayList();
    private List<String> testFailed = Lists.newArrayList();
    private List<String> testSkipped = Lists.newArrayList();

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites,
                               String outputDirectory) {
        System.out.println("Passed Case: " + testPassed.size());
        System.out.println("testFailed Case: " + testFailed.size());
        System.out.println("testSkipped Case: " + testSkipped.size());

        for (String passed : testPassed) {
            System.out.println("passed case:" + passed);
        }
        for (String passed : testFailed) {
            System.out.println("failed case:" + passed);
        }

        for (String passed : testSkipped) {
            System.out.println("skipped case:" + passed);
        }

    }

    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testPassed.add(result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testFailed.add(result.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testSkipped.add(result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}
