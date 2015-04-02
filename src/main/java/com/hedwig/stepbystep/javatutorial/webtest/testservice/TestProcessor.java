package com.hedwig.stepbystep.javatutorial.webtest.testservice;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by patrick on 15/4/2.
 * process the tests
 * @version $Id$
 */


public class TestProcessor {
    private List<ITestAction> testActions;
    private List<ICheckPoint> checkPoints;

    void process(){

        for (ITestAction action : testActions) {
            action.process();
            ICheckPoint point = getCheckPointByAction(action);
            if(getCheckPointByAction(action)!=null){
                point.verify();
            }

            //add test result
        }
    }

    private ICheckPoint getCheckPointByAction(ITestAction action){
        for (ICheckPoint checkPoint : checkPoints) {
            if(checkPoint.where().equals(action.getClass()))
                return checkPoint;
        }

        return null;
    }

    public void addActions(ITestAction ... actions){
        for (ITestAction action : actions) {
            testActions.add(action);
        }
    }


}
