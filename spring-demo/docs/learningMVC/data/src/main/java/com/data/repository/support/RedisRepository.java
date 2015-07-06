package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;

import com.data.qdomain.Entity;
import com.data.redis.BeanUtilsBeanHashMapper;
import com.data.repository.GenericRepository;
import com.google.common.collect.Iterables;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@NoRepositoryBean
public abstract class RedisRepository<E extends Entity<ID, E>, ID extends Serializable & Comparable<ID>>
        implements GenericRepository<E, ID>, InitializingBean {

    protected RedisTemplate<String, String> redisTemplate;
    protected HashOperations<String, String, String> hashOps;

    protected HashMapper<E, String, String> hashMapper;

    private final Class<E> domainClass;
    private final Class<ID> idClass;

    private RedisQuery<E, ID> redisQuery;

    @SuppressWarnings("unchecked")
    protected RedisRepository() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Type[] types = type.getActualTypeArguments();
        domainClass = (Class<E>) types[0];
        idClass = (Class<ID>) types[1];

        // default entityName is class simpleName
        redisQuery = new RedisQuery<>(domainClass, domainClass.getSimpleName());
        hashMapper = new BeanUtilsBeanHashMapper<>(domainClass);
    }

    @SuppressWarnings("unused")
    public Class<E> getDomainClass() {
        return domainClass;
    }

    @SuppressWarnings("unused")
    public Class<ID> getIdClass() {
        return idClass;
    }

    public RedisQuery<E, ID> getRedisQuery() {
        return redisQuery;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (redisTemplate != null) {
            hashOps = redisTemplate.opsForHash();
        }
    }

    @Override
    public List<E> findAll(Iterable<ID> ids) {
        List<E> list = new ArrayList<>(Iterables.size(ids));
        int i = 0;
        for (ID id : ids) {
            list.add(i++, findOne(id));
        }
        return list;
    }

    @Override
    public List<E> findAll(Sort sort) {
        throw new UnsupportedOperationException("Not support query all.");
    }

    @Override
    public <S extends E> List<S> save(Iterable<S> entities) {
        if (entities == null || Iterables.isEmpty(entities)) {
            return Collections.emptyList();
        }
        List<S> list = new ArrayList<>(Iterables.size(entities));
        int i = 0;
        for (S entity : entities) {
            list.add(i++, save(entity));
        }
        return list;
    }

    @Override
    public void deleteInBatch(Iterable<E> entities) {
        delete(entities);
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    @SuppressWarnings("all")
    public void deleteAll() {
        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations)
                    throws DataAccessException {
                RedisTemplate<String, String> r = (RedisTemplate<String, String>) operations;
                redisQuery.deleteAllKeys(r);
                return null;
            }
        });
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return null;
    }

    @Override
    @SuppressWarnings("all")
    public <S extends E> S save(final S entity) {
        Map<String, String> hash = hashMapper.toHash(entity);
        redisQuery.excludeProperty(hash);

        return redisTemplate.execute(new SessionCallback<S>() {
            @Override
            public <K, V> S execute(RedisOperations<K, V> operations)
                    throws DataAccessException {
                //@SuppressWarnings("unchecked")
                RedisTemplate<String, String> r = (RedisTemplate<String, String>) operations;

                redisQuery.saveHash(r, hashOps, entity, hash);

                // save indexes
                redisQuery.savePropIndexes(r, hash, entity.getId());
                return entity;
            }
        });
    }

    @Override
    public E findOne(ID id) {
        Map<String, String> map = hashOps.entries(redisQuery.getEntityKey(id));
        if (map == null || map.isEmpty()) {
            return null;
        }
        return hashMapper.fromHash(map);
    }

    @Override
    public boolean exists(ID id) {
        return redisTemplate.hasKey(redisQuery.getEntityKey(id));
    }

    @Override
    public long count() {
        return redisTemplate.opsForSet().size(redisQuery.getIdSetKey());
    }

    @Override
    @SuppressWarnings("all")
    public void delete(ID id) {

        redisTemplate.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                RedisTemplate<String, String> r = (RedisTemplate<String, String>) operations;

                Map<String, String> hash = hashOps.entries(redisQuery.getEntityKey(id));
                if (hash == null || hash.isEmpty()) {
                    return null;
                }
                // 先删除索引列映射
                redisQuery.deleteIndexKey(r, id, hash);

                return null;
            }
        });

    }

    @Override
    public void delete(E entity) {
        delete(entity.getId());
    }

    @Override
    public void delete(Iterable<? extends E> entities) {
        if (entities == null || Iterables.isEmpty(entities)) {
            return;
        }
        for (E entity : entities) {
            delete(entity);
        }
    }

    public void setHashMapper(HashMapper<E, String, String> hashMapper) {
        this.hashMapper = hashMapper;
    }

    @Autowired(required = false)
    public void setStringRedisTemplate(RedisTemplate<String, String> stringRedisTemplate) {
        this.redisTemplate = stringRedisTemplate;
    }

}
