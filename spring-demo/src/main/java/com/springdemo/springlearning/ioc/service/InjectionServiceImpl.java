package com.springdemo.springlearning.ioc.service;

import com.springdemo.springlearning.ioc.dao.InjectionDao;

public class InjectionServiceImpl implements InjectionService {
    private InjectionDao dao;

    public InjectionServiceImpl(InjectionDao dao) {
        this.dao = dao;
    }

    public InjectionServiceImpl() {
    }

    @Override
    public void post() {
        dao.save();
    }

    public InjectionDao getDao() {
        return dao;
    }

    public void setDao(InjectionDao dao) {
        this.dao = dao;
    }
}
