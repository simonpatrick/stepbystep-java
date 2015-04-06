package com.springdemo.learningMVC.data.src.main.java.com.data.repository.listener;

import com.data.qdomain.Entity;
import com.data.qdomain.QAbstractVersioned;
import com.data.qdomain.Recording;
import com.data.qdomain.VersionedEntity;
import com.data.qdomain.jdbc.QueryDslJdbcTemplate;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.types.expr.ComparableExpressionBase;
import com.mysema.query.types.path.NumberPath;
import com.mysema.util.ReflectionUtils;
import org.joda.time.DateTime;
import org.springframework.dao.PessimisticLockingFailureException;

import java.lang.reflect.Field;
import java.util.Objects;

public class DefaultEntityUpdateListener implements EntityUpdateListener {

    private QueryDslJdbcTemplate queryDslJdbcTemplate;

    @Override
    @SuppressWarnings({"serial", "unchecked"})
    public void preUpdate(EntityEvent event) {
        QueryDslEntityEvent ev = (QueryDslEntityEvent) event;
        Entity entity = ev.getEntity();
        RelationalPath entityPath = ev.getEntityPath();
        PrimaryKey primary = entityPath.getPrimaryKey();
        Objects.requireNonNull(primary, "primary");

        // auto check version field for updating
        if (entity instanceof VersionedEntity) {
            processVersionedEntity(entity, entityPath, primary);
        }

        // auto update lastModifiedDate
        if (entity instanceof Recording) {
            Recording recording = (Recording) entity;
            recording.setLastModifiedDate(DateTime.now());
        }
    }

    @SuppressWarnings("unchecked")
    private void processVersionedEntity(
            Entity entity, RelationalPath entityPath, PrimaryKey primary) {
        NumberPath<Integer> versionPath = null;
        if (entityPath instanceof QAbstractVersioned) {
            versionPath = ((QAbstractVersioned<?>) entityPath).version;
        } else {
            Field versionField = ReflectionUtils.getFieldOrNull(
                    entityPath.getClass(), VersionedEntity.PROP_VERSION);
            if (versionField == null) { // No version field
                return; // do nothing
            }
            try {
                versionPath = (NumberPath<Integer>) versionField.get(entityPath);
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        }
        // just support single primary key
        ComparableExpressionBase idPath = (ComparableExpressionBase)
                primary.getLocalColumns().get(0);
        Integer latestVersion = queryDslJdbcTemplate.queryForObject(
                queryDslJdbcTemplate.newSqlQuery().from(entityPath)
                .where(idPath.eq(entity.getId())), versionPath);

        Integer submittedVersion = ((VersionedEntity) entity).getVersion();
        if (!Objects.equals(latestVersion, submittedVersion)) {
            throw new PessimisticLockingFailureException(
                    String.format("Stale entity: submitted version %s, but latest version is %s",
                            submittedVersion, latestVersion)
            );
        }
        ((VersionedEntity) entity).setVersion(submittedVersion + 1);
    }

    @Override
    public void afterUpdate(EntityEvent event) {

    }

    public void setQueryDslJdbcTemplate(QueryDslJdbcTemplate queryDslJdbcTemplate) {
        this.queryDslJdbcTemplate = queryDslJdbcTemplate;
    }
}
