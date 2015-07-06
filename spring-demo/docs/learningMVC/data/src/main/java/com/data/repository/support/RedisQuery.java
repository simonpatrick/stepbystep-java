package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;

import com.data.qdomain.Entity;
import com.data.repository.query.PropertyIndex;
import com.google.common.collect.Sets;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class RedisQuery<E extends Entity<ID, E>, ID extends Serializable & Comparable<ID>> {

    private static final String[] EXCLUDE_SERIALIZER_FIELDS = { "new" };
    private static final String ID_SET_KEY_PATTERN = "%s:ids";

    private final Class<E> entityClass;

    /**
     * 对象名称（默认为{@code entityClass.getSimpleName()}）。
     */
    private String entityName;

    /**
     * 对象的简写名称。例如：{@code college} =&gt; "clg"。
     */
    private String entityShortName;

    /**
     * 属性名称和属性索引信息的映射表。
     */
    private Map<String, PropertyIndex> mappedIndexes;

    /**
     * Bean的属性名称与属性简写名称的映射。
     */
    private Map<String, String> beanPropToShortProp;

    private String[] excludeProperties;

    private String idSetKey;

    public RedisQuery(Class<E> entityClass) {
        this(entityClass, entityClass.getSimpleName());
    }

    public RedisQuery(Class<E> entityClass, String entityShortName) {
        this(entityClass, entityClass.getSimpleName(), entityShortName);
    }

    public RedisQuery(Class<E> entityClass, String entityName, String entityShortName) {
        this(entityClass, entityName, entityShortName,
                new HashMap<>(),
                new HashMap<>());
    }

    public RedisQuery(
            Class<E> entityClass, String entityName, String entityShortName,
            Map<String, PropertyIndex> mappedIndexes,
            Map<String, String> beanPropToShortProp) {
        this.entityClass = entityClass;
        this.entityName = entityName;
        this.entityShortName = entityShortName;
        this.mappedIndexes = mappedIndexes;
        this.beanPropToShortProp = beanPropToShortProp;
    }

    public String allKeysKey() {
        return String.format("%s:keys", getEntityShortName());
    }

    /**
     * 序列化指定的实体id。
     *
     * @param id entity id.
     * @return id的字符串。
     */
    public String serializeId(Object id) {
        if (id instanceof String) {
            return (String) id;
        } else {
            return id.toString();
        }
    }

    /**
     * 返回指定id的实体的{@code key}。
     *
     * @param id 实体id。
     * @return 返回指定id的实体的{@code key}。
     */
    public String getEntityKey(Object id) {
        return String.format("%s:%s", getEntityShortName(), serializeId(id));
    }

    /**
     * 返回存储id {@code Set} 列表的{@code key}。
     */
    public String getIdSetKey() {
        if (idSetKey == null) {
            idSetKey = String.format(ID_SET_KEY_PATTERN, getEntityShortName());
        }
        return idSetKey;
    }

    public RedisQuery<E, ID> addPropIndex(String propName, String field, boolean unique) {
        mappedIndexes.put(propName, PropertyIndex.of(propName, field, unique));
        return this;
    }

    public String getPropIndexKey(String propName, Object value) {
        PropertyIndex pi;
        if ((pi = mappedIndexes.get(propName)) == null) {
            throw new IllegalArgumentException(
                    String.format("The property (%s) is not index field.", propName));
        }
        return String.format("%s:idx:%s:%s", getEntityShortName(),
                pi.getField(), value);
    }

    public Set<String> getAllIds(final RedisTemplate<String, String> r) {
        return r.opsForSet().members(getIdSetKey());
    }

    public RedisQuery<E, ID> saveHash(
            final RedisTemplate<String, String> r,
            final HashOperations<String, String, String> hashOps,
            final E entity,
            final Map<String, String> beanHash) {
        hashOps.putAll(getEntityKey(entity.getId()), beanHash);
        r.opsForSet().add(getIdSetKey(), serializeId(entity.getId()));

        return this;
    }

    public void savePropIndexes(
            final RedisTemplate<String, String> r,
            final Map<String, String> beanHash,
            final Object id) {
        Set<String> newKeys = Sets.newHashSet();
        for (Map.Entry<String, PropertyIndex> entry : getMappedIndexes().entrySet()) {
            String propName = entry.getKey();
            String propValue = beanHash.get(propName);
            PropertyIndex pi = entry.getValue();
            String indexKey = getPropIndexKey(propName, propValue);
            if (propValue != null) {
                if (pi.isUnique()) {
                    r.opsForValue().set(indexKey, serializeId(id));
                } else {
                    r.opsForSet().add(indexKey, serializeId(id));
                }
                newKeys.add(indexKey);
            }
        }
        r.opsForSet().add(allKeysKey(), newKeys.toArray(new String[newKeys.size()]));
    }

    public void deleteAllKeys(final RedisTemplate<String, String> r) {
        Set<String> keys = r.opsForSet().members(allKeysKey());
        if (keys != null && keys.size() > 0) {
            r.delete(keys);
        }
        r.delete(allKeysKey());

        Set<String> idSet = r.opsForSet().members(getIdSetKey());
        r.delete(getIdSetKey());
        if (idSet == null || idSet.isEmpty()) {
            return;
        }
        r.delete(idSet.stream()
                .map(this::getEntityKey).collect(Collectors.toSet()));
    }

    public void deleteIndexKey(
            final RedisTemplate<String, String> r,
            final Object entityId,
            Map<String, String> beanHash) {
        Map<String, PropertyIndex> piMap = getMappedIndexes();
        for (Map.Entry<String, PropertyIndex> entry : piMap.entrySet()) {
            String propName = entry.getKey();
            PropertyIndex pi = entry.getValue();
            String indexKey = getPropIndexKey(propName, beanHash.get(propName));
            if (pi.isUnique()) {
                r.delete(indexKey);
            } else {
                r.opsForSet().remove(indexKey, serializeId(entityId));
            }
        }
        r.delete(getEntityKey(entityId));
        r.opsForSet().remove(getIdSetKey(), serializeId(entityId));
    }

    public RedisQuery<E, ID> excludeProperty(final Map<String, String> beanHash) {
        if (getExcludeProperties() == null ||
                getExcludeProperties().length == 0) {
            return this;
        }
        for (String exclude : getExcludeProperties()) {
            beanHash.remove(exclude);
        }
        return this;
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityShortName() {
        return entityShortName;
    }

    public void setEntityShortName(String entityShortName) {
        this.entityShortName = entityShortName;
    }

    public Map<String, PropertyIndex> getMappedIndexes() {
        return mappedIndexes;
    }

    public void setMappedIndexes(Map<String, PropertyIndex> mappedIndexes) {
        this.mappedIndexes = mappedIndexes;
    }

    public Map<String, String> getBeanPropToShortProp() {
        return beanPropToShortProp;
    }

    public void setBeanPropToShortProp(Map<String, String> beanPropToShortProp) {
        this.beanPropToShortProp = beanPropToShortProp;
    }

    public String[] getExcludeProperties() {
        if (excludeProperties == null) {
            return EXCLUDE_SERIALIZER_FIELDS;
        }
        return excludeProperties;
    }

    public void setExcludeProperties(String[] excludeProperties) {
        Set<String> excludeSet = new HashSet<>();
        excludeSet.addAll(Arrays.asList(excludeProperties));
        excludeSet.addAll(Arrays.asList(EXCLUDE_SERIALIZER_FIELDS));
        this.excludeProperties = excludeSet.toArray(new String[excludeSet.size()]);
    }
}
