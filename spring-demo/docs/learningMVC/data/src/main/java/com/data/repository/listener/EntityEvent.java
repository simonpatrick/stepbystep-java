package com.springdemo.learningMVC.data.src.main.java.com.data.repository.listener;

import com.data.qdomain.Entity;

import java.io.Serializable;

public class EntityEvent implements Serializable {

    private static final long serialVersionUID = -7868521345677442303L;

    private final Entity<?, ?> entity;

    public EntityEvent(Entity<?, ?> entity) {
        this.entity = entity;
    }

    public Entity<?, ?> getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return String.format("EntityEvent{entity=%s}", entity);
    }
}
