package com.springdemo.learningMVC.data.src.main.java.com.data.repository.listener;

import com.data.qdomain.Entity;
import com.google.common.collect.Maps;
import com.mysema.query.sql.RelationalPath;

import java.util.Map;
import java.util.Objects;

public class QueryDslEntityEvent extends EntityEvent {

    private static final long serialVersionUID = -3515178679975442520L;

    private final Map<String, Object> data = Maps.newHashMap();

    private final RelationalPath<?> entityPath;

    public QueryDslEntityEvent(Entity<?, ?> entity, RelationalPath<?> entityPath) {
        super(entity);
        this.entityPath = entityPath;
    }

    public RelationalPath<?> getEntityPath() {
        return entityPath;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public QueryDslEntityEvent addData(String key, Object value) {
        Objects.requireNonNull(key, "key");
        data.put(key, value);
        return this;
    }
}
