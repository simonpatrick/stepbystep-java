package com.hedwig.stepbystep.javatutorial.webtest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/3/10.
 *
 * @version $Id$
 */


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ElementName {
    String name() default "";
}
