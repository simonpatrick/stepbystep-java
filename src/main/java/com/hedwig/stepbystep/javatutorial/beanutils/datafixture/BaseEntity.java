package com.hedwig.stepbystep.javatutorial.beanutils.datafixture;

import java.util.Map;


public abstract class BaseEntity<T> {

    private String primaryKeyName;
    private String seqName;
    private Map<String,String> rowMap;

    protected BaseEntity(String primaryKeyName, String seqName, Map<String, String> rowMap) {
        this.primaryKeyName = primaryKeyName;
        this.seqName = seqName;
        this.rowMap = rowMap;
    }

    public abstract T findByPrimaryKey(String baseId);


    public String getPrimaryKeyName() {
        return primaryKeyName;
    }

    public void setPrimaryKeyName(String primaryKeyName) {
        this.primaryKeyName = primaryKeyName;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    public Map<String, String> getRowMap() {
        return rowMap;
    }

    public void setRowMap(Map<String, String> rowMap) {
        this.rowMap = rowMap;
    }
}
