package io.hedwing.java8samples.files;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collections;
import java.util.List;

/**
 * Created by patrick on 16/1/6.
 */
public class FileUtils {

    private String workspace = workspace();
    private static final String EMPTY = "";

    private String workspace() {
        try {
            return URLDecoder.decode(FileUtils.class.getClassLoader().getResource("").getPath(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean isDir() {
        System.out.println(workspace);
        return new File(workspace).isDirectory();
    }

    public void recordViolations(){
        recordViolations(workspace);

    }
    public void recordViolations(String path) {
        File file = new File(path);
        System.out.println(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                recordViolations(f.getAbsolutePath());
            }
        } else {
            if (file.getName().toUpperCase().contains(".XML")) {
                List<Node> nodes = getSqlMapperNodes(file.getAbsolutePath());
                if (nodes.size() > 0) {
                    recordViolationForASqlMapper(nodes);
                }
            }
        }
    }

    private void recordViolationForASqlMapper(List nodes) {
        //select with no lock
        // violation for sql injection
        for (Object node : nodes) {
            List<Element> children = ((Element)node).elements();
            for (Element child : children) {
                if(child.getQName().getName().contains("select")){
                    //process with(nolock)
                }
                if(child.getText().contains("$")){
                    System.out.println(child.getText());
                    System.out.println("error here");
                }
            }
        }
    }

    public List getSqlMapperNodes(String path) {
        try {
            Document document  = new SAXReader().read(path);
            return document.selectNodes("//mapper | //sqlMap");
        } catch (DocumentException e) {
            return Collections.emptyList();
        }
    }
}
