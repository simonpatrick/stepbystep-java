package com.hedwig.stepbystep.javatutorial.beanutils.jsoncomparetool;

import java.util.List;

/**
 * Created by kewu on 13-12-6.
 * Copy From Regis codes in PDF Parser
 */
public  interface TreeNode {

    public static final String STRING_TYPE = "string";
    public static final String BOOLEAN_TYPE = "boolean";
    public static final String NUMBER_TYPE = "number";
    public static final String NULL_TYPE = "null";
    public static final String ARRAY_TYPE = "array";
    public static final String OBJECT_TYPE = "object";

    public String getValue();

    public String getValueType();

    public String getName();

    public List<TreeNode> getChildren();
}
