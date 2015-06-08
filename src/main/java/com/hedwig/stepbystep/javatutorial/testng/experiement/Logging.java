package com.hedwig.stepbystep.javatutorial.testng.experiement;

import com.google.common.collect.Lists;

import java.util.Collections;
import java.util.List;

/**
 * Created by patrick on 15/6/8.
 *
 * @version $Id$
 */


public class Logging {

    private static ThreadLocal<Logging> loggers =new ThreadLocal<>();
    private List<String> steps = Collections.synchronizedList(Lists.newArrayList());

    public static ThreadLocal<Logging> getLoggers() {
        return loggers;
    }

    public static void setLoggers(ThreadLocal<Logging> loggers) {
        Logging.loggers = loggers;
    }

    public void addStepLog(String step){
        steps.add(step);
    }

    public static void log(String message){
        if(loggers.get()==null){
            loggers.set(new Logging());
        }

        //System.out.println(message);
        loggers.get().addStepLog(message);
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
