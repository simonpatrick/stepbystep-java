package com.hedwig.testng.globallogging;

import com.google.common.collect.Maps;
import com.hedwig.testng.globallogging.tm.TestCase;
import com.hedwig.testng.globallogging.tm.TestSuite;
import com.hedwig.testng.globallogging.tm.TestngAdaptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.*;

import java.util.Map;

import static com.hedwig.testng.globallogging.TestStepCollectors.getSteps;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */

@SuppressWarnings("unchecked")
public class TestStepCollectorTestListener implements ITestListener{

    private Map<String, TestSuite> testSuiteMap = Maps.newConcurrentMap();
    private static final Logger logger = LogManager.getLogger(TestStepCollectorTestListener.class.getName());
    private ThreadLocal<ITestContext> testngContext = new ThreadLocal<>();
    private ThreadLocal<ITestResult> currentResult = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        //initTestCase current test case
        TestCase currentCase = TestngAdaptor.covertToTestCase(new TestCase(), result);
        TestStepCollectors.initTestCaseLog(currentCase.getTestDescription());
        getCurrentTestSuite(result).getTestCases().add(currentCase);
        currentResult.set(result);

    }

    @Override
    public void onTestSuccess(ITestResult result) {
        TestngAdaptor.updateResult(getCurrentTestCase(result), result);
        TestStepCollectors.setTestCaseCompleted(getCurrentTestCase(result).getTestDescription());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        TestngAdaptor.updateResult(getCurrentTestCase(result), result);
        TestStepCollectors.setTestCaseCompleted(getCurrentTestCase(result).getTestDescription());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        TestngAdaptor.covertToTestCase(getCurrentTestCase(result), result);
        TestStepCollectors.setTestCaseCompleted(getCurrentTestCase(result).getTestDescription());

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        TestngAdaptor.updateResult(getCurrentTestCase(result), result);
        TestStepCollectors.setTestCaseCompleted(getCurrentTestCase(result).getTestDescription());
    }

    @Override
    public void onStart(ITestContext context) { // run before beforeclass annotation, only run once,it is in class level =>
        //initTestCase current test suite
        TestSuite ts = testSuiteMap.get(context.getSuite().getName());
        if (ts == null) {
            ts = new TestSuite(context.getStartDate(), context.getSuite().getName());
            testSuiteMap.put(context.getSuite().getName(), ts);
            testngContext.set(context);
        }

    }

    @Override
    public void onFinish(ITestContext context) {//run after after class annotation
        testSuiteMap.get(context.getSuite().getName()).setEndDate(context.getEndDate());
        logger.info("test suite map size is {}", testSuiteMap.size());
        System.out.println(Thread.currentThread().getId());
        System.out.println(getSteps());

//        System.out.println(getSteps());
        //getSteps().get().forEach(System.out::println);
    }

    private synchronized TestSuite getCurrentTestSuite(ITestResult result) {
        if (testSuiteMap.get(result.getTestContext().getSuite().getName()) != null) {
            return testSuiteMap.get(result.getTestContext().getSuite().getName());
        }else{
            TestSuite ts = new TestSuite(result.getTestContext().getStartDate(), result.getTestContext().getSuite().getName());
            logger.info("创建新的TestSuite");
            testSuiteMap.put(result.getTestContext().getSuite().getName(), ts);
            return ts;
        }
    }

    /**
     * get the current test case in runtime
     *
     * @param result
     * @return
     */
    private synchronized TestCase getCurrentTestCase(ITestResult result) {
        TestSuite suite =getCurrentTestSuite(result);
//        logger.info("TestCases:{}",suite.getTestCases());
        logger.info("test result: {}",result);
        if(null!=suite&&result!=null) {
            for (TestCase tc : suite.getTestCases()) {
                if (tc.getStartedMills() == result.getStartMillis()
                        && tc.getTestClassName().equalsIgnoreCase(result.getMethod().getRealClass().getSimpleName())
                        && tc.getTestMethodName().equalsIgnoreCase(result.getMethod().getMethodName())) {
                    return tc;
                }
            }
        }
        TestCase currentCase = TestngAdaptor.covertToTestCase(new TestCase(), result);
        suite.addTestCase(currentCase);
        return currentCase;
    }

}
