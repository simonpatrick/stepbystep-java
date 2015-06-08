package com.hedwig.stepbystep.javatutorial.testng.experiement;

import com.google.common.collect.Lists;
import com.hedwig.stepbystep.javatutorial.helpers.StringHelper;
import org.testng.ITestContext;
import org.testng.annotations.*;
import sun.rmi.runtime.Log;
import sun.rmi.transport.ObjectTable;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * Created by patrick on 15/6/8.
 *
 * @version $Id$
 */


public class TestSample {

    String priority ="";
    @BeforeClass
    public void init(ITestContext context){
        System.out.println(context.getName());
        System.out.println(context.getAttribute("priority"));
        System.out.println(context.getAttributeNames());
        System.out.println("All Parameters"+context.getCurrentXmlTest().getAllParameters());
        System.out.println("Test Parameters:"+context.getCurrentXmlTest().getTestParameters());
        String test=context.getCurrentXmlTest().getAllParameters().get("priority");
        priority=test==null?"":test.toString();
    }

    @DataProvider(name = "data",parallel = true)
    public Object[][] getData(Method me){
        System.out.println(me.getName());
        TestCaseDescription d = new TestCaseDescription();
        d.setDescription("testing");
        d.setMethodName("test123"); //method filter
        d.setPriority("smoketesting");

        Object[][] y=null;
        if(me.getName().equalsIgnoreCase(d.getMethodName())&&Priority.isApplied(priority,d.getPriority())) {
            y= new Object[1][1];
            y[0][0] = d;
        }
        return y;
    }

    @Test(dataProvider = "data",invocationCount = 5,threadPoolSize = 6)
    public void test123(TestCaseDescription d){
        Logging.log("testing message");
        System.out.println("start testing");
        System.out.println(d);
        Logging.log("after message");
    }

    @AfterMethod(alwaysRun = true)
    public void printResult(){
           List<String> steps= Logging.getLoggers().get().getSteps();
        for (String step : steps) {
            System.out.println(Thread.currentThread().getId()+":"+step);
        }
    }

    public static enum Priority{
        SmokeTesting("SmokeTesting",100),P1("P1",99),P2("P2",98),P3("P3",93),Others("Others",0);
        private int value;
        private String name;

        Priority(String name,int value) {
            this.value = value;
            this.name=name;
        }

        public static Priority getPriorityByName(String name){
            if(!StringHelper.isNotEmptyOrNotBlankString(name)) return Others;
            for (Priority priority : Priority.values()) {
                if(priority.getName().equalsIgnoreCase(name)) return priority;
            }

            return Others;
        }

       public static boolean isApplied(String configuredPriority,String priority){
            int val = getPriorityByName(configuredPriority).getValue();
            int p = getPriorityByName(priority).getValue();
            return p-val>=0?true:false;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
