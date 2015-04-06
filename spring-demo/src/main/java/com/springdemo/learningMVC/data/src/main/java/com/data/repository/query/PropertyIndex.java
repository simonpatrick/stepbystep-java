package com.springdemo.learningMVC.data.src.main.java.com.data.repository.query;

public class PropertyIndex {

    public static PropertyIndex of(String propName, boolean unique) {
        return new PropertyIndex(propName, propName, unique);
    }

    public static PropertyIndex of(String propName, String field, boolean unique) {
        return new PropertyIndex(propName, field, unique);
    }

    private final String propName;

    private final String field;

    private final boolean unique;

    PropertyIndex(String propName, String field, boolean unique) {
        this.propName = propName;
        this.field = field;
        this.unique = unique;
    }

    /**
     * 对应实体类的属性名称。
     */
    public String getPropName() {
        return propName;
    }

    /**
     * 实际使用的字段名称（简写名称）。
     */
    public String getField() {
        return field;
    }

    /**
     * 是否是唯一索引。
     */
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropertyIndex)) return false;

        PropertyIndex that = (PropertyIndex) o;

        return unique == that.unique && field.equals(that.field) &&
                propName.equals(that.propName);

    }

    @Override
    public int hashCode() {
        int result = propName.hashCode();
        result = 31 * result + field.hashCode();
        result = 31 * result + (unique ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("PropertyIndex{propName='%s', field='%s', unique=%s}",
                propName, field, unique);
    }
}
