package com.hedwig.stepbystep.javatutorial.webtest.seleniumide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

public class GeneratePageObjectFromHtmlTemplate {

    public static void main(String[] args) throws IOException {
        String path = GeneratePageObjectFromHtmlTemplate.class.getResource("/Survey.ept").getPath();
        GeneratePageObjectFromHtmlTemplate t = new GeneratePageObjectFromHtmlTemplate();
        File file = new File(path);
        StringBuilder declare = new StringBuilder();
        StringBuilder constructure = new StringBuilder();

        if(file.exists()) {
            Document doc = Jsoup.parse(file, "UTF-8");
            Elements elements = doc.getElementsByTag("input");
            elements.addAll(doc.getElementsByTag("select"));
            elements.addAll(doc.getElementsByTag("button"));
            elements.addAll(doc.getElementsByTag("radiobutton"));
            for (Element element : elements) {
                Statement statement = t.generateStatement(element);
                declare.append(statement.getDeclareStatement()+"\n");
                constructure.append(statement.getConstructureStatment()+"\n");
            }
        }

        System.out.println(declare.toString());
        System.out.println(constructure.toString());
    }

    public Statement generateStatement(Element inputElement){
        String statement = "private  ";
        String consturctureStatment ;
        String fieldType ;
        if(inputElement.tagName().equalsIgnoreCase("select")){
            fieldType="Select ";
        }else {
            if (inputElement.attr("type").equalsIgnoreCase("text")) {
                fieldType = "TextField ";
            } else if (inputElement.attr("type").equalsIgnoreCase("BUTTON")) {
                fieldType = "Button ";
            } else {
                fieldType = "BaseHtmlElement ";
            }
        }
       statement=statement+fieldType;

       if(null!=inputElement.attr("id")){
           statement=statement+inputElement.attr("id")+";";
           consturctureStatment = inputElement.attr("id")+"=new "+fieldType+"(driver,"
                   +"\"id="+inputElement.attr("id")+"\")";
       }else{
           statement=statement+inputElement.attr("name");
           consturctureStatment = inputElement.attr("name")+"=new "+fieldType+"(driver,"
                   +"\"name="+inputElement.attr("name")+"\");";
       }

        return new Statement(statement,consturctureStatment);
    }

    public class Statement{
        private String declareStatement ;
        private String constructureStatment;

        public Statement(String declareStatement, String constructureStatment) {
            this.declareStatement = declareStatement;
            this.constructureStatment = constructureStatment;
        }

        public String getDeclareStatement() {
            return declareStatement;
        }

        public void setDeclareStatement(String declareStatement) {
            this.declareStatement = declareStatement;
        }

        public String getConstructureStatment() {
            return constructureStatment;
        }

        public void setConstructureStatment(String constructureStatment) {
            this.constructureStatment = constructureStatment;
        }
    }

}
