package com.hedwig.stepbystep.javatutorial.testng.listener;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class RetryListener implements IAnnotationTransformer{
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if(retry==null){
            annotation.setRetryAnalyzer(RetrySample.class);
        }
    }
}
