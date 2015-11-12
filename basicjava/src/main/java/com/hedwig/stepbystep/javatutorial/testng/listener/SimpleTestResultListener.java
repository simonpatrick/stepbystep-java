package com.hedwig.stepbystep.javatutorial.testng.listener;

import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.util.List;

/**
 * Created by patrick on 15/3/6.
 *
 * @version $Id$
 */


public class SimpleTestResultListener implements IReporter,ITestListener{

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        System.out.println("report");
   //     System.out.println(xmlSuites.toString());
        for (ISuite suite : suites) {
            System.out.println(suite.getName());
            for (IInvokedMethod iInvokedMethod : suite.getAllInvokedMethods()) {
                if(!iInvokedMethod.isConfigurationMethod()){
                    System.out.println("-- Invoked Method:"+iInvokedMethod.getTestMethod().getMethodName());
                    System.out.println("-- Invoked Method Test Result:"+iInvokedMethod.getTestResult().getStatus());
                }
            }
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("test started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        System.out.println("test pass");
        result.isSuccess();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("test failed");

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("skipped test");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        System.out.println("Percentage");
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("On Start:"+context.getName());
        System.out.println("On Start:"+context.getSuite().getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("On Test Suite failed:"+context.getSuite().getName());
    }
}
