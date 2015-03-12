package com.hedwig.stepbystep.javatutorial.testng.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.util.Map;

/**
 * Created by patrick on 15/3/12.
 *
 * @version $Id$
 */


public class SimpleSuiteListener implements ISuiteListener {
    private static final Logger logger = LogManager.getLogger(SimpleSuiteListener.class.getName());
    @Override
    public void onStart(ISuite suite) {
        logger.info("suite_name ={}",suite.getName());
        logger.info("--all_methods = {}",suite.getAllInvokedMethods());
    }

    @Override
    public void onFinish(ISuite suite) {
        for ( Map.Entry entry: suite.getResults().entrySet()) {
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }
        logger.info("test_result"+suite.getResults());
    }
}
