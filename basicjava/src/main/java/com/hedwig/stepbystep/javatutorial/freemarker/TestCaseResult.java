package com.hedwig.stepbystep.javatutorial.freemarker;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */


public class TestCaseResult {
    private String testResult;
    private String testCaseName;
    private String testSuiteName;

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getTestSuiteName() {
        return testSuiteName;
    }

    public void setTestSuiteName(String testSuiteName) {
        this.testSuiteName = testSuiteName;
    }
}
