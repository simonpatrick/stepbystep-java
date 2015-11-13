package com.hedwig.stepbystep.javatutorial.beanutils.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface Alias {

    String[] values() default "";
}
