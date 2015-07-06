package com.springdemo.learningMVC.common.src.main.java.com.common.web.context;

import org.springframework.util.ClassUtils;
import org.springframework.web.context.ContextLoaderListener;

import javax.servlet.ServletContextEvent;

/**
 */
public class CustomContextLoaderListener extends ContextLoaderListener {

    private SpringContextInitAdvisor contextInitAdvisor;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        initContextInitAdvisor(event);
        beforeRunning();
        super.contextInitialized(event);
        afterRunning();
    }

    private void beforeRunning() {
        if (contextInitAdvisor != null) {
            contextInitAdvisor.beforeInit();
        }
    }

    private void afterRunning() {
        if (contextInitAdvisor != null) {
            contextInitAdvisor.afterInit();
        }
    }

    @SuppressWarnings("unchecked")
    private void initContextInitAdvisor(ServletContextEvent event) {
        String advisorClassname = event.getServletContext()
                .getInitParameter("contextInitAdvisor");
        if (advisorClassname == null) {
            return; // do anything
        }
        Class<SpringContextInitAdvisor> ciaClass;
        try {
            ciaClass = (Class<SpringContextInitAdvisor>) ClassUtils.forName(advisorClassname,
                    ClassLoader.getSystemClassLoader());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        try {
            contextInitAdvisor = ciaClass.newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }
}
