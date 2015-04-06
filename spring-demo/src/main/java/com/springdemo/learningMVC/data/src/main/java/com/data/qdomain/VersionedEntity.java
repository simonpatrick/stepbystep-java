package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

public interface VersionedEntity {

    /**
     * {@code version} property name.
     */
    String PROP_VERSION = "version";

    /**
     * Returns the entity version number.
     */
    public Integer getVersion();

    /**
     * Sets the entity version number.
     *
     * @param version the entity version.
     */
    public void setVersion(Integer version);
}
