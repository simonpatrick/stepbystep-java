package com.hedwig.stepbystep.javatutorial.advanced;

import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class TestngGetTestContext {

    @BeforeSuite
    public void init(ITestContext context){
        System.out.println(context.getSuite().getName());
        System.out.println(context.getSuite().getXmlSuite().getListeners());
        System.out.println(((TestRunner)context).getTestListeners());
        System.out.println(context);
    }

    @Test
    public void test_01(){
        System.out.println("test_01");
    }
}
