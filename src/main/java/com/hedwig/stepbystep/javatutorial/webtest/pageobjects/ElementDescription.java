package com.hedwig.stepbystep.javatutorial.webtest.pageobjects;

import com.google.common.base.MoreObjects;

/**
 * Created by patrick on 15/3/10.
 *
 * @version $Id$
 */


public class ElementDescription {
    private String name;
    private String type;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("type", type)
                .add("value", value)
                .toString();
    }
}
