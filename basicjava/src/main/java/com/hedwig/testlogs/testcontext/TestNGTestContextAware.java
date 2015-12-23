package com.hedwig.testlogs.testcontext;

import com.google.common.collect.Maps;
import com.hedwig.testlogs.adaptors.TestNGAdaptor;
import com.hedwig.testlogs.internalmodel.TestCase;
import com.hedwig.testlogs.internalmodel.TestSuite;
import com.hedwig.testlogs.listeners.TestStepLoggerListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.util.Map;
import java.util.Set;

/**
 * Created by patrick on 15/11/16.
 */
public class TestNGTestContextAware {

    private Map<String, TestSuite> testSuiteMap = Maps.newConcurrentMap();
    private static final Logger logger = LogManager.getLogger(TestNGTestContextAware.class.getName());

    public TestCase initTestCase(ITestResult result) {
        TestCase testCase = TestNGAdaptor.covertToTestCase(new TestCase(), result);
        getCurrentTestSuite(result).addTestCase(testCase);
        initTestCaseLog(testCase);
        return testCase;
    }

    private void initTestCaseLog(TestCase testCase) {
        TestStepLoggerListener.getInstance().initTestCaseLogger(testCase);
    }

    public TestSuite initTestSuite(ITestContext context) {
        TestSuite ts = testSuiteMap.get(context.getSuite().getName());
        if (ts == null) {
            ts = new TestSuite(context.getStartDate(), context.getSuite().getName());
            testSuiteMap.put(context.getSuite().getName(), ts);
        }
        return ts;
    }

    private synchronized TestSuite getCurrentTestSuite(ITestResult result) {

        if (testSuiteMap.get(result.getTestContext().getSuite().getName()) != null) {
            return testSuiteMap.get(result.getTestContext().getSuite().getName());
        } else {
            TestSuite ts = new TestSuite(result.getTestContext().getStartDate(),
                    result.getTestContext().getSuite().getName());
            logger.info("创建新的TestSuite");
            testSuiteMap.put(result.getTestContext().getSuite().getName(), ts);
            return ts;
        }
    }

    private synchronized TestCase getCurrentTestCase(ITestResult result) {
        TestSuite suite = getCurrentTestSuite(result);
        logger.info("test result: {}", result);
        if (null != suite && result != null) {
            Set<TestCase> cases = suite.getTestCases().keySet();
            for (TestCase tc : cases) {
                if (
                        tc.getTestClassName().equalsIgnoreCase(result.getMethod().getRealClass().getSimpleName())
                                && tc.getTestMethodName().equalsIgnoreCase(result.getMethod().getMethodName())
                                && tc.getStartedMillis() == result.getStartMillis()) {
                    return tc;
                }
            }
        }
        TestCase currentCase = TestNGAdaptor.covertToTestCase(new TestCase(), result);
        suite.addTestCase(currentCase);
        return currentCase;
    }

    public void completeTestCaseLog(ITestResult result) {
        TestCase currentCase = getCurrentTestCase(result);
        TestNGAdaptor.updateResult(currentCase, result);
        currentCase.setIsCompleted(true);
        currentCase.setEndMills(System.currentTimeMillis());
    }

    public void completeTestSuite(ITestContext context) {
        testSuiteMap.get(context.getSuite().getName()).setEndDate(context.getEndDate());
        logger.info("test suite map size is {}", testSuiteMap.size());
    }

    public Map<String, TestSuite> getTestSuiteMap() {
        return testSuiteMap;
    }

    public void generateTestResult() {
        for (Map.Entry<String, TestSuite> entry : testSuiteMap.entrySet()) {
            System.out.println("TestSuiteName:" + entry.getValue().getSuiteName());
            System.out.println("IsSuitePassed:" + entry.getValue().isPassedSuite());
            System.out.println("passed test suite size:" + entry.getValue().getTestCases().size());
            for (TestCase testCase : entry.getValue().getTestCases().keySet()) {
                System.out.printf("testcase name:" + testCase.getTestClassName());
                System.out.printf("testcase name:" + testCase.getTestDescription());
                System.out.printf("testlogs:" + testCase.getLogs());
            }
        }
    }

}
