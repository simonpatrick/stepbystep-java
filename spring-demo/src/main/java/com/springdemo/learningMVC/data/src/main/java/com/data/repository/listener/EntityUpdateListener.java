package com.springdemo.learningMVC.data.src.main.java.com.data.repository.listener;

public interface EntityUpdateListener {

    public void preUpdate(EntityEvent event);

    public void afterUpdate(EntityEvent event);
}
