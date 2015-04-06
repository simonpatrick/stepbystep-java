package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import java.io.Serializable;

public abstract class AbstractVersionedEntity<
        PK extends Serializable & Comparable<PK>, E extends AbstractVersionedEntity<PK, E>>
        extends AbstractEntity<PK, E> implements VersionedEntity {


    private static final long serialVersionUID = 6377810483370734380L;
    private Integer version = 1;

    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void setVersion(Integer version) {
        this.version = version;
    }
}
