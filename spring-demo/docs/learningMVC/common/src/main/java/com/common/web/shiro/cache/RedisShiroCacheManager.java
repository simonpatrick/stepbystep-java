package com.springdemo.learningMVC.common.src.main.java.com.common.web.shiro.cache;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The redis based on implementation of the apache shiro cache manager.
 *
 */
public class RedisShiroCacheManager extends AbstractCacheManager {

    private final ConcurrentMap<String, RedisTemplate> templateMap =
            new ConcurrentHashMap<>();

    private final RedisTemplate template;

    // 0 - never expire
    private long defaultExpiration = 0;
    private Map<String, Long> expires = null;

    public RedisShiroCacheManager(RedisTemplate template) {
        this.template = template;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Cache createCache(String name) throws CacheException {
        long expiration = computeExpiration(name);
        return new RedisShiroCache(name, "", computeTemplate(name), expiration);
    }

    /**
     * Sets the default expire time (in seconds).
     *
     * @param defaultExpireTime time in seconds.
     */
    public void setDefaultExpiration(long defaultExpireTime) {
        this.defaultExpiration = defaultExpireTime;
    }

    /**
     * Sets the expire time (in seconds) for cache regions (by key).
     *
     * @param expires time in seconds
     */
    public void setExpires(Map<String, Long> expires) {
        this.expires = (expires != null ? new ConcurrentHashMap<>(expires) : null);
    }

    private long computeExpiration(String name) {
        Long expiration = null;
        if (expires != null) {
            expiration = expires.get(name);
        }
        return (expiration != null ? expiration : defaultExpiration);
    }

    private RedisTemplate computeTemplate(String name) {
        RedisTemplate template = templateMap.get(name);
        return template == null ? this.template : template;
    }

    public void setRedisTemplates(Map<String, RedisTemplate> templates) {
        Objects.requireNonNull(templates, "templates");
        if (templates.isEmpty()) {
            return;
        }
        templateMap.putAll(templates);
    }
}
