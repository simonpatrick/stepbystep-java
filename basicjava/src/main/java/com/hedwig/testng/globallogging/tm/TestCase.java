package com.hedwig.testng.globallogging.tm;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * Created by patrick on 15/3/16.
 *  获取testng 测试数据
 * @version $Id$
 */


public class TestCase {

    private String testCaseId;
    private String testClassName;
    private String testRealClassName;
    private String testMethodName;
    private String testDescription;
    private String priority;
    private int status;
    private Throwable errors ;
    private String errorMessage;
    private long startedMills;
    private long endMills;
    private boolean isSkipped;
    private String parameters ;
    private List<String> stepScreenshotPath = Lists.newArrayList();
    private List<String> failedScreenshotPath = Lists.newArrayList();
    private List<String> logs = Lists.newArrayList();

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
        Collections.addAll(this.failedScreenshotPath,path);
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

    public List<String> getLogs() {
        return logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "testCaseId='" + testCaseId + '\'' +
                ", testClassName='" + testClassName + '\'' +
                ", testRealClassName='" + testRealClassName + '\'' +
                ", testMethodName='" + testMethodName + '\'' +
                ", testDescription='" + testDescription + '\'' +
                ", priority='" + priority + '\'' +
                ", status=" + status +
                ", errors=" + errors +
                ", errorMessage='" + errorMessage + '\'' +
                ", startedMills=" + startedMills +
                ", endMills=" + endMills +
                ", isSkipped=" + isSkipped +
                ", parameters='" + parameters + '\'' +
                ", stepScreenshotPath=" + stepScreenshotPath +
                ", failedScreenshotPath=" + failedScreenshotPath +
                ", logs=" + logs +
                '}';
    }
}
