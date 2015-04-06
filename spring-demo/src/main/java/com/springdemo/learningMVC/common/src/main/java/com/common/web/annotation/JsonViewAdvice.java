package com.springdemo.learningMVC.common.src.main.java.com.common.web.annotation;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;

@ControllerAdvice(annotations = RestController.class)
public class JsonViewAdvice extends JsonViewResponseBodyAdvice {

    public JsonViewAdvice() {
        super();
    }
}
