package com.hedwig.stepbystep.javatutorial.testng.listener;

import com.google.common.collect.Maps;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestCase;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestSuite;
import com.hedwig.stepbystep.javatutorial.testng.testmodel.TestngAdaptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Map;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */


public class SimpleTestListener implements ITestListener{

    private Map<String,TestSuite> testSuiteMap = Maps.newConcurrentMap();
    private static final Logger logger = LogManager.getLogger(SimpleTestListener.class.getName());


    @Override
    public void onTestStart(ITestResult result) {
        logger.info("test {} is started at {}",result.getName(),result.getStartMillis());
        logger.info("test instance {} is started", result.getInstanceName());
        logger.info("test class name {} is started", result.getTestClass().getRealClass().getName());
        logger.info("test name is {},description is {}",result.getName(),result.getMethod().getDescription());
        logger.info("test method is {}", result.getMethod().getMethodName());
        logger.info("test method parameter is {}", result.getMethod().getConstructorOrMethod().getName());
        logger.info("data provider is {}",result.getInstance());
        logger.info("data provider is {}",result.getInstance());
        logger.info("test method parameter is {}",result.getParameters());
        //init current test case
        TestCase currentCase = TestngAdaptor.covertToTestCase(new TestCase(),result);
        getCurrentTestSuite(result).getTestCases().add(currentCase);
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
        logger.info("{} is started at {}",context.getName(),context.getStartDate());
        logger.info("All tested methods :{}",context.getAllTestMethods());
        logger.info("All passed tested methods :{}",context.getPassedTests());
        //init current test suite
        if(testSuiteMap.get(context.getName())!=null){
            throw new RuntimeException("same suite name is existing");
        }else{
            TestSuite ts = new TestSuite(context.getStartDate(),context.getName());
            testSuiteMap.put(context.getName(),ts);
        }
    }

    @Override
    public void onFinish(ITestContext context) {//run after aterclass annotation
        logger.info("{} is ended at {}",context.getName(),context.getEndDate());
        logger.info("all tested methods: {}",context.getAllTestMethods());
        logger.info("all passed tested methods :{}",context.getPassedTests());
        logger.info("all failed tested methods :{}",context.getFailedTests());
        logger.info("test result is {}",context.getSuite().getResults());
        logger.info("test result is {}",context.getPassedTests());
        logger.info("test result is {}",context.getFailedTests());
        //to do remove the failed but retry passed result
        testSuiteMap.get(context.getName()).setEndDate(context.getEndDate());
        System.out.println(testSuiteMap.size());
    }

    private TestSuite getCurrentTestSuite(ITestResult result){
        if(testSuiteMap.get(result.getTestContext().getName())!=null){
         return   testSuiteMap.get(result.getTestContext().getName());
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
        for (TestCase tc: suite.getTestCases()) {
            if(tc.getStartedMills()==result.getStartMillis()
                    &&tc.getTestClassName()==result.getMethod().getRealClass().getName()
                    &&tc.getTestMethodName()==result.getMethod().getMethodName()){
                return tc;
            }
        }

        throw new RuntimeException("test case "+result.getMethod().getMethodName()+" is not initialized");
    }
}
