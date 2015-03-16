package com.hedwig.stepbystep.javatutorial.testng.testmodel;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */


public class TestSuite {
    private String suiteName;
    private String description;
    private List<TestCase> testCases = Lists.newArrayList();
    private Date startedDate;
    private Date endDate;
    private boolean status;

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

    public boolean isStatus() {
        return status;
    }

}
