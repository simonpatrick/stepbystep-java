package com.springdemo.learningMVC.data.src.main.java.com.data.repository.support;

import com.data.qdomain.AbstractEntity;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import org.springframework.data.repository.CrudRepository;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Supplier;

public abstract class RepositoryInterceptor {

    private static final Set<String> DEFAULT_METHODS =
            ImmutableSet.of("findOne", "findAll", "findBy", "get", "list", "find");

    private final Set<String> methodStarting = new HashSet<>(16);

    protected RepositoryInterceptor() {
        super();
        methodStarting.addAll(DEFAULT_METHODS);
    }

    protected Object doInjectRepository(
            Object proxy, String method, Supplier<Object> resultSupplier) {
        Object target = resultSupplier.get();
        if (target == null) {
            return null; // do anything
        } else if (!isIntercept(method)) {
            return target;
        }
        Class<?> targetClass = target.getClass();
        // Entity object
        if (canInjectRepository(target)) {
            injectRepository(target, proxy);
        }
        // Collection entities (List, Set ...)
        else if (Collection.class.isAssignableFrom(targetClass)) {
            ((Collection<?>) target).stream()
                    .filter(this::canInjectRepository)
                    .forEach(o -> injectRepository(o, proxy));
        }
        // Iterable implementation entities
        else if (Iterable.class.isAssignableFrom(targetClass)) {
            Iterables.filter((Iterable<?>) target, this::canInjectRepository)
                    .forEach(o -> injectRepository(o, proxy));
        }
        // Entity[] arrays
        else if (targetClass.isArray() && Array.getLength(target) > 0) {
            Arrays.asList((Object[]) target).stream()
                    .filter(this::canInjectRepository)
                    .forEach(o -> injectRepository(o, proxy));
        }
        // Map entities (only value) Map<SomeType, Entity>
        else if (Map.class.isAssignableFrom(targetClass)) {
            ((Map<?, ?>) target).forEach((k, v) -> {
                if (canInjectRepository(v)) {
                    injectRepository(v, proxy);
                }
            });
        }
        return target;
    }

    private boolean isIntercept(String method) {
        for (String s : methodStarting) {
            if (method.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    private boolean canInjectRepository(Object component) {
        return component != null &&
                AbstractEntity.class.isAssignableFrom(
                        component instanceof Class ? (Class<?>) component :
                                component.getClass());
    }

    @SuppressWarnings("unchecked")
    private void injectRepository(Object component, Object proxy) {
        if (component == null) {
            return;
        }
        ((AbstractEntity) component).setRepository((CrudRepository) proxy);
    }

    public void setMethodStarting(Collection<String> methods) {
        if (methods == null || methods.isEmpty()) {
            return;
        }
        methodStarting.addAll(methods);
    }
}
