package com.hedwig.testlogs.listeners;

import com.hedwig.testlogs.testcontext.TestNGTestContextAware;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * Created by patrick on 15/11/16.
 */
public class TestNgListenerWithTestStepLogger implements ITestListener,IReporter {

    private TestNGTestContextAware testContextAware = new TestNGTestContextAware();
    private ThreadLocal<ITestContext> testngContext = new ThreadLocal<>();
    private ThreadLocal<ITestResult> currentResult = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        currentResult.set(result);
        testContextAware.initTestCase(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testContextAware.completeTestCaseLog(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testContextAware.completeTestCaseLog(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testContextAware.completeTestCaseLog(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        testContextAware.completeTestCaseLog(result);
    }

    @Override
    public void onStart(ITestContext context) {
        testngContext.set(context);
        testContextAware.initTestSuite(context);

    }

    @Override
    public void onFinish(ITestContext context) {
        testContextAware.completeTestSuite(context);
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        testContextAware.generateTestResult();
    }

}
