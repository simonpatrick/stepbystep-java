package com.springdemo.springlearning.ioc.service;

import com.interview.springlearning.ioc.dao.InjectionDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InjectionServiceImpl implements InjectionService {
    private static final Logger logger = LogManager.getLogger(InjectionServiceImpl.class.getName());
    private InjectionDao dao;

    public InjectionServiceImpl(InjectionDao dao) {
        this.dao = dao;
    }

    public InjectionServiceImpl() {
    }

    @Override
    public void post() {
        logger.info("start save injection dao data");
        dao.save();
    }

    public InjectionDao getDao() {
        return dao;
    }

    public void setDao(InjectionDao dao) {
        this.dao = dao;
    }
}
