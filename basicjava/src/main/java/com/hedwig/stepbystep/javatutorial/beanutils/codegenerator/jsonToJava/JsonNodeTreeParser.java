package com.hedwig.stepbystep.javatutorial.beanutils.codegenerator.jsonToJava;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by kewu on 14-1-27.
 */
public class JsonNodeTreeParser {

    private Map<String,Map<String,String>> classContent = new HashMap<String,Map<String,String>>();
    private final ObjectMapper m ;
    private final Node rootNode;

    public static JsonNodeTreeParser newInstance(){
        return new JsonNodeTreeParser();
    }

    public JsonNodeTreeParser() {
        this.m = new ObjectMapper();
        this.rootNode= new Node();
    }

    public Map<String,Map<String,String>> parser(File file) throws IOException {

        this.rootNode.setValue(m.readTree(file));
        this.rootNode.setName(file.getName());
        this.rootNode.setParent(null);
        this.rootNode.setChildren(getChildrenNodes(this.rootNode));
        generateClassMetaMap(rootNode);
        return classContent;
    }

    public Map<String,Map<String,String>> parser(String json,String mainClassName) throws IOException {
        this.rootNode.setName(mainClassName);
        this.rootNode.setValue(m.readTree(json));
        this.rootNode.setParent(null);
        this.rootNode.setChildren(getChildrenNodes(rootNode));
        generateClassMetaMap(this.rootNode);
        return classContent;
    }

    public static void main(String[] args) throws IOException {
        String name ="PdfParserResponse";
        String json = "{\n" +
                "        \"fileInfos\": [\n" +
                "            {\n" +
                "                \"id\": 36751908,   \n" +
                "                \"parsedData\": {\n" +
                "                        \"pdfParseDataId\": 28113758,\n" +
                "                        \"section\": \"GEN\",\n" +
                "                        \"row\": \"ADM\",\n" +
                "                        \"seat\": \"ADM\"\n" +
                "                }\n" +
                "            }\n" +
                "        ],\"test\":1\n" +
                "}";

        JsonNodeTreeParser parser = new JsonNodeTreeParser();
        parser.parser(json,name);
        System.out.println(parser.getClassContent());
    }

       public List<Node> getChildrenNodes(Node  parentNode) throws IOException {
           JsonNode node = parentNode.getValue();
           List<Node> children = new ArrayList<Node>();
           if(node.isArray()) node =node.get(0);

           Iterator<Map.Entry<String,JsonNode>> iterator = node.fields();
           Map.Entry<String,JsonNode> next = null;

           while(iterator.hasNext()){
                   next = iterator.next();
                   Node childNode = new Node();
                   childNode.setParent(parentNode);
                   childNode.setValue(next.getValue());
                   childNode.setName(next.getKey());
                   childNode.setChildren(getChildrenNodes(childNode));
                   children.add(childNode);
           }
           return children;

       }

     public void printOut(Node rootNode){
         if(rootNode.getChildren().size()>0) System.out.println("class name: "+rootNode.getName());
         for (Node node : rootNode.getChildren()) {
             System.out.println("member type:"+node.getValueType());
             System.out.println("member name:" +node.getName());
             System.out.println("parent Name:" + node.getParent().getName());
             if(node.getChildren().size()>0) printOut(node);
         }

     }

    public void generateClassMetaMap(Node rootNode){
        String className =null;
        if(rootNode.getChildren().size()>0) className =rootNode.getName();
        Map<String,String> fieldMap = new HashMap<String, String>();
        for (Node node : rootNode.getChildren()) {
           fieldMap.put(node.getName(),node.getValueType());

            if(classContent.size()==0){
                classContent.put(className,fieldMap);
             }else{
                if(null==classContent.get(className)){
                    classContent.put(className,fieldMap);
                }else{
                    classContent.get(className).putAll(fieldMap);
                }
            }
           if(node.getChildren().size()>0) generateClassMetaMap(node);
       }
    }

    public Map<String, Map<String, String>> getClassContent() {
        return classContent;
    }
}
