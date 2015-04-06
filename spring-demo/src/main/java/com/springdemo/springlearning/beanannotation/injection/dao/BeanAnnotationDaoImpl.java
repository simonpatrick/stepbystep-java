package com.springdemo.springlearning.beanannotation.injection.dao;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class BeanAnnotationDaoImpl implements BeanAnnotationDao{
    private static final Logger logger = LogManager.getLogger(BeanAnnotationDaoImpl.class.getName());

    @Override
    public void save() {
        logger.info("saving the result");
    }
}
