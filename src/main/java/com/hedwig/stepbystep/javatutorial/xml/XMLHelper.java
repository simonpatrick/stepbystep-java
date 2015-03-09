package com.hedwig.stepbystep.javatutorial.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by patrick on 15/3/9.
 *
 * @version $Id$
 */


public class XMLHelper {
    private XMLHelper(){}
    private Document doc;
    private static final Logger logger = LogManager.getLogger(XMLHelper.class.getName());

    public static XMLHelper build(String path){
        XMLHelper helper = new XMLHelper();
        SAXReader reader = new SAXReader();
        try {
            helper.setDoc(reader.read(ClassLoader.getSystemResourceAsStream(path)));
        } catch (DocumentException e) {
            logger.error("load page object resource file failed, error={}", e);
        }

        return helper;
    }

    public String getText(String nodeName){

        return null;
    }

    public String getAttribute(String nodeName,String attributeName){

        return null;
    }

    public static void main(String[] args) throws IOException, DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(ClassLoader.getSystemResourceAsStream("pages/BaiduHomePageResource.xml"));
        System.out.println(doc.getRootElement());
        Element root = doc.getRootElement();
        System.out.println(root.elements());
        Iterator iter = root.elementIterator();
        while(iter.hasNext()){
           Node node = (Element)iter.next();
            System.out.println(node.getName());
            System.out.println(node.getText());
        }
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }
}
