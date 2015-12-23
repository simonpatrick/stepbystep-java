package com.hedwig.testlogs.listeners;

import com.hedwig.testlogs.internalmodel.TestCase;

import java.util.LinkedList;

/**
 * Created by patrick on 15/11/16.
 */
public class TestStepLoggerListener {

    private ThreadLocal<LinkedList<TestCase>> testCasesLogs = new ThreadLocal<>();
    private static TestStepLoggerListener instance = new TestStepLoggerListener();

    private TestStepLoggerListener(){}

    public static TestStepLoggerListener getInstance(){
//        if(null==instance) {
//            instance=new TestStepLoggerListener();
//        }
        return instance;
    }

    /**
     * initTestCase single test case logger
     * @param testCase
     */
    public void initTestCaseLogger(TestCase testCase){
        LinkedList<TestCase> tests = testCasesLogs.get();
        if(null!=tests){
            if(!testCase.getTestDescription().
                    equalsIgnoreCase(tests.getLast().getTestDescription())) {
                tests.getLast().setIsCompleted(true);
                tests.add(testCase);
            }
        }else{
            LinkedList<TestCase> initLogs = new LinkedList<>();
            initLogs.add(testCase);
            testCasesLogs.set(initLogs);
        }
    }

    /**
     * push step log message into test case in current thread
     * @param msg
     */
    public void pushLog(String ...msg){
        TestCase tc = getTestCaseLogCollector();
//        if(tc.isCompleted()) throw new RuntimeException("something is wrong " +
//                "as the test case collector is closed," +
//                "the reason might be new test case logger is not initialized");
        tc.getLogs().add(String.join("|",msg));
    }

    /**
     * push step log message into test case in current thread
     * @param msg
     */
    public void pushLog(Object msg){
        TestCase tc = getTestCaseLogCollector();
//        if(tc.isCompleted()) throw new RuntimeException("something is wrong " +
//                "as the test case collector is closed," +
//                "the reason might be new test case logger is not initialized");
        tc.getLogs().add(msg.toString());
    }

    /**
     * get current test case in the running thread
     * @return
     */
    private synchronized TestCase getTestCaseLogCollector(){

        if(null== testCasesLogs.get()) throw new RuntimeException("TestStepCollectors is not initialized");
        return testCasesLogs.get().getLast();
    }

    /**
     * set running test case to completed
     * @param testCase
     */
    public void completeTestCaseLog(TestCase testCase){
        getTestCaseLogCollector().setIsCompleted(true);
    }
}
