package com.hedwig.testlogs.adaptors;

import com.hedwig.testlogs.internalmodel.TestCase;
import com.hedwig.testlogs.externalmodel.TestDescription;
import org.testng.ITestResult;

/**
 * Created by patrick on 15/11/16.
 */
public class TestNGAdaptor {

    private TestNGAdaptor() {
    }

    public static TestCase covertToTestCase(TestCase tc, ITestResult result) {
        tc.setStartedMillis(result.getStartMillis());
        tc.setEndMills(result.getEndMillis());
        tc.setParameters(result.getParameters());
        tc.setTestClassName(result.getTestClass().getRealClass().getSimpleName());
        tc.setTestRealClassName(result.getTestClass().getRealClass().getName());
        tc.setStatus(result.getStatus());
        tc.setTestMethodName(result.getMethod().getMethodName());
        tc.setErrors(result.getThrowable());
        setTestDescription(tc,result);
        //todo update logs
        return tc;
    }

    public static void updateResult(TestCase tc,ITestResult result){
        tc.setStatus(result.getStatus());
        tc.setErrors(result.getThrowable());
        //todo update logs
    }

    private static void setTestDescription(TestCase tc, ITestResult result) {
        String des = null;
        for (Object o : result.getParameters()) {
            if (o instanceof TestDescription) {
                if (isNotEmptyOrNotBlankString(((TestDescription) o).getTestDescription())) {
                    des = ((TestDescription) o).getTestDescription();
                    break;
                }
            }
        }
        if (null == des) des = result.getMethod().getDescription();
        if(isNotEmptyOrNotBlankString(des)){
            tc.setTestDescription(des);
        }else{
            tc.setTestDescription(result.getMethod().getMethodName());
        }
    }

    private static boolean isNotEmptyOrNotBlankString(String source){
        if(source!=null) {
            source = source.trim();
            return source.length() != 0;
        }
        return false;
    }
}
