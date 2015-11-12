package com.hedwig.stepbystep.javatutorial.webtest.seleniumide;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeneratorFromSeleniumIDE {
    private static final Logger logger = LogManager.getLogger(GeneratorFromSeleniumIDE.class.getName());
    private List<SeleniumIDERecordUnit> units;

    public GeneratorFromSeleniumIDE() {
        units= new ArrayList<SeleniumIDERecordUnit>();
    }

    public void parse(File file) throws IOException {
        if(file.exists()){
            Document doc = Jsoup.parse(file,"UTF-8");
            Elements tbody = doc.getElementsByTag("tbody");
            for (Element element : tbody) {
                Elements trs = element.getElementsByTag("tr");
                for (Element tr : trs) {
                    Elements tds = tr.getElementsByTag("td");
                    this.units.add(new SeleniumIDERecordUnit(tds.get(0).text(), tds.get(1).text()
                            , tds.get(2).text()));
                }
            }
        }
    }



    public void printStatement(File file) throws IOException {
        parse(file);
        System.out.println(generateStatement());
        System.out.printf(generateConstructor());
    }

    private String generateStatement(){

        StringBuffer sb = new StringBuffer("private IWebTestDriver driver;\n");
        String type,name,locatorExpression;
        for (SeleniumIDERecordUnit unit : units) {
            String declaredMsg = "private $type $name;\n";
            //todo add string handle here to transfer different statement

            declaredMsg=declaredMsg.replace("$type",unit.getType());
            declaredMsg=declaredMsg.replace("$name",unit.getVariantName());
            sb.append(declaredMsg);
        }
        return sb.toString();
    }

    private String generateConstructor(){
        StringBuffer sb = new StringBuffer("this.driver=driver\n");
        for (SeleniumIDERecordUnit unit : units) {
            if(!unit.getType().equalsIgnoreCase("IWebTestDriver")){
                sb.append(unit.getVariantName()+"=new "+unit.getType()+"(driver,"
                        +"\""+unit.getLocatorExpression()+"\");\n");
            }
        }
        return sb.toString();
    }



    public static void main(String[] args) throws IOException {
        String path = "C:\\Users\\wuke\\Desktop\\seleniumIDERecord\\ѧ����ע������.html";
        File file = new File(path);
        GeneratorFromSeleniumIDE generator= new GeneratorFromSeleniumIDE();
        generator.printStatement(file);
    }

    public List<SeleniumIDERecordUnit> getUnits() {
        return units;
    }

    public void setUnits(List<SeleniumIDERecordUnit> units) {
        this.units = units;
    }
}
