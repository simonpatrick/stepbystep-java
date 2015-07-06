package com.springdemo.learningMVC.data.src.main.java.com.data.redis;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.data.redis.hash.HashMapper;

import java.util.Map;

public class BeanUtilsBeanHashMapper<T> implements HashMapper<T, String, String> {

    private final Class<T> type;
    private final BeanUtilsBean bub;

    public BeanUtilsBeanHashMapper(Class<T> type) {
        this(type, BeanUtilsBean.getInstance());
    }

    public BeanUtilsBeanHashMapper(Class<T> type, BeanUtilsBean bub) {
        super();
        this.type = type;
        this.bub = bub;
    }

    @Override
    public Map<String, String> toHash(T object) {
        try {
            return bub.describe(object);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Cannot describe object " + object);
        }
    }

    @Override
    public T fromHash(Map<String, String> hash) {
        T instance = org.springframework.beans.BeanUtils.instantiate(type);
        try {
            bub.populate(instance, hash);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return instance;
    }

    public BeanUtilsBean getBeanUtilsBean() {
        return bub;
    }
}
