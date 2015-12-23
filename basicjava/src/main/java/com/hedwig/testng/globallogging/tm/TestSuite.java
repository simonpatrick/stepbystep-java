package com.hedwig.testng.globallogging.tm;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by patrick on 15/3/16.
 * testng test suite数据
 * @version $Id$
 */

public class TestSuite {
    private String suiteName;
    private String description;
    private List<TestCase> testCases = Lists.newArrayList();
    private List<TestCase> passedTestCases =Lists.newArrayList();
    private List<TestCase> failedTestCases = Lists.newArrayList();
    private Date startedDate;
    private Date endDate;
    private boolean isPassedSuite = failedTestCases.isEmpty();

    public TestSuite(String suiteName) {
        this.suiteName = suiteName;
    }

    public TestSuite(Date startedDate, String suiteName) {
        this.startedDate = new Date(startedDate.getTime());
        this.suiteName = suiteName;
    }

    public String getSuiteName() {
        return suiteName;
    }

    public void setSuiteName(String suiteName) {
        this.suiteName = suiteName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public Date getStartedDate() {
        return new Date(this.startedDate.getTime());
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = new Date(startedDate.getTime());
    }

    public Date getEndDate() {
        return new Date(endDate.getTime());
    }

    public void setEndDate(Date endDate) {
        this.endDate = new Date(endDate.getTime());
    }


    public void addTestCase(TestCase ...testCases){
        Collections.addAll(this.testCases,testCases);
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("suiteName", suiteName)
                .add("description", description)
                .add("testCases", testCases)
                .add("startedDate", startedDate)
                .add("endDate", endDate)
                .add("isPassedSuite", isPassedSuite)
                .toString();
    }

    /**
     * 判断测试套件是否失败，考虑了重试case的情况
     * @return
     */
    public boolean isTestSuiteFailed() {
        Map<String,TestCase> passedResult = new HashMap<>();
        Map<String,TestCase> failedResult = new HashMap<>();
        for (TestCase testCase : testCases) {
            String uniqueTestedMethod = testCase.getTestClassName()+testCase.getTestMethodName()+ testCase.getParameters();
            Integer status = testCase.getStatus();
            //already have failed result
            if(failedResult.get(uniqueTestedMethod)!=null){
                if(status==1){ //remove the failed case and add passed case,and retry passed
                    failedResult.remove(uniqueTestedMethod);
                    passedResult.put(uniqueTestedMethod,testCase);
                }// else do nothing,keep it there
            }else{
                if(passedResult.get(uniqueTestedMethod)==null){
                    if(status==1) {
                        passedResult.put(uniqueTestedMethod,testCase);
                    }else if(status==2) failedResult.put(uniqueTestedMethod,testCase);
                }
            }
        }

        passedTestCases.addAll(passedResult.values().stream().collect(Collectors.toList()));

        failedTestCases.addAll(failedResult.values().stream().collect(Collectors.toList()));
        if (failedTestCases.isEmpty()) isPassedSuite = true;
        else isPassedSuite = false;
        return !isPassedSuite;

    }

    public List<TestCase> getPassedTestCases() {
        return passedTestCases;
    }

    public void setPassedTestCases(List<TestCase> passedTestCases) {
        this.passedTestCases = passedTestCases;
    }

    public List<TestCase> getFailedTestCases() {
        return failedTestCases;
    }

    public void setFailedTestCase(List<TestCase> failedTestCases) {
        this.failedTestCases = failedTestCases;
    }

    public boolean  getIsPassedSuite() {
        return isPassedSuite;
    }

    public void setPassedSuite(boolean isPassedSuite) {
        this.isPassedSuite = isPassedSuite;
    }
}
