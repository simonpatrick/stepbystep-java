package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;

import com.data.qdomain.Entity;
import com.data.repository.query.BeanSort;
import org.springframework.data.domain.*;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class JvmMemoryQuery<E extends Entity<ID, E>, ID extends Serializable & Comparable<ID>> {

    private final ConcurrentMap<ID, E> DATA;

    public JvmMemoryQuery(int initialCapacity) {
        this.DATA = new ConcurrentHashMap<>(initialCapacity);
    }

    public JvmMemoryQuery(Map<ID, E> map) {
        this.DATA = new ConcurrentHashMap<>(map);
    }

    public <S extends E> S add(S entity) {
        Objects.requireNonNull(entity, "entity");
        Objects.requireNonNull(entity.getId(), "entity#id");
        DATA.put(entity.getId(), entity);
        return entity;
    }

    public boolean exists(ID id) {
        return DATA.containsKey(id);
    }

    public boolean exists(Predicate<E> predicate) {
        return DATA.values().stream()
                .filter(predicate).count() > 0;
    }

    public E findOne(ID id) {
        return id == null ? null : DATA.get(id);
    }

    public E findOne(Predicate<E> predicate) {
        List<E> list = DATA.values().stream()
                .filter(predicate)
                .map(Function.identity())
                .collect(Collectors.toList());
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    public List<E> findAll(Predicate<E> predicate, Sort sort) {
        Stream<E> stream = DATA.values().stream();
        if (predicate != null) {
            stream = stream.filter(predicate);
        }
        List<E> list = stream
                .map(Function.identity())
                .collect(Collectors.toList());
        if (list == null || list.isEmpty()) {
            return Collections.emptyList();
        }
        Sort innerSort = sort;
        if (sort == null || !sort.iterator().hasNext()) {
            innerSort = new Sort(Sort.Direction.ASC, Entity.PROP_ID);
        }
        Collections.sort(list, BeanSort.beanComparator(innerSort));
        return list;
    }

    public Page<E> findAll(Predicate<E> predicate, Pageable pageable) {
        Stream<E> stream = DATA.values().stream();
        if (predicate != null) {
            stream = stream.filter(predicate);
        }
        List<E> list = stream
                .map(Function.identity())
                .collect(Collectors.toList());
        if (list == null || list.isEmpty()) {
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }
        Collections.sort(list, BeanSort.beanComparator(pageable.getSort()));

        int total = list.size(), offset = pageable.getOffset();
        List<E> content;
        if (offset >= total) {
            content = Collections.emptyList();
        } else {
            int fromIndex, toIndex;
            if (offset == total - 1) {
                fromIndex = total - 1;
                toIndex = total;
            } else {
                fromIndex = offset;
                if (offset + pageable.getPageSize() > total) {
                    toIndex = total;
                } else {
                    toIndex = offset + pageable.getPageSize();
                }
            }
            content = list.subList(fromIndex, toIndex);
        }
        return new PageImpl<>(content, pageable, total);
    }

    public long count(Predicate<E> predicate) {
        if (predicate == null) {
            return DATA.size();
        }
        return DATA.values().stream()
                .filter(predicate)
                .count();
    }

    public void delete(E entity) {
        if (entity == null || entity.getId() == null) {
            return;
        }
        DATA.remove(entity.getId(), entity);
    }

    public void delete(ID id) {
        if (id == null) {
            return;
        }
        DATA.remove(id);
    }

    public void delete(Predicate<E> predicate) {
        if (predicate == null) {
            return;
        }
        List<ID> ids = DATA.values().stream()
                .filter(predicate)
                .map(Persistable::getId).collect(Collectors.toList());
        for (ID id : ids) {
            delete(id);
        }
    }

    public void clear() {
        DATA.clear();
    }

    public void importData(List<E> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        DATA.putAll(data.stream()
                .filter(e -> e != null && e.getId() != null)
                .collect(Collectors.toMap(
                        Persistable::getId, Function.identity())));
    }
}
