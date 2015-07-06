package com.springdemo.learningMVC.data.src.main.java.com.data.repository;

import com.data.qdomain.Entity;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

public interface QueryDslRepository<E extends Entity<ID, E>, ID extends Serializable & Comparable<ID>>
        extends GenericRepository<E, ID> {

    /**
     * 检查指定查询规约下是否存在记录。
     *
     * @param spec 查询规约。
     * @return 如果指定查询规约下存在记录，则返回{@code true}，否则{@code false}。
     */
    boolean exists(Predicate spec);

    /**
     * Returns a single entity matching the given {@link com.mysema.query.types.Predicate}.
     *
     * @param spec 查询规约。
     * @return 指定匹配规约的实体。
     */
    E findOne(Predicate spec);

    /**
     * Returns all entities matching the given {@link Predicate}.
     *
     * @param spec 查询规约。
     * @return 指定匹配规约的所有实体。
     */
    List<E> findAll(Predicate spec);

    /**
     * Returns all entities matching the given {@link Predicate} applying the given {@link com.mysema.query.types.OrderSpecifier}s.
     *
     * @param predicate 查询规约。
     * @param orders 排序规约。
     * @return
     */
    List<E> findAll(Predicate predicate, OrderSpecifier<?>... orders);

    /**
     * Returns a {@link org.springframework.data.domain.Page} of entities matching the given {@link Predicate}.
     *
     * @param predicate 查询规约。
     * @param pageable 分页规约。
     * @return
     */
    Page<E> findAll(Predicate predicate, Pageable pageable);

    /**
     * Returns the number of instances that the given {@link Predicate} will return.
     *
     * @param predicate the {@link Predicate} to count instances for
     * @return the number of instances
     */
    long count(Predicate predicate);
}
