package com.hedwig.stepbystep.javatutorial.quickstart;

import org.testng.annotations.*;

import static org.testng.Assert.assertTrue;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class TestngAnnotationDemo {

    @BeforeClass
    public void beforeClass(){
        System.out.println("Before Class");
    }

    @BeforeSuite
    public void beforeSuite(){
        System.out.println("Before Suite");
    }

    @BeforeTest
    public void beforeTest(){
        System.out.println("Before Test");
    }
    @BeforeMethod
    public void beforeMethod(){
        System.out.println("Before Method");
    }
    @AfterClass
    public void afterClass(){
        System.out.println("After Class");
    }

    @AfterTest
    public void afterTest(){
        System.out.println("after test");
    }
    @AfterMethod
    public void afterMethod(){
        System.out.println("after method");
    }
    @AfterSuite
    public void AfterSuite(){
        System.out.println("After Suite");
    }

    @Test
    public void allReadyCorrect(){
        System.out.println("start testing");
        assertTrue(true,"always true");
    }
}
