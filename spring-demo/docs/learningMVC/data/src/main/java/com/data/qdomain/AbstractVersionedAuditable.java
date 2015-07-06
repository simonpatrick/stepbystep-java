package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import java.io.Serializable;

public abstract class AbstractVersionedAuditable<
        PK extends Serializable & Comparable<PK>, E extends AbstractVersionedAuditable<PK, E, U>,
        U extends Serializable & Comparable<U>>
        extends AbstractAuditable<PK, E, U> implements VersionedEntity {


    private static final long serialVersionUID = -6476514728046485019L;
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