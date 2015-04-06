package com.springdemo.learningMVC.common.src.main.java.com.common.web.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.*;

/**
 * The redis based on implementation of the apache shiro cache.
 *
 * @author fuchun
 * @version $Id: RedisShiroCache.java 375 2014-11-03 09:27:01Z fuchun $
 * @since 2.0
 */
public class RedisShiroCache<K, V> implements Cache<K, V> {

    private static final int PAGE_SIZE = 128;
    private final RedisTemplate<K, V> template;
    private final String name;
    private final byte[] prefix;
    private final byte[] setKey;
    private final byte[] lockKey;
    private final long expiration;

    public RedisShiroCache(String name, String prefix, RedisTemplate<K, V> template, long expiration) {
        this.template = template;
        this.name = name;
        this.prefix = prefix == null ? null : prefix.getBytes();
        this.expiration = expiration;

        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        this.setKey = stringSerializer.serialize(name + ":keys");
        this.lockKey = stringSerializer.serialize(name + ":lock");
    }

    public String getName() {
        return name;
    }

    @Override
    public V get(K key) throws CacheException {
        return template.execute(connection -> {
            waitForLock(connection);
            return getInternal(connection, key);
        }, true);
    }

    @SuppressWarnings("unchecked")
    private V getInternal(RedisConnection connection, K key) {
        byte[] bs = connection.get(computeKey(key));
        Object value = template.getValueSerializer() != null ?
                template.getValueSerializer().deserialize(bs) : bs;
        return bs == null ? null : (V) value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public V put(final K key, V value) throws CacheException {
        final byte[] keyBytes = computeKey(key);
        final byte[] valueBytes = convertToBytesIfNecessary(
                (RedisSerializer<Object>) template.getValueSerializer(), value);

        return template.execute(connection -> {

            waitForLock(connection);
            V oldValue = getInternal(connection, key);

            connection.multi();

            connection.set(keyBytes, valueBytes);
            connection.zAdd(setKey, 0, keyBytes);

            if (expiration > 0) {
                connection.expire(keyBytes, expiration);
                // update the expiration of the set of keys as well
                connection.expire(setKey, expiration);
            }
            connection.exec();

            return oldValue;
        }, true);
    }

    @Override
    public V remove(final K key) throws CacheException {
        final byte[] k = computeKey(key);

        return template.execute(connection -> {
            waitForLock(connection);
            V oldValue = getInternal(connection, key);

            connection.del(k);
            // remove key from set
            connection.zRem(setKey, k);
            return oldValue;
        }, true);
    }

    @Override
    public void clear() throws CacheException {
        // need to del each key individually
        template.execute(connection -> {
            // another clear is on-going
            if (connection.exists(lockKey)) {
                return null;
            }

            try {
                connection.set(lockKey, lockKey);

                int offset = 0;
                boolean finished;

                do {
                    // need to paginate the keys
                    Set<byte[]> keys = connection.zRange(
                            setKey, (offset) * PAGE_SIZE, (offset + 1) * PAGE_SIZE - 1);
                    finished = keys.size() < PAGE_SIZE;
                    offset++;
                    if (!keys.isEmpty()) {
                        connection.del(keys.toArray(new byte[keys.size()][]));
                    }
                } while (!finished);

                connection.del(setKey);
                return null;

            } finally {
                connection.del(lockKey);
            }
        }, true);
    }

    @Override
    public int size() {
        return template.execute(connection -> {
            waitForLock(connection);
            Long card = connection.sCard(setKey);
            return card == null ? 0 : card.intValue();
        }, true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<K> keys() {
        return template.execute(connection -> {
            waitForLock(connection);
            Set<byte[]> sMembers = connection.sMembers(setKey);
            if (sMembers == null || sMembers.isEmpty()) {
                return Collections.<K>emptySet();
            }
            Set<K> result = new LinkedHashSet<>(sMembers.size());
            sMembers.stream().filter(b -> b != null && b.length > 0)
                    .forEach(b -> result.add((K) deserializeIfNecessary(
                            template.getKeySerializer(), b)));
            return result;
        }, true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<V> values() {
        return template.execute(connection -> {
            int offset = 0;
            boolean finished;
            final List<V> result = new LinkedList<>();
            do {
                // need to paginate the keys
                Set<byte[]> keys = connection.zRange(
                        setKey, (offset) * PAGE_SIZE, (offset + 1) * PAGE_SIZE - 1);
                finished = keys.size() < PAGE_SIZE;
                offset++;
                if (!keys.isEmpty()) {
                    List<byte[]> values = connection.mGet(
                            keys.toArray(new byte[keys.size()][]));
                    values.stream().filter(b -> b != null && b.length > 0)
                            .forEach(b -> result.add((V) deserializeIfNecessary(
                                    template.getValueSerializer(), b
                            )));
                }
            } while (!finished);

            return result;
        }, true);
    }

    @SuppressWarnings("unchecked")
    private byte[] computeKey(Object key) {

        byte[] keyBytes = convertToBytesIfNecessary(
                (RedisSerializer<Object>) template.getKeySerializer(), key);

        if (prefix == null || prefix.length == 0) {
            return keyBytes;
        }

        byte[] result = Arrays.copyOf(prefix, prefix.length + keyBytes.length);
        System.arraycopy(keyBytes, 0, result, prefix.length, keyBytes.length);

        return result;
    }

    private boolean waitForLock(RedisConnection connection) {

        boolean retry;
        boolean foundLock = false;
        do {
            retry = false;
            if (connection.exists(lockKey)) {
                foundLock = true;
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                retry = true;
            }
        } while (retry);

        return foundLock;
    }

    private byte[] convertToBytesIfNecessary(RedisSerializer<Object> serializer, Object value) {

        if (serializer == null && value instanceof byte[]) {
            return (byte[]) value;
        }
        assert serializer != null;
        return serializer.serialize(value);
    }

    private <T> Object deserializeIfNecessary(RedisSerializer<T> serializer, byte[] value) {
        if (serializer != null) {
            return serializer.deserialize(value);
        }

        return value;
    }
}
