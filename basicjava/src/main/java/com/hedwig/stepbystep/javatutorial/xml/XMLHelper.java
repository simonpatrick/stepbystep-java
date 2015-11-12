package com.hedwig.stepbystep.javatutorial.xml;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.*;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */


public class XMLHelper {
    private XMLHelper(){}
    private Document doc;
    private Element root;

    private static final Logger logger = LogManager.getLogger(XMLHelper.class.getName());

    public static XMLHelper build(String path){
        XMLHelper helper = new XMLHelper();
        SAXReader reader = new SAXReader();
        try {
            helper.setDoc(reader.read(ClassLoader.getSystemResourceAsStream(path)));
            helper.setRoot(helper.getDoc().getRootElement());
        } catch (DocumentException e) {
            logger.error("load page object resource file failed, error=",e);
            throw new RuntimeException(e);
        }

        return helper;
    }

    public String getText(String nodeName){
        return root.element(nodeName).getTextTrim();
    }

    public String getAttribute(String nodeName,String attributeName){

        return root.element(nodeName).attributeValue(attributeName);
    }

    public Map<String,String> getAttributes(String nodeName){
        Map<String,String> attributeKV = Maps.newHashMap();
        for (Object attribute : root.element(nodeName).attributes()) {
            attributeKV.put(((Attribute) attribute).getName(), ((Attribute) attribute).getStringValue());
        }

        return attributeKV;
    }

    public Map<String,String> getNameAndTextMapForElement(Element element){
        Map<String,String> nameAndTextMap=Maps.newHashMap();
        for (Object e: element.elements()) {
            nameAndTextMap.put(((Element) e).getName(), ((Element)e).getTextTrim());
        }

        return nameAndTextMap;
    }

    public Map<String,String> getNameAndTextForAllElements(){
        return getNameAndTextMapForElement(root);
    }

    public Map<String,String> getNameAndTextForElement(String nodeName){
        Map<String,String> nameAndTextMap=Maps.newHashMap();
        List nodes = doc.selectNodes(nodeName);
        for (Object node : nodes) {
            nameAndTextMap.putAll(getNameAndTextMapForElement((Element)node));
        }
        return nameAndTextMap;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public Element getRoot() {
        return root;
    }

    public void setRoot(Element root) {
        this.root = root;
    }

    public Document getDoc() {
        return doc;
    }

}
