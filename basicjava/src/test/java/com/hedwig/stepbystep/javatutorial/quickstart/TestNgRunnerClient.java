package com.hedwig.stepbystep.javatutorial.quickstart;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class TestNgRunnerClient {
    public static void main(String[] args) {
        TestListenerAdapter tla = new TestListenerAdapter();
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { TestNgQuickStart.class,
                TestNgAnnotaionAdvanced.class,
                TestngAnnotationDemo.class,TestNgQuickStartClassLevel.class });
        testng.addListener(tla);
        testng.run();
    }
}
