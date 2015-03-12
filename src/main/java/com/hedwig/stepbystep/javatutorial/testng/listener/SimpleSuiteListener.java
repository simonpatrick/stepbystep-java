package com.hedwig.stepbystep.javatutorial.testng.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;

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
        logger.info("test_result"+suite.getResults());
    }
}
