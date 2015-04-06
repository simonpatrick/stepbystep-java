package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;

import com.google.common.collect.Iterables;
import com.mysema.query.sql.RelationalPath;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.types.Expression;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.Path;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QSort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryDsl {

    /**
     * 返回指定实体的所有列路径信息。
     *
     * @param entityPath 实体路径。
     * @return 返回指定实体的所有列路径信息。
     */
    public static Path<?>[] columns(final RelationalPath<?> entityPath) {
        List<Path<?>> pathList = entityPath.getColumns();
        Path[] paths = new Path[pathList.size()];
        return pathList.toArray(paths);
    }

    public static Path<?> findPath(final RelationalPath<?> entityPath, String propName) {
        for (Path<?> path : entityPath.getColumns()) {
            if (Objects.equals(propName, path.getMetadata().getElement())) {
                return path;
            }
        }
        return null;
    }

    public static SQLQuery applyPagination(
            Pageable pageable, final SQLQuery sqlQuery, final RelationalPath<?> entityPath) {
        if (pageable == null) {
            return sqlQuery;
        }

        sqlQuery.offset(pageable.getOffset());
        sqlQuery.limit(pageable.getPageSize());

        return applySorting(pageable.getSort(), sqlQuery, entityPath);
    }

    public static SQLQuery applySorting(
            Sort sort, final SQLQuery sqlQuery, final RelationalPath<?> entityPath) {
        if (sort == null || sort.iterator() == null ||
                !sort.iterator().hasNext()) {
            return sqlQuery;
        }
        if (sort instanceof QSort) {
            QSort qSort = (QSort) sort;
            List<OrderSpecifier<?>> orderSpecifiers = qSort.getOrderSpecifiers();
            if (orderSpecifiers == null || orderSpecifiers.isEmpty()) {
                return sqlQuery;
            }
            qSort.getOrderSpecifiers().forEach(sqlQuery::orderBy);
        } else {
            for (Sort.Order order : sort) {
                if (order.getProperty() == null) {
                    continue;
                }
                sqlQuery.orderBy(toOrder(entityPath, order));
            }
        }
        return sqlQuery;
    }

    public static OrderSpecifier<?>[] toOrders(
            final RelationalPath<?> entityPath, Sort sort) {
        int sortSize;
        if (sort == null || (sortSize = Iterables.size(sort)) == 0) {
            return new OrderSpecifier[0];
        }
        if (sort instanceof QSort) {
            QSort qSort = (QSort) sort;
            List<OrderSpecifier<?>> orderSpecifiers = qSort.getOrderSpecifiers();
            if (orderSpecifiers == null || orderSpecifiers.isEmpty()) {
                return new OrderSpecifier[0];
            }
            return Iterables.toArray(qSort.getOrderSpecifiers(), OrderSpecifier.class);
        } else {
            List<OrderSpecifier<?>> orders = new ArrayList<>(sortSize);
            for (Sort.Order order : sort) {
                OrderSpecifier<?> os = toOrder(entityPath, order);
                if (os == null) {
                    continue;
                }
                orders.add(os);
            }
            return orders.toArray(new OrderSpecifier[orders.size()]);
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static OrderSpecifier<?> toOrder(
            final RelationalPath<?> entityPath, Sort.Order order) {
        String property;
        Expression<Object> propExp;
        if ((property = order.getProperty()) == null ||
                (propExp = (Expression<Object>) findPath(
                        entityPath, property)) == null) {
            return null;
        }
        return new OrderSpecifier(order.isAscending() ?
                Order.ASC : Order.DESC, propExp);
    }
}
