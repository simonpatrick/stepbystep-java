package com.hedwig.stepbystep.javatutorial.testng.listener;

import com.google.common.collect.Maps;
import com.hedwig.stepbystep.javatutorial.freemarker.FreemarkerHelper;
import com.hedwig.stepbystep.javatutorial.selenium.ScreenShotUtils;
import com.hedwig.stepbystep.javatutorial.testng.experiement.TestCaseDescription;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestCase;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestSuite;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestngAdaptor;
import freemarker.template.TemplateException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */


public class SimpleWebDriverScreenShotTestListener extends AbstractWebDriverEventListener implements ITestListener,IReporter {

    private Map<String,TestSuite> testSuiteMap = Maps.newConcurrentMap();
    private static final Logger logger = LogManager.getLogger(SimpleWebDriverScreenShotTestListener.class.getName());
    private ThreadLocal<ITestContext> testngContext= new ThreadLocal<>() ;
    private ThreadLocal<ITestResult> currentResult=new ThreadLocal<>();

    //threadLocal-> current ITestContext,ITestResult
    //TestResultLogging->getCurrent ITestContext,ITestResult -> logging is putting one to this map
    //Then aggregate these logs to TestResult
    @Override
    public void onTestStart(ITestResult result) {
        //init current test case
        TestCase currentCase = TestngAdaptor.covertToTestCase(new TestCase(),result);
        getCurrentTestSuite(result).getTestCases().add(currentCase);
        currentResult.set(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TestngAdaptor.covertToTestCase(getCurrentTestCase(result),result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        TestngAdaptor.covertToTestCase(getCurrentTestCase(result),result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestngAdaptor.covertToTestCase(getCurrentTestCase(result),result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        TestngAdaptor.covertToTestCase(getCurrentTestCase(result),result);
    }

    @Override
    public void onStart(ITestContext context) { // run before beforeclass annotation, only run once,it is in class level =>
        //init current test suite
        if(testSuiteMap.get(context.getSuite().getName())!=null){
            throw new RuntimeException("same suite name is existing");
        }else{
            TestSuite ts = new TestSuite(context.getStartDate(),context.getSuite().getName());
            testSuiteMap.put(context.getSuite().getName(),ts);
        }
        testngContext.set(context);
    }

    @Override
    public void onFinish(ITestContext context) {//run after aterclass annotation
        testSuiteMap.get(context.getSuite().getName()).setEndDate(context.getEndDate());
        logger.info("test suite map size is {}"+ testSuiteMap.size());
    }

    private TestSuite getCurrentTestSuite(ITestResult result){
        if(testSuiteMap.get(result.getTestContext().getSuite().getName())!=null){
         return   testSuiteMap.get(result.getTestContext().getSuite().getName());
        }
        throw new RuntimeException("test suite is not initialized,please check your testng file");
    }

    /**
     * get the current test case in runtime
     * @param result
     * @return
     */
    private TestCase getCurrentTestCase(ITestResult result){
        TestSuite suite = getCurrentTestSuite(result);
        //(result.getParameters() instanceof TestCaseDescription)
        for (TestCase tc: suite.getTestCases()) {
            if(tc.getStartedMills()==result.getStartMillis()
                    &&tc.getTestClassName().equalsIgnoreCase(result.getMethod().getRealClass().getSimpleName())
                    &&tc.getTestMethodName().equalsIgnoreCase(result.getMethod().getMethodName())){
                return tc;
            }
        }

        throw new RuntimeException("test case "+result.getMethod().getMethodName()+" is not initialized");
    }

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        Map freeMarkerData = new HashMap<>();
        boolean isFailed = false;
        int total_suite_count = testSuiteMap.entrySet().size();
        int total_test_case_count =0;
        int total_failed_case_count=0;
        int total_passed_case_count=0;
        for (Map.Entry<String, TestSuite> testSuiteEntry: testSuiteMap.entrySet()) {
            if(testSuiteEntry.getValue().isTestSuiteFailed()) isFailed =true;
            total_test_case_count+=testSuiteEntry.getValue().getTestCases().size();
            total_failed_case_count+=testSuiteEntry.getValue().getFailedTestCases().size();
            total_passed_case_count+=testSuiteEntry.getValue().getPassedTestCases().size();
        }

        freeMarkerData.put("testSuiteMap",testSuiteMap);
        freeMarkerData.put("isFailed",isFailed);
        freeMarkerData.put("total_suite_count",total_suite_count);
        freeMarkerData.put("total_test_case_count",total_test_case_count);
        freeMarkerData.put("total_failed_case_count",total_failed_case_count);
        freeMarkerData.put("total_passed_case_count",total_passed_case_count);

        try {
            FreemarkerHelper.processSimpleReport(freeMarkerData);
        } catch (IOException e) {
            logger.error("error_result=",e);
        } catch (TemplateException e) {
            logger.error("error_result=",e);
        }
    }


    @Override
    public void onException(Throwable throwable, WebDriver driver) {

        String errorStringShotPath = ScreenShotUtils.takeScreenshotForSimpleReport(driver);
        getCurrentTestCase(currentResult.get()).addFailedScreenshotPath(errorStringShotPath);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        String errorStringShotPath = ScreenShotUtils.takeScreenshotForSimpleReport(driver);
        getCurrentTestCase(currentResult.get()).addFailedScreenshotPath(errorStringShotPath);
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        String errorStringShotPath = ScreenShotUtils.takeScreenshotForSimpleReport(driver);
        getCurrentTestCase(currentResult.get()).addFailedScreenshotPath(errorStringShotPath);
    }

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
        String errorStringShotPath = ScreenShotUtils.takeScreenshotForSimpleReport(driver);
        getCurrentTestCase(currentResult.get()).addFailedScreenshotPath(errorStringShotPath);
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
        String errorStringShotPath = ScreenShotUtils.takeScreenshotForSimpleReport(driver);
        getCurrentTestCase(currentResult.get()).addFailedScreenshotPath(errorStringShotPath);
    }
}
