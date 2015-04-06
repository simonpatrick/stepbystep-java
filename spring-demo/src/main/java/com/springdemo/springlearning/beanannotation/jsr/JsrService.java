package com.springdemo.springlearning.beanannotation.jsr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class JsrService {
    private static final Logger logger = LogManager.getLogger(JsrService.class.getName());

    private JsrDao dao;

    public JsrService() {
        logger.info("start create Jsr Service");
    }

    public JsrDao getDao() {
        return dao;
    }

    @Inject
    public void setDao(JsrDao dao) {
        this.dao = dao;
    }

    @PostConstruct
    public void init(){
        logger.info("JsrService is init");
    }

    @PreDestroy
    public void destroy(){
        logger.info("JsrService is destroyed");
    }

    public void save(){
        dao.save();
    }
}
