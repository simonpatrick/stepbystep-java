package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import java.io.Serializable;

public interface EntityMeta<ID extends Serializable & Comparable<ID>> extends Metadata {

    /**
     * 返回元数据关联的实体Id。
     */
    public ID getEntityId();
}
