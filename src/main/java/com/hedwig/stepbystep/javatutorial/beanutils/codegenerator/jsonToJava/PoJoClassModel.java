package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.jsonToJava;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kewu on 14-2-7.
 */
public abstract class PoJoClassModel {

    private String packageName;
    private String className;
    private Map<String,String> fields = new HashMap<String, String>();

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
