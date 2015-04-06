package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;

import com.data.qdomain.Entity;
import com.data.qdomain.jdbc.JavaBeanPropertyRowMapper;
import com.data.qdomain.jdbc.QueryDslJdbcTemplate;
import com.data.repository.QueryDslRepository;
import com.data.repository.listener.EntityInsertListener;
import com.data.repository.listener.EntityUpdateListener;
import com.data.repository.listener.QueryDslEntityEvent;
import com.google.common.collect.*;
import com.mysema.query.sql.PrimaryKey;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.dml.BeanMapper;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import com.mysema.query.types.Predicate;
import com.mysema.query.types.Projections;
import com.mysema.query.types.expr.SimpleExpression;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import javax.annotation.Nonnull;
import java.beans.PropertyEditor;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;


public class QueryDslJdbcRepository<E extends Entity<ID, E>, ID extends Serializable & Comparable<ID>>
        implements QueryDslRepository<E, ID>, QueryDslPredicateExecutor<E>,
        ApplicationContextAware, InitializingBean {

    /**
     * Insert Clause listeners
     */
    protected final List<EntityInsertListener> insertListeners = Lists.newArrayList();
    /**
     * Update Clause listeners
     */
    protected final List<EntityUpdateListener> updateListeners = Lists.newArrayList();

    protected QueryDslJdbcTemplate queryDslJdbcTemplate;
    protected ConversionService conversionService;
    protected Table<String, Class<?>, PropertyEditor> propertyEditorTable;

    protected RelationalPath<E> entityPath;

    private final Class<E> domainClass;
    private final Class<ID> idClass;

    private long maxSize4FindAll = -1;

    private ApplicationContext context;

    @SuppressWarnings("unused")
    public QueryDslJdbcRepository() {
        this(null, HashBasedTable.create(), null);
    }

    public QueryDslJdbcRepository(RelationalPath<E> entityPath) {
        this(entityPath, HashBasedTable.create(), null);
    }

    @SuppressWarnings("unchecked")
    public QueryDslJdbcRepository(
            RelationalPath<E> entityPath,
            Table<String, Class<?>, PropertyEditor> propertyEditorTable,
            ConversionService conversionService) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        domainClass = (Class<E>) types[0];
        idClass = (Class<ID>) types[1];

        this.entityPath = entityPath;
        this.propertyEditorTable = propertyEditorTable;
        this.conversionService = conversionService == null ? new DefaultConversionService() :
                conversionService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (context != null) {
            Map<String, EntityUpdateListener> updateListenerMap =
                    context.getBeansOfType(EntityUpdateListener.class);
            if (updateListenerMap != null && updateListenerMap.size() > 0) {
                updateListeners.addAll(updateListenerMap.values());
            }

            Map<String, EntityInsertListener> insertListenerMap =
                    context.getBeansOfType(EntityInsertListener.class);
            if (insertListenerMap != null && insertListenerMap.size() > 0) {
                insertListeners.addAll(insertListenerMap.values());
            }
        }
    }

    public Class<E> getDomainClass() {
        return domainClass;
    }

    public Class<ID> getIdClass() {
        return idClass;
    }

    /**
     * Returns {@code true} if the entity's ID is auto_increment,
     * otherwise {@code false}. Default: {@code true}.
     */
    protected boolean isAutoIncrement() {
        return true;
    }

    /**
     * Returns the QueryDsl {@code RelationPath} of this domain repository.
     */
    protected RelationalPath<E> getEntityPath() {
        return entityPath;
    }

    /**
     * Query for object with given {@code SQLQuery}.
     *
     * @param sqlQuery SQLQuery.
     * @return the domain object with given query.
     */
    protected E queryForObject(final SQLQuery sqlQuery) {
        return queryDslJdbcTemplate.queryForObject(
                sqlQuery, Projections.bean(getEntityPath(),
                        QueryDsl.columns(getEntityPath())));
    }

    /**
     * Query for domain list with given {@code SQLQuery}.
     *
     * @param sqlQuery SQLQuery.
     * @return the domain object list with given query.
     */
    protected List<E> queryForList(final SQLQuery sqlQuery) {
        return queryDslJdbcTemplate.query(
                sqlQuery, Projections.bean(getEntityPath(),
                        QueryDsl.columns(getEntityPath())));
    }

    protected void checkEntityIdNotNull(ID id) throws IllegalArgumentException {
        if (id == null) {
            throw new IllegalArgumentException("The given entity id must not be null!");
        }
    }

    protected void checkEntityNotNull(E entity) throws IllegalArgumentException {
        if (entity == null) {
            throw new IllegalArgumentException("The given entity must not be null!");
        }
    }

    /**
     * Returns new {@link JavaBeanPropertyRowMapper} with this domain class.
     */
    protected JavaBeanPropertyRowMapper<E> newEntityRowMapper() {
        JavaBeanPropertyRowMapper<E> rowMapper =
                JavaBeanPropertyRowMapper.newInstance(getDomainClass());
        if (conversionService != null)
            rowMapper.setConversionService(conversionService);
        if (propertyEditorTable != null)
            rowMapper.setCustomPropertyEditors(propertyEditorTable);
        return rowMapper;
    }

    /**
     * Returns the new {@code SQLQuery} with this {@link #getEntityPath()} .
     */
    protected SQLQuery newSqlQuery() {
        return queryDslJdbcTemplate.newSqlQuery().from(getEntityPath());
    }

    /**
     * Returns the new empty {@code SQLQuery}. Just with configuration.
     */
    protected SQLQuery emptySqlQuery() {
        return queryDslJdbcTemplate.newSqlQuery();
    }

    /**
     * Returns the new SQL sub query ({@code SQLSubQuery).
     */
    protected SQLSubQuery newSubQuery() {
        return new SQLSubQuery();
    }

    @SuppressWarnings("unchecked")
    protected Predicate primaryKeyPredicate(ID[] ids) {
        if (ids == null || ids.length == 0) {
            throw new IllegalArgumentException(
                    "The given primary key `ids` must not be null or empty.");
        }
        PrimaryKey<E> primaryKey = getEntityPath().getPrimaryKey();
        assert primaryKey != null;
        List<? extends Path<?>> idPaths = primaryKey.getLocalColumns();
        assert idPaths != null && idPaths.size() > 0;
        if (ids.length == 1) {
            return ((SimpleExpression<ID>) idPaths.get(0)).eq(ids[0]);
        }

        return ((SimpleExpression<ID>) idPaths.get(0)).in(ids);
    }

    /**
     * Returns primary keys predicate used given ids.
     *
     * @param ids the given ids (primary keys)
     */
    protected Predicate primaryKeyPredicate(@Nonnull Iterable<ID> ids) {
        ID[] idArr = Iterables.toArray(ids, getIdClass());
        return primaryKeyPredicate(idArr);
    }

    @SuppressWarnings("unchecked")
    protected Predicate primaryKeyPredicate(ID id) {
        checkEntityIdNotNull(id);
        PrimaryKey<E> primaryKey = getEntityPath().getPrimaryKey();
        assert primaryKey != null;
        List<? extends Path<?>> idPaths = primaryKey.getLocalColumns();
        assert idPaths != null && idPaths.size() > 0;
        return ((SimpleExpression<ID>) idPaths.get(0)).eq(id);
    }

    @Override
    @Nonnull
    public List<E> findAll(Iterable<ID> ids) {
        if (ids == null || Iterables.isEmpty(ids)) {
            return ImmutableList.of();
        }
        return queryForList(newSqlQuery().where(primaryKeyPredicate(ids)));
    }

    @Override
    public long count() {
        return count(null);
    }

    @Override
    public void delete(ID id) {
        checkEntityIdNotNull(id);
        queryDslJdbcTemplate.delete(getEntityPath(),
                delete -> delete.where(primaryKeyPredicate(id)).execute());
    }

    @Override
    public void delete(E entity) {
        checkEntityNotNull(entity);
        checkEntityIdNotNull(entity.getId());
        delete(entity.getId());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(Iterable<? extends E> entities) {
        deleteInBatch((Iterable<E>) entities);
    }

    @Override
    public List<E> findAll(Sort sort) {
        final SQLQuery sqlQuery = newSqlQuery();
        QueryDsl.applySorting(sort, sqlQuery, getEntityPath());
        if (maxSize4FindAll > 0) {
            long total = count();
            long limit = total > maxSize4FindAll ? maxSize4FindAll : total;
            sqlQuery.offset(0).limit(limit);
        }
        return queryForList(sqlQuery);
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return findAll(null, pageable);
    }

    @Override
    public <S extends E> S save(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("The given entity must be not null!");
        }
        if (entity.getId() == null) { // only auto_increment
            return insert(entity);
        } else {
            return exists(entity.getId()) ? update(entity) : insert(entity);
        }
    }

    /**
     * Inserts a new entity to repository (relation database).
     *
     * @param entity new entity.
     * @param <S> the entity type.
     * @return a new entity with id.
     */
    public <S extends E> S insert(final S entity) {
        boolean withKey = false;
        if (isAutoIncrement()) {
            withKey = entity.getId() == null;
        } else { // not auto_increment for PK
            if (entity.getId() == null)
                throw new InvalidDataAccessResourceUsageException(
                        "The entity's ID must not be null.");
        }
        List<EntityInsertListener> listeners = copyGetInsertListeners();
        QueryDslEntityEvent event = null;
        if (!listeners.isEmpty()) {
            event = new QueryDslEntityEvent(entity, getEntityPath());
            firePreInsertEvents(listeners, event);
        }
        long row = 0;
        if (withKey) {
            ID id = queryDslJdbcTemplate.insertWithKey(getEntityPath(),
                    insert -> insert.populate(entity, BeanMapper.WITH_NULL_BINDINGS)
                            .executeWithKey(getIdClass()));
            if (id != null) {
                entity.setId(id);
                row = 1;
            }
        } else {
            row = queryDslJdbcTemplate.insert(getEntityPath(),
                    insert -> insert.populate(entity, BeanMapper.WITH_NULL_BINDINGS)
                            .execute()
            );
        }
        if (row > 0 && event != null) {
            fireAfterInsertEvents(listeners, event);
        }
        return row == 0 ? null : entity;
    }

    /**
     * Updates the given entity object.
     *
     * @param entity updated entity.
     * @param <S> the entity type.
     * @return a updated entity.
     */
    @SuppressWarnings("unchecked")
    public <S extends E> S update(final S entity) {
        Objects.requireNonNull(entity, "entity");
        // copy listeners
        List<EntityUpdateListener> listeners = copyGetUpdateListeners();
        QueryDslEntityEvent event = null;
        if (!listeners.isEmpty()) {
            event = new QueryDslEntityEvent(entity, getEntityPath());
            firePreUpdateEvents(listeners, event);
        }
        long row = queryDslJdbcTemplate.update(getEntityPath(),
                update -> update.populate(entity, BeanMapper.DEFAULT)
                        .where(primaryKeyPredicate(entity.getId()))
                        .execute()
        );
        if (!listeners.isEmpty() && event != null) {
            event.addData("updatedRow", row);
            fireAfterUpdateEvents(listeners, event);
        }
        return row == 0 ? null : entity;
    }

    @Override
    public <S extends E> List<S> save(Iterable<S> entities) {
        if (entities == null || Iterables.size(entities) == 0) {
            return ImmutableList.of();
        }
        List<S> result = new LinkedList<>();
        for (S entity : entities) {
            S updatedEntity = save(entity);
            if (updatedEntity != null) {
                result.add(updatedEntity);
            }
        }

        return result;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E findOne(ID id) {
        checkEntityIdNotNull(id);
        return findOne(primaryKeyPredicate(id));
    }

    @Override
    public boolean exists(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("The given entity id must not be null!");
        }
        return exists(primaryKeyPredicate(id));
    }

    @Override
    public void deleteInBatch(Iterable<E> entities) {
        if (entities == null || Iterables.isEmpty(entities)) {
            return;
        }
        final Iterable<E> realEntities = Iterables.filter(entities,
                input -> input != null && input.getId() != null);

        final Iterable<ID> deletedIds = Iterables.transform(realEntities,
                input -> {
                    assert input != null;
                    return input.getId();
                }
        );

        queryDslJdbcTemplate.delete(getEntityPath(), deleteClause ->
                deleteClause.where(primaryKeyPredicate(deletedIds)).execute());
    }

    @Override
    public void deleteAllInBatch() {
        throw new UnsupportedOperationException("Not support delete all records.");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Not support delete all records.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(Predicate predicate) {
        return queryDslJdbcTemplate.exists(newSqlQuery().where(predicate));
    }

    @Override
    public E findOne(Predicate predicate) {
        return queryForObject(newSqlQuery().where(predicate));
    }

    @Override
    public List<E> findAll(Predicate predicate) {
        checkNotNull(predicate, "predicate");
        return queryForList(newSqlQuery().where(predicate));
    }

    @Override
    public List<E> findAll(Predicate predicate, OrderSpecifier<?>... orders) {
        final SQLQuery sqlQuery = newSqlQuery().where(predicate).orderBy(orders);
        return queryForList(sqlQuery);
    }

    @Override
    public Page<E> findAll(Predicate predicate, Pageable pageable) {
        long total = count(predicate);
        List<E> content;
        if (total == 0) {
            content = ImmutableList.of();
        } else {
            final SQLQuery sqlQuery = predicate == null ?
                    newSqlQuery() : newSqlQuery().where(predicate);
            QueryDsl.applyPagination(pageable, sqlQuery, getEntityPath());
            content = queryForList(sqlQuery);
        }
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public long count(Predicate predicate) {
        SQLQuery sqlQuery;
        if (predicate != null) {
            sqlQuery = newSqlQuery().where(predicate);
        } else {
            sqlQuery = newSqlQuery();
        }
        return queryDslJdbcTemplate.count(sqlQuery);
    }

    public void setUpdateListeners(List<EntityUpdateListener> listeners) {
        Objects.requireNonNull(listeners, "updateListeners");
        if (listeners.isEmpty()) {
            return;
        }
        updateListeners.addAll(listeners);
    }

    public void addUpdateListener(EntityUpdateListener listener) {
        Objects.requireNonNull(listener, "updateListener");
        updateListeners.add(listener);
    }

    /**
     * Deletes all entities managed by the repository (only testing).
     */
    @SuppressWarnings("unused")
    public void deleteAllInBatchInner() {
        queryDslJdbcTemplate.delete(getEntityPath(), SQLDeleteClause::execute);
    }

    @Nonnull
    protected List<EntityUpdateListener> copyGetUpdateListeners() {
        if (updateListeners.isEmpty()) {
            return ImmutableList.of();
        }
        List<EntityUpdateListener> listeners;
        synchronized (updateListeners) {
            listeners = ImmutableList.copyOf(updateListeners);
        }
        return listeners;
    }

    @Nonnull
    protected List<EntityInsertListener> copyGetInsertListeners() {
        if (insertListeners.isEmpty()) {
            return ImmutableList.of();
        }
        List<EntityInsertListener> listeners;
        synchronized (insertListeners) {
            listeners = ImmutableList.copyOf(insertListeners);
        }
        return listeners;
    }

    public void firePreUpdateEvents(
            List<EntityUpdateListener> listeners, QueryDslEntityEvent event) {
        listeners.forEach(updateListener -> updateListener.preUpdate(event));
    }

    public void fireAfterUpdateEvents(
            List<EntityUpdateListener> listeners, QueryDslEntityEvent event) {
        listeners.forEach(updateListener -> updateListener.afterUpdate(event));
    }

    protected void firePreInsertEvents(
            List<EntityInsertListener> listeners, QueryDslEntityEvent event) {
        listeners.forEach(insertListener -> insertListener.preInsert(event));
    }

    protected void fireAfterInsertEvents(
            List<EntityInsertListener> listeners, QueryDslEntityEvent event) {
        listeners.forEach(insertListener -> insertListener.afterInsert(event));
    }

    @Autowired(required = false)
    public void setQueryDslJdbcTemplate(QueryDslJdbcTemplate queryDslJdbcTemplate) {
        this.queryDslJdbcTemplate = queryDslJdbcTemplate;
    }

    @Autowired(required = false)
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Autowired(required = false)
    public void setMaxSize4FindAll(long maxSize4FindAll) {
        this.maxSize4FindAll = maxSize4FindAll;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        context = applicationContext;
    }
}
