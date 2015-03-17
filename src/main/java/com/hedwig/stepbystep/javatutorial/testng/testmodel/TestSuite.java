package com.hedwig.stepbystep.javatutorial.testng.testmodel;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */

//todo need to refactor the test result Model
public class TestSuite {
    private String suiteName;
    private String description;
    private List<TestCase> testCases = Lists.newArrayList();
    private List<TestCase> passedTestCases =Lists.newArrayList();
    private List<TestCase> failedTestCases = Lists.newArrayList();
    private Date startedDate;
    private Date endDate;
    private Boolean isPass;

    public TestSuite(String suiteName) {
        this.suiteName = suiteName;
    }

    public TestSuite(Date startedDate, String suiteName) {
        this.startedDate = startedDate;
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
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public void addTestCase(TestCase ...testCases){
        for (TestCase testCase : testCases) {
            this.testCases.add(testCase);
        }
    }
    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("suiteName", suiteName)
                .add("description", description)
                .add("testCases", testCases)
                .add("startedDate", startedDate)
                .add("endDate", endDate)
                .add("isPass", isPass)
                .toString();
    }


    public boolean isTestSuiteFailed() {
        Map<String,TestCase> passedResult = new HashMap<>();
        Map<String,TestCase> failedResult = new HashMap<>();
        for (TestCase testCase : testCases) {
            String uniqueTestedMethod = testCase.getTestClassName()+testCase.getTestMethodName()+ testCase.getParameters();
            Integer status = testCase.getStatus();
            if(status==1&&failedResult.get(uniqueTestedMethod)!=null){//retried case
                failedResult.remove(uniqueTestedMethod);
                passedResult.put(uniqueTestedMethod,testCase);
            }else{
                if(status==1) passedResult.put(uniqueTestedMethod,testCase);
                if(status==2) failedResult.put(uniqueTestedMethod,testCase);
            }
        }
        this.passedTestCases = (List<TestCase>) passedResult.values();
        this.failedTestCases = (List<TestCase>) failedResult.values();
        return failedTestCases.size()>0?true:false;
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

    public boolean isPass() {
        if(isPass==null){
           isPass = !isTestSuiteFailed();
        }
        return isPass;
    }

    public void setPass(boolean isPass) {
        this.isPass = isPass;
    }
}
