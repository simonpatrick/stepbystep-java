package com.springdemo.learningMVC.data.src.main.java.com.data.repository.listener;

import com.data.qdomain.Entity;
import com.data.qdomain.Recording;
import com.data.qdomain.VersionedEntity;
import org.joda.time.DateTime;

public class DefaultEntityInsertListener implements EntityInsertListener {

    @Override
    public void preInsert(EntityEvent event) {
        Entity<?, ?> entity = event.getEntity();

        if (entity instanceof VersionedEntity) {
            ((VersionedEntity) entity).setVersion(1);
        }
        if (entity instanceof Recording) {
            Recording recording = (Recording) entity;
            recording.setLastModifiedDate(DateTime.now());
            recording.setCreatedDate(DateTime.now());
        }
    }

    @Override
    public void afterInsert(EntityEvent event) {

    }
}