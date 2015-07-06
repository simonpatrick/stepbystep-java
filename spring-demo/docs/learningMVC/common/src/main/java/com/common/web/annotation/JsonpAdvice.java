package com.springdemo.learningMVC.common.src.main.java.com.common.web.annotation;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Jsonp 响应体拦截处处理。
 *

 */
@ControllerAdvice(annotations = RestController.class)
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {

    public JsonpAdvice() {
        super("jsonp", "callback", "jsonpCallback");
    }
}
