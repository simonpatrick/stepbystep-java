package com.springdemo.learningMVC.data.src.main.java.com.data.qdomain;

import com.google.common.collect.ImmutableMap;
import org.springframework.data.domain.Persistable;
import org.springframework.data.repository.CrudRepository;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public interface Entity<ID extends Serializable & Comparable<ID>, E extends Entity<ID, E>>
        extends Persistable<ID> {

    /**
     * 将集合中的实体转换为{@code ID -> Entity} 映射。
     * <p />
     * 如果要转换的实体集合是{@code null} 或者空集合，则直接返回空映射。
     *
     * @param entities 要转换的实体集合。
     * @param <ID> 实体ID的类型。
     * @param <E> 实体的类型。
     * @return 转换后的实体映射。
     */
    @Nonnull
    public static <ID extends Serializable & Comparable<ID>, E extends Entity<ID, E>> Map<ID, E>
            uniqueIndex(Collection<E> entities) {
        if (entities == null || entities.isEmpty()) {
            return ImmutableMap.of();
        }
        return entities.stream()
                .filter(e -> e != null && e.getId() != null)
                .collect(Collectors.toMap(Persistable::getId, Function.identity()));
    }

    /**
     * The "id" property name.
     */
    String PROP_ID = "id";

    /**
     * The "name" property name.
     */
    String PROP_NAME = "name";

    /**
     * The "value" property name.
     */
    String PROP_VALUE = "value";

    /**
     * The "status" property name.
     */
    String PROP_STATUS = "status";

    /**
     * The "userId" property name.
     */
    String PROP_USER_ID = "userId";

    /**
     * The "userName" property name.
     */
    String PROP_USER_NAME = "userName";

    /**
     * 设置实体的{@code ID}。
     *
     * @param id 要设置的{@code ID}。
     */
    public void setId(ID id);

    /**
     * 将指定的实体和当前实体进行合并。
     *
     * @param e 新状态的实体。
     * @return 返回更新状态后的实体。
     * @throws NullPointerException if {@code e} is {@code null}.
     * @throws IllegalArgumentException if {@code !e.getId().equals(getId())}.
     */
    default E merge(E e) {
        checkNotNull(e, "Merged entity (e)");
        checkArgument((e.getId() != null && getId() != null &&
                !e.getId().equals(getId())), "!e.getId().equals(getId())");
        return e;
    }

    public default <S extends E> S save() {
        // do nothing
        return null;
    }

    public default void delete() {
        // do nothing
    }

    public default CrudRepository<E, ID> repository() {
        return null;
    }
}