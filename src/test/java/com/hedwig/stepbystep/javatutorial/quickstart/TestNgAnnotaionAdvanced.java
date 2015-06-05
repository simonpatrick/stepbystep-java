package com.hedwig.stepbystep.javatutorial.quickstart;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class TestNgAnnotaionAdvanced {

    @Test(groups = {"P1","P2"})
    public void allReadyCorrect(){
        assertTrue(true,"always true");
    }

    @Test(dependsOnMethods ={"allReadyCorrect"} ,groups = {"P1","P2","P3"})
    public void allReadyCorrect_01(){
        System.out.println("waiting allReadyCorrect running");
        assertTrue(true,"always true");
    }

    @Test(expectedExceptions = RuntimeException.class,expectedExceptionsMessageRegExp = "throw",groups = {"P4"})
    public void allReadyCorrect_expectedException(){
        assertTrue(true,"always true");
        throw new RuntimeException("throw");
    }

    @Test(invocationCount = 20,threadPoolSize = 10,groups = {"P5"})
    public void allReadyCorrect_multipleThreads(){
        System.out.println(Thread.currentThread().getId());
        assertTrue(true,"always true");
    }

    //这个方法会服务于任何把它（测试方法）的数据提供者的名字为"test1"方法
    @DataProvider(name = "test1")
    public Object[][] createData1() {
        return new Object[][] {
                { "Cedric", new Integer(36) },
                { "Anne", new Integer(37)},
        };
    }

    //这个测试方法，声明其数据提供者的名字为“test1”
    @Test(dataProvider = "test1")
    public void verifyData1(String n1, Integer n2) {
        System.out.println(n1 + " " + n2);
    }
}
