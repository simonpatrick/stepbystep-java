package com.hedwig.testlogs;

import com.hedwig.testlogs.listeners.TestNgListenerWithTestStepLogger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;


public class TestNGEnhancedTest {
    private TestNgListenerWithTestStepLogger eventListener;

    private synchronized void  initEventListener(ITestContext context) {

        for (ITestListener listener : ((TestRunner) context).getTestListeners()) {
            if (listener instanceof TestNgListenerWithTestStepLogger) {
                eventListener = (TestNgListenerWithTestStepLogger) listener;
                return;
            }
        }

    }

    private synchronized TestNgListenerWithTestStepLogger getEventListener(ITestContext context) {
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