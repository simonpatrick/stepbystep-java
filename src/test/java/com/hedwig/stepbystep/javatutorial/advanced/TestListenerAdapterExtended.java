package com.hedwig.stepbystep.javatutorial.advanced;

import org.testng.IReporter;
import org.testng.ISuite;
import org.testng.TestListenerAdapter;
import org.testng.xml.XmlSuite;

import java.util.List;

/**
 * Created by patrick on 15/3/18.
 *
 * @version $Id$
 */


public class TestListenerAdapterExtended extends TestListenerAdapter implements IReporter{
    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        System.out.println("report is generated");
    }
}
