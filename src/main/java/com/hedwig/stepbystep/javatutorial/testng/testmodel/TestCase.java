package com.hedwig.stepbystep.javatutorial.testng.testmodel;

import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */


public class TestCase {
    private String testClassName;
    private String testMethodName;
    private String testDescription;
    private int status;
    private Throwable errors ;
    private long startedMills;
    private long endMills;
    private boolean isSkipped;
    private Object[] parameters ;
    private List<String> stepScreenshotPath = Lists.newArrayList();
    private List<String> failedScreenshotPath = Lists.newArrayList();

    public String getTestClassName() {
        return testClassName;
    }

    public void setTestClassName(String testClassName) {
        this.testClassName = testClassName;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public boolean isSkipped() {
        return isSkipped;
    }

    public void setSkipped(boolean isSkipped) {
        this.isSkipped = isSkipped;
    }

    public Long getEndMills() {
        return endMills;
    }

    public void setEndMills(Long endMills) {
        this.endMills = endMills;
    }

    public long getStartedMills() {
        return startedMills;
    }

    public void setStartedMills(long startedMills) {
        this.startedMills = startedMills;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public void setEndMills(long endMills) {
        this.endMills = endMills;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Throwable getErrors() {
        return errors;
    }

    public void setErrors(Throwable errors) {
        this.errors = errors;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public List<String> getStepScreenshotPath() {
        return stepScreenshotPath;
    }

    public void setStepScreenshotPath(List<String> stepScreenshotPath) {
        this.stepScreenshotPath = stepScreenshotPath;
    }

    public List<String> getFailedScreenshotPath() {
        return failedScreenshotPath;
    }

    public void setFailedScreenshotPath(List<String> failedScreenshotPath) {
        this.failedScreenshotPath = failedScreenshotPath;
    }
}
