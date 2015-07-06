package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import org.joda.time.DateTime;

import java.io.Serializable;

public abstract class AbstractRecording<
        PK extends Serializable & Comparable<PK>, E extends AbstractRecording<PK, E>>
        extends AbstractEntity<PK, E> implements Recording {

    private static final long serialVersionUID = 1L;

    private DateTime createdDate;
    private DateTime lastModifiedDate;

    @Override
    public DateTime getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public DateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    @Override
    public void setLastModifiedDate(DateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
