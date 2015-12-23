package com.hedwig.testng.globallogging;

import com.google.common.collect.Lists;

import java.util.LinkedList;
import java.util.List;

/**
 * One Thread,one Running Test Case
 * Created by patrick on 15/11/16.
 */
public class TestStepCollectors {

    private TestStepCollectors(){}
    private static ThreadLocal<LinkedList<TestCaseLog>> steps = new ThreadLocal<>();

    public static void initTestCaseLog(String testCaseName){
        LinkedList<TestCaseLog> logs = steps.get();
        if(null!=logs){
            if(!testCaseName.equalsIgnoreCase(logs.getLast().getTestCaseName())){
                logs.getLast().setIsCompleted(true);
                logs.add(new TestCaseLog(testCaseName));
            }
        }else{
            LinkedList<TestCaseLog> initLogs = new LinkedList<>();
            initLogs.add(new TestCaseLog(testCaseName));
            steps.set(initLogs);
        }

    }

    private static TestCaseLog getTestCaseLogCollector(){

        if(null== steps.get()) throw new RuntimeException("TestStepCollectors is not initialized");
//        if(null== steps.get()) throw new RuntimeException("TestStepCollectors is not initialized");
        if(steps.get().getLast().isCompleted) throw new RuntimeException("something is run " +
                "as the test case collector is closed,maybe " +
                "it is because new test case logger is not initialized");
        return steps.get().getLast();
    }

    public static void pushLog(String ...msg){
        getTestCaseLogCollector().getLogs().add(String.join("|",msg));
    }

    public static void pushLog(Object msg){
        getTestCaseLogCollector().getLogs().add(msg.toString());
    }

    public static void setTestCaseCompleted(String testCase){
        if(!testCase.equalsIgnoreCase(getTestCaseLogCollector().getTestCaseName()))
            throw new RuntimeException("please make sure there is one test case running in one thread," +
                    "otherwise it messes up everything,please check your testcase setting");
        TestResultLogger.logWOTestStep(steps.get().toString());
        getTestCaseLogCollector().setIsCompleted(true);

    }

    public static ThreadLocal<LinkedList<TestCaseLog>> getSteps() {
        return steps;
    }

    public static void setSteps(ThreadLocal<LinkedList<TestCaseLog>> steps) {
        TestStepCollectors.steps = steps;
    }

    public static class TestCaseLog{
        private String testCaseName;
        private List<String> logs = Lists.newArrayList();
        private boolean isCompleted =false;

        public TestCaseLog(String testCaseName) {
            this.testCaseName = testCaseName;
        }

        public String getTestCaseName() {
            return testCaseName;
        }

        public void setTestCaseName(String testCaseName) {
            this.testCaseName = testCaseName;
        }

        public List<String> getLogs() {
            return logs;
        }

        public void setLogs(List<String> logs) {
            this.logs = logs;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

        public void setIsCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
        }

        @Override
        public String toString() {
            return "TestCaseLog{" +
                    "testCaseName='" + testCaseName + '\'' +
                    ", logs=" + logs +
                    ", isCompleted=" + isCompleted +
                    '}';
        }
    }
}
