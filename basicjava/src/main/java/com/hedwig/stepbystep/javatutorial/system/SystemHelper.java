package com.hedwig.stepbystep.javatutorial.system;

/**
 * Created by patrick on 15/3/6.
 *
 * @version $Id$
 */


public class SystemHelper {
    public static void main(String[] args) {
        String stepGuid = System.getProperty("stepguid");
        System.out.println(stepGuid);
    }
}
