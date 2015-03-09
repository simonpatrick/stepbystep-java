package com.hedwig.stepbystep.javatutorial.testng.listener;

import com.hedwig.stepbystep.javatutorial.io.FileHelper;
import org.testng.IInvokedMethod;
import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by patrick on 15/3/6.
 *
 * @version $Id$
 */


public class SimpleReporter implements IReporter{
    private String finalResult ;
    private String methodResult;

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        System.out.println(outputDirectory);
        System.out.println(xmlSuites);
        System.out.println(suites);
        System.out.println(suites.get(0).getResults());
        System.out.println("---------------------------------------");
        File file =FileHelper.createFile(outputDirectory,"test_result.txt");
        for (ISuite suite : suites) {
            generateSuite(suite,file);
            generateTestName(suite,file);
        }

    }

    private void generateSuite(ISuite suite,File file) {
        FileHelper.writeToFile(file.getAbsolutePath(),suite.getName());
    }


    private void generateFinalTestResult(ISuite suite){
        boolean flag = false;
        if(!suite.getSuiteState().isFailed()){
            flag =true;
        }else{
            for (IInvokedMethod iInvokedMethod : suite.getAllInvokedMethods()) {

            }
        }
    }



    private void generateTestName(ISuite suite,File file){
        FileHelper.writeToFile(file.getAbsolutePath(),"TestName:"+suite.getXmlSuite().getTest());
    }

    private void generateTestClass(){}

    private void generateTestMethod(){}


}
