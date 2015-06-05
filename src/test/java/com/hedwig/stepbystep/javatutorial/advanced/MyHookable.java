package com.hedwig.stepbystep.javatutorial.advanced;

import org.testng.IHookCallBack;
import org.testng.IHookable;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class MyHookable implements IHookable {
    @Override
    public void run(IHookCallBack callBack, ITestResult testResult) {
        System.out.println("hookable is running");
        callBack.runTestMethod(testResult);
        System.out.println("hookable is finished");
    }

    @Test
    public void test_01(){
        System.out.println("test_01");
    }

    @Test
    public void test_02(){
        System.out.println("test_02");
    }

    @AfterMethod(alwaysRun = true)
    public void test_after(){
        System.out.println("after test_01");
    }
}
