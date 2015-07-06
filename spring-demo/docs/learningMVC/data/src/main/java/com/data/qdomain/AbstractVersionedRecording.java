package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import java.io.Serializable;

public abstract class AbstractVersionedRecording<
        PK extends Serializable & Comparable<PK>, E extends AbstractVersionedRecording<PK, E>>
        extends AbstractRecording<PK, E> implements VersionedEntity {


    private static final long serialVersionUID = 42911697241368026L;
    private Integer version;

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
