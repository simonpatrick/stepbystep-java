package com.hedwig.stepbystep.javatutorial.beanutils.jsoncomparetool;


import com.google.gson.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by kewu on 13-12-6.
 */
public class JsonNode implements TreeNode {

    private JsonElement node;
    private String name;

    public JsonNode(String name, JsonElement node) {
        this.name = name;
        this.node = node;
    }

    /**
     * return the json root
     * @param json
     * @return
     */
   public static JsonNode getRoot(String json){
       JsonParser parser = new JsonParser();
       JsonElement root =parser.parse(json);
       return new JsonNode("",root);

   }


    @Override
    public String getValue() {
        if(node.isJsonPrimitive()){
            return node.getAsString();
        }


        return null;
    }

    @Override
    public String getValueType() {

        if (node.isJsonPrimitive()) {
            if (((JsonPrimitive) node).isNumber()) {
                return TreeNode.NUMBER_TYPE;
            }
            if (((JsonPrimitive) node).isBoolean()) {
                return TreeNode.BOOLEAN_TYPE;
            }
            if (((JsonPrimitive) node).isString()) {
                String value = node.getAsString();
                if (value.startsWith("$")) {
                    String variable = value.substring(1);
                    if ("boolean".equalsIgnoreCase(variable)) {
                        return TreeNode.BOOLEAN_TYPE;
                    } else if ("number".equalsIgnoreCase(variable)) {
                        return TreeNode.NUMBER_TYPE;
                    } else if ("any".equalsIgnoreCase(variable)) {
                        return TreeNode.STRING_TYPE;
                    }
                }
                return TreeNode.STRING_TYPE;
            }
        }
        if (node.isJsonNull()) {
            return TreeNode.NULL_TYPE;
        }
        if (node.isJsonArray()) {
            return TreeNode.ARRAY_TYPE;
        }
        if (node.isJsonObject()) {
            return TreeNode.OBJECT_TYPE;
        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<TreeNode> getChildren() {
        List<TreeNode> rt = new ArrayList<TreeNode>();
        if(node.isJsonObject()){
            for (Map.Entry<String,JsonElement> stringJsonElementEntry : ((JsonObject)node).entrySet()) {
                rt.add(new JsonNode(stringJsonElementEntry.getKey(),stringJsonElementEntry.getValue()));
            }


        }else if(node.isJsonArray()){
            JsonArray array = (JsonArray)node;
            if(array!=null){
                if(array.size()>0&&!array.get(0).isJsonPrimitive()){
                    for (int i = 0; i <array.size() ; i++) {
                        rt.add(new JsonNode(String.valueOf(i),array.get(i)));
                    }
                }
            }

        }else if(node.isJsonNull()||node.isJsonPrimitive()){
            return Collections.emptyList();
        }
        return rt;
    }



    public JsonElement getNode() {
        return node;
    }

    public void setNode(JsonElement node) {
        this.node = node;
    }


}
