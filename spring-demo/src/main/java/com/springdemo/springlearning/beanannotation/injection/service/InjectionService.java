package com.springdemo.springlearning.beanannotation.injection.service;


import com.springdemo.springlearning.beanannotation.injection.dao.BeanAnnotationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InjectionService {
    private static final Logger logger = LoggerFactory.getLogger(InjectionService.class.getName());
    @Autowired
    private BeanAnnotationDao dao;

//    public InjectionService(BeanAnnotationDao dao) {
//        this.dao = dao;
//    }

    public void post(String message){
        logger.info("this inbound request :{}", message);
        dao.save();
    }

    public BeanAnnotationDao getDao() {
        return dao;
    }

    public void setDao(BeanAnnotationDao dao) {
        logger.info("set is executed");
        this.dao = dao;
    }
}
