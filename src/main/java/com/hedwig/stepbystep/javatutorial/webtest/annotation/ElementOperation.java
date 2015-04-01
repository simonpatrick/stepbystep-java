package com.hedwig.stepbystep.javatutorial.webtest.annotation;

/**
 * Created by patrick on 15/4/1.
 *
 * @version $Id$
 */


public @interface ElementOperation {
    String name() ;
    String action() default "";
}
