package com.hedwig.testng.globallogging;

/**
 * Created by patrick on 15/11/16.
 */
public class MessageConverter {

    public static void convert(String marker,String ... keyInfos){
        String msg = marker;
        for (String message : keyInfos) {
            msg= msg.replaceFirst("\\{\\}",message);
        }

        System.out.println(msg);
    }

    public static void main(String[] args) {
        convert("testrestult={},step={}","test1","step1");
    }
}
