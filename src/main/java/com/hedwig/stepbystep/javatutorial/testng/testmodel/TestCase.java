package com.hedwig.stepbystep.javatutorial.testng.testmodel;

import com.google.common.base.MoreObjects;
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
    private String testRealClassName;
    private String testMethodName;
    private String testDescription;
    private int status;
    private Throwable errors ;
    private String errorMessage;
    private long startedMills;
    private long endMills;
    private boolean isSkipped;
    private String parameters ;
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
        generateErrorMessage();
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

    public void addFailedScreenshotPath(String ... path){
        for (String s : path) {
            this.failedScreenshotPath.add(s);
        }
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("testClassName", testClassName)
                .add("testMethodName", testMethodName)
                .add("testDescription", testDescription)
                .add("status", status)
                .add("errors", errors)
                .add("startedMills", startedMills)
                .add("endMills", endMills)
                .add("isSkipped", isSkipped)
                .add("parameters", parameters)
                .add("stepScreenshotPath", stepScreenshotPath)
                .add("failedScreenshotPath", failedScreenshotPath)
                .toString();
    }

    private void generateErrorMessage(){
       if(errors==null) return ;
        StringBuilder sb= new StringBuilder();
        sb.append(errors.getMessage());
        sb.append("----------------------\n");
        sb.append("\n");
        sb.append("cause:");
        sb.append("----------------------\n");
        sb.append("\n");
        sb.append(errors.getCause());
        sb.append("----------------------\n");
        sb.append("\n");
        for (StackTraceElement stackTraceElement : errors.getStackTrace()) {
            sb.append(stackTraceElement);
            sb.append("\n");
        }

        this.errorMessage=sb.toString();
    }
    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {

        StringBuilder sb = new StringBuilder();
        for (Object o : parameters) {
            sb.append(o.toString());
            sb.append(";");
        }
        this.parameters = sb.toString();
    }

    public String getTestRealClassName() {
        return testRealClassName;
    }

    public void setTestRealClassName(String testRealClassName) {
        this.testRealClassName = testRealClassName;
    }
}
