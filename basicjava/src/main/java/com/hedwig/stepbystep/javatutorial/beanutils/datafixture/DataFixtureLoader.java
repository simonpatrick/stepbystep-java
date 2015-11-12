package com.hedwig.stepbystep.javatutorial.beanutils.datafixture;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by kewu on 14-2-28.
 */
public class DataFixtureLoader {

    private static String[] getHeaders(String line){
        String header[] =null;
        header = line.split(",");
        for (int j = 0; j < header.length; j++) {

            String[] names = header[j].split("_");
            StringBuilder sb = new StringBuilder();
            for (int n = 0; n < names.length; n++) {
                if (n == 0) {
                    sb.append(names[n].toLowerCase());
                } else {
                    sb.append(StringUtils.capitalize(names[n].toLowerCase()));
                }
            }

            header[j] = sb.toString();
        }

        return header;
    }

    private static String buildKey(String[] headers,String line){ //key index
        String[] values =line.split(",");
        String key = "";
        for (int i = 0; i <12 ; i++) {
            if(i<12){
                key=key+values[i]+",";
            }
        }

        return key.substring(0,key.length()-1);
    }

    private static String buildValue(String[]headers,String line){//index

        String[] values =line.split(",");
        String result= "";
        String template ="{%s}";
        for (int i = 12; i <headers.length ; i++) {

            result=result+"\""+headers[i]+"\""+ ":"+"\""+values[i]+"\""+"-";
        }

        return String.format(template, result.substring(0, result.length() - 1));

    }

    public static void main(String[] args) throws IOException {
       // read line, get first ID
       String path = ClassLoader.getSystemClassLoader()
               .getResource("templateForTestCases.csv").getPath();

        File file = new File(path);
        List<String> lines=FileUtils.readLines(file);
        Map<String,String> result = new LinkedHashMap<String,String>();
        String headers[] = null;
        for (int i = 0; i <lines.size() ; i++){
            if (lines.get(i).contains("ID")) {
               headers=getHeaders(lines.get(i));

            }else{

                String key = buildKey(headers, lines.get(i));
                String value = buildValue(headers, lines.get(i));
                if(null!=result.get(key)){
                    result.put(key,result.get(key)+"&&"+value);
                }else{
                    result.put(key,value);
                }

            }

            for (String s : headers) {
                System.out.println(s);
            }

          }

        File file1 = new File("test.csv");
        if(file1.exists()){
            file1.delete();
            file1.createNewFile();
        }else{
            file1.createNewFile();
        }

        List<String> content = new ArrayList<String>();
        String h ="TestObject.TestCaseId,TestObject.TestMethod,TestObject.TestTitle,Event.eventId,Users.sellerContactid," +
                "User.userGroupList,User.defaultEmail,ExpectedEventFulfillmentWindow.isWindowShow,ExpectedEventFulfillmentWindow.targetWindowId,"+
                "DataFixture.baseId,DataFixture.action,DataFixture.tableName,DataFixture.data";
        content.add(h);
        String pref="%s,";

        for (String s : result.keySet()) {


            String[] testData = s.split(",");
            String prefReal=String.format(pref,testData[0]);
            String key1 ="";
            for (int i = 1; i <12 ; i++) {
               key1=key1+testData[i]+",";
            }

            String  realTestData =prefReal+key1+result.get(s);
            content.add(realTestData);
            System.out.println(realTestData);
        }

        FileUtils.writeLines(file1,content);
    }
}
