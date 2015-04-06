package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

public interface Metadata {

    /**
     * {@code metaKey} 属性名称。
     */
    String PROP_META_KEY = "metaKey";

    /**
     * {@code metaValue} 属性名称。
     */
    String PROP_META_VALUE = "metaValue";

    /**
     * 元数据的键名。
     */
    public String getMetaKey();

    /**
     * 元数据的键值。
     */
    public String getMetaValue();
}
