package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.jsonToJava;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kewu on 14-1-28.
 */
public class Node {

    private Node parent;
    private List<Node> children = new ArrayList<Node>();
    private String name;
    private JsonNode value;

    public boolean isRoot(){
        return null==this.parent?true:false;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueType() {
        return this.value.getNodeType().toString();
    }

    public JsonNode getValue() {
        return value;
    }

    public void setValue(JsonNode value) {
        this.value = value;
    }
}
