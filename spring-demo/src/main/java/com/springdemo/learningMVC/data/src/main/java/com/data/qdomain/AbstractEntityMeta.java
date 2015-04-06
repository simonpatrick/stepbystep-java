package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import com.common.base.Values;
import com.google.common.base.MoreObjects;

import java.io.Serializable;

public abstract class AbstractEntityMeta<PK extends Serializable & Comparable<PK>,
        E extends AbstractEntityMeta<PK, E>> extends AbstractEntity<PK, E>
        implements EntityMeta<PK>, Values {

    private static final long serialVersionUID = 1L;

    private PK entityId;
    private String metaKey;
    private String metaValue;

    @Override
    public PK getEntityId() {
        return entityId;
    }

    public void setEntityId(PK entityId) {
        this.entityId = entityId;
    }

    @Override
    public String getMetaKey() {
        return metaKey;
    }

    public void setMetaKey(String metaKey) {
        this.metaKey = metaKey;
    }

    @Override
    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }


    @Override
    public String getValue() {
        return getMetaValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntityMeta)) return false;
        if (!super.equals(o)) return false;

        AbstractEntityMeta that = (AbstractEntityMeta) o;

        return (entityId != null ? !entityId.equals(that.entityId) : that.entityId != null) &&
                (metaKey != null ? !metaKey.equals(that.metaKey) : that.metaKey != null) &&
                !(metaValue != null ? !metaValue.equals(that.metaValue) : that.metaValue != null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (entityId != null ? entityId.hashCode() : 0);
        result = 31 * result + (metaKey != null ? metaKey.hashCode() : 0);
        result = 31 * result + (metaValue != null ? metaValue.hashCode() : 0);
        return result;
    }

    protected MoreObjects.ToStringHelper toStringHelper(String entityIdProp) {
        return MoreObjects.toStringHelper(getClass())
                .add(PROP_ID, getId())
                .add(entityIdProp, getEntityId())
                .add(PROP_META_KEY, getMetaKey())
                .add(PROP_META_VALUE, getMetaValue());
    }

    @Override
    public String toString() {
        return toStringHelper("entityId").toString();
    }
}
