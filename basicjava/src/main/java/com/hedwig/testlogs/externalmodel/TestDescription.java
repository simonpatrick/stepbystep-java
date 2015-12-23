package com.hedwig.testlogs.externalmodel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by patrick on 15/6/8.
 *
 * @version $Id: TestDescription.java 1934 2015-10-27 07:47:13Z wuke $
 */


public class TestDescription {
    private String testCaseId;
    private String testMethodName;
    private String testDescription;
    private String priority;
    private String checkPointClasses;
    private List<Class> checkPoints = new ArrayList<>();

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getTestMethodName() {
        return testMethodName;
    }

    public void setTestMethodName(String testMethodName) {
        this.testMethodName = testMethodName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public String getCheckPointClasses() {
        return checkPointClasses;
    }

    //todo need to add bdd style
    public void initCheckPointClasses(String checkPointClasses) throws ClassNotFoundException {

        this.checkPointClasses = checkPointClasses;
        if(checkPointClasses==null||!checkPointClasses.startsWith("com")) throw  new RuntimeException(checkPointClasses+"检查点输入错误，请校验");
        String[] classes = checkPointClasses.split("-");
        for (String aClass : classes) {
            checkPoints.add(Class.forName(aClass.trim()));
        }
    }

    public List<Class> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<Class> checkPoints) {
        this.checkPoints = checkPoints;
    }

    @Override
    public String toString() {
        return "TestDescription{" +
                "testCaseId='" + testCaseId + '\'' +
                ", testMethodName='" + testMethodName + '\'' +
                ", testDescription='" + testDescription + '\'' +
                ", priority='" + priority + '\'' +
                ", checkPointClasses='" + checkPointClasses + '\'' +
                ", checkPoints=" + checkPoints +
                '}';
    }
}