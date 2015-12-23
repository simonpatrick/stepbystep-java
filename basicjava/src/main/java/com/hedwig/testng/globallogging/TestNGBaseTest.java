package com.hedwig.testng.globallogging;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.util.List;


/**
 * Created by patrick on 15/11/16.
 */
public class TestNGBaseTest {
    private TestStepCollectorTestListener eventListener;

    private synchronized void  initEventListener(ITestContext context) {

       List<ITestListener> listeners =((TestRunner)context).getTestListeners();
        eventListener = new TestStepCollectorTestListener();
        listeners.add(eventListener);
    }

    private synchronized TestStepCollectorTestListener getEventListener(ITestContext context) {
        if (eventListener == null) {
            initEventListener(context);
        }

        return eventListener;
    }

    @BeforeMethod(alwaysRun = true)
    public void init(ITestContext context) {
        TestResultLogger.logWOTestStep("start driver......");
        TestResultLogger.logWOTestStep("Thread is " +
                Thread.currentThread().getName() + ";" +
                "ThreadId:" + Thread.currentThread().getId());
        getEventListener(context);
    }

    @AfterMethod(alwaysRun = true)
    public void after(ITestContext context) {
        try {
            TestResultLogger.logWOTestStep("end of testing!");
        } finally {
            TestResultLogger.logWOTestStep("finally");
        }
    }
}
