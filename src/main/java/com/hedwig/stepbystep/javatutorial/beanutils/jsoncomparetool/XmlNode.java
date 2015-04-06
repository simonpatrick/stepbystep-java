package com.hedwig.stepbystep.javatutorial.beanutils.jsoncomparetool;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlNode implements TreeNode {

    private Node node;

    public XmlNode(Node node) {
        this.node = node;
    }

    public static TreeNode loadDocument(InputSource in) throws SAXException, IOException, ParserConfigurationException {
        return new XmlNode(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(in).getDocumentElement());
    }

    public static TreeNode loadDocument(File file) throws SAXException, IOException, ParserConfigurationException {
        return new XmlNode(DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file).getDocumentElement());
    }

    @Override
    public String getValue() {
        if (node.getChildNodes() != null && node.getChildNodes().getLength() == 1
                && node.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE) {
            return node.getChildNodes().item(0).getTextContent();
        }
        return null;
    }

    @Override
    public String getName() {
        return node.getNodeName();
    }

    @Override
    public List<TreeNode> getChildren() {
        NodeList nodeList = node.getChildNodes();
        List<TreeNode> rt = new ArrayList<TreeNode>();
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                rt.add(new XmlNode(node));
            }
        }
        return rt;
    }

    @Override
    public String getValueType() {
        return TreeNode.STRING_TYPE;
    }
}
