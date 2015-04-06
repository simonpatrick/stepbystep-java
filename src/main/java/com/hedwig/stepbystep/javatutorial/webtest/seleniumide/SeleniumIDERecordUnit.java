package com.hedwig.stepbystep.javatutorial.webtest.seleniumide;

import com.google.common.collect.Maps;

import java.util.Map;

public class SeleniumIDERecordUnit {

    private String action;
    private String elementType;
    private String variant;
    private String locatorExpression;
    private String variantName;
    private String type;
    public static Map<String, String> keyClassMap = Maps.newHashMap();

    static {
        keyClassMap.put("type", "TextField");
        keyClassMap.put("click", "Button");
        keyClassMap.put("img", "Image");
        keyClassMap.put("open", "String");
        keyClassMap.put("select", "SelectList");
        keyClassMap.put("clickandwait", "Button");
        keyClassMap.put("assertalert", "Alert");
    }

    public SeleniumIDERecordUnit(String action, String elementType, String variant) {
        this.action = action;
        this.elementType = elementType;
        this.variant = variant;
        convert(action,elementType);
    }

    public void convert(String action, String elementType) {
        System.out.println(action);
        type = keyClassMap.get(action.toLowerCase());
        locatorExpression = elementType;
        String[] temp = locatorExpression.split("=");
        if (temp.length > 1) {
            this.variantName = temp[1];
        } else {
            this.variantName = locatorExpression;
        }
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getLocatorExpression() {
        return locatorExpression;
    }

    public void setLocatorExpression(String locatorExpression) {
        this.locatorExpression = locatorExpression;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}