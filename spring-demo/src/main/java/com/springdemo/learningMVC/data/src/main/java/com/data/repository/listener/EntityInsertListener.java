package com.springdemo.learningMVC.data.src.main.java.com.data.repository.listener;

public interface EntityInsertListener {

    public void preInsert(EntityEvent event);

    public void afterInsert(EntityEvent event);
}
