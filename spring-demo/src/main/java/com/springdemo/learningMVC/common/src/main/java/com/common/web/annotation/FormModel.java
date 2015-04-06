package com.springdemo.learningMVC.common.src.main.java.com.common.web.annotation;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FormModel {

    String value() default "";
}
