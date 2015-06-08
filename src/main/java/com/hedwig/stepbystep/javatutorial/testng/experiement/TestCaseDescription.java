package com.hedwig.stepbystep.javatutorial.testng.experiement;

import com.google.common.base.MoreObjects;

/**
 * Created by patrick on 15/6/8.
 *
 * @version $Id$
 */


public class TestCaseDescription {

    private String description;
    private String priority;
    private String methodName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("description", description)
                .add("priority", priority)
                .add("methodName", methodName)
                .toString();
    }
}
