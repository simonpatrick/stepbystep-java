package com.springdemo.learningMVC.common.src.main.java.com.common.web.servlet;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response,
            Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            if (hm.getBean() instanceof CleanAware) {
                ((CleanAware) hm.getBean()).cleanUp();
            }
        }
    }
}
