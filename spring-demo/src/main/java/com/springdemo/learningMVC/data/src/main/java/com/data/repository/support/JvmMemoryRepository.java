package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;

import com.data.qdomain.Entity;
import com.data.repository.GenericRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Persistable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public abstract class JvmMemoryRepository<E extends Entity<ID, E>, ID extends Serializable & Comparable<ID>>
        implements GenericRepository<E, ID> {

    protected JvmMemoryQuery<E, ID> memoryQuery;

    protected JvmMemoryRepository(int initialCapacity) {
        this.memoryQuery = new JvmMemoryQuery<E, ID>(initialCapacity);
    }

    protected JvmMemoryRepository(Map<ID, E> map) {
        this.memoryQuery = new JvmMemoryQuery<>(map);
    }

    @Override
    public List<E> findAll(Iterable<ID> ids) {
        return memoryQuery.findAll(
                e -> Iterables.contains(ids, e.getId()), (Sort) null);
    }

    @Override
    public List<E> findAll(Sort sort) {
        return memoryQuery.findAll(null, sort);
    }

    @Override
    public <S extends E> List<S> save(Iterable<S> entities) {
        if (entities == null || Iterables.isEmpty(entities)) {
            return Collections.emptyList();
        }
        List<S> list = new ArrayList<>(Iterables.size(entities));
        int i = 0;
        for (S entity : entities) {
            S e = save(entity);
            if (e != null) {
                list.add(i++, e);
            }
        }

        return list;
    }

    @Override
    public void deleteInBatch(Iterable<E> entities) {
        if (entities == null || Iterables.isEmpty(entities)) {
            return;
        }
        for (E entity : entities) {
            if (entity == null || entity.getId() == null) {
                continue;
            }
            delete(entity);
        }
    }

    @Override
    public void deleteAllInBatch() {
        deleteAll();
    }

    @Override
    public void deleteAll() {
        memoryQuery.clear();
    }

    @Override
    public Page<E> findAll(Pageable pageable) {
        return memoryQuery.findAll(null, pageable);
    }

    @Override
    public <S extends E> S save(S entity) {
        return memoryQuery.add(entity);
    }

    @Override
    public E findOne(ID id) {
        return memoryQuery.findOne(id);
    }

    @Override
    public boolean exists(ID id) {
        return memoryQuery.exists(id);
    }

    @Override
    public long count() {
        return memoryQuery.count(null);
    }

    @Override
    public void delete(ID id) {
        memoryQuery.delete(id);
    }

    @Override
    public void delete(E entity) {
        memoryQuery.delete(entity);
    }

    @Override
    public void delete(Iterable<? extends E> entities) {
        if (entities == null || Iterables.isEmpty(entities)) {
            return;
        }
        List<ID> ids = ImmutableList.copyOf(entities).stream()
                .map(Persistable::getId).collect(Collectors.toList());
        memoryQuery.delete(e -> ids.contains(e.getId()));
    }
}
