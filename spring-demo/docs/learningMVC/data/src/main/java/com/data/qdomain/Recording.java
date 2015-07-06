package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import org.joda.time.DateTime;

public interface Recording {

    /**
     * {@code CREATED_DATE} property name.
     */
    String PROP_CREATED_DATE = "createdDate";

    /**
     * {@code LAST_MODIFIED_DATE} property name.
     */
    String PROP_LAST_MODIFIED_DATE = "lastModifiedDate";

    /**
     * Returns the creation date of the entity.
     *
     * @return the createdDate
     */
    public DateTime getCreatedDate();

    /**
     * Sets the creation date of the entity.
     *
     * @param createdAt the creation date to set
     */
    public void setCreatedDate(DateTime createdAt);

    /**
     * Returns the date of the last modification.
     *
     * @return the lastModifiedDate
     */
    public DateTime getLastModifiedDate();

    /**
     * Sets the date of the last modification.
     *
     * @param lastModifiedAt the date of the last modification to set
     */
    public void setLastModifiedDate(DateTime lastModifiedAt);
}
