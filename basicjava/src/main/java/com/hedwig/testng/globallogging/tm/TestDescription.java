package com.hedwig.testng.globallogging.tm;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

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
    private List<Class> checkPoints = Lists.newArrayList();
    private static final Logger logger = LogManager.getLogger(TestDescription.class.getName());

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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("testCaseId", testCaseId)
                .add("testMethodName", testMethodName)
                .add("testDescription", testDescription)
                .add("priority", priority)
                .toString();
    }


    public String getCheckPointClasses() {
        return checkPointClasses;
    }

    public List<Class> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<Class> checkPoints) {
        this.checkPoints = checkPoints;
    }

    public void setCheckPointClasses(String checkPointClasses) {
        this.checkPointClasses = checkPointClasses;
    }
}
