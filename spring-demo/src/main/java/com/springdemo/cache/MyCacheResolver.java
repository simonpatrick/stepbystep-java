package com.springdemo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.CacheResolver;

import java.util.*;

public class MyCacheResolver implements CacheResolver {

    @Autowired
    private CacheManager cacheManager;

    @Override
    public Collection<? extends Cache> resolveCaches(CacheOperationInvocationContext<?> context) {
        List<Cache> caches = new ArrayList<Cache>();
        for(String cacheName : context.getOperation().getCacheNames()) {
            caches.add(cacheManager.getCache(cacheName));
        }
        if(context.getTarget() instanceof UserService2) {
            caches.add(cacheManager.getCache("user2"));
            caches.add(cacheManager.getCache("user3"));
        }
        return caches;
    }
}
