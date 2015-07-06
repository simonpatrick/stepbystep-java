package com.springdemo.learningMVC.data.src.main.java.com.data.redis;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.google.common.base.Verify;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ClassUtils;

import javax.annotation.Nonnull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

public class JsonRedisTemplate<T extends Serializable> extends RedisTemplate<String, T> {

    private static final boolean HAS_JODA_MODULE =
            ClassUtils.isPresent("com.fasterxml.jackson.datatype.joda.JodaModule",
                    JsonRedisTemplate.class.getClassLoader());
    private static final boolean HAS_GUAVA_MODULE =
            ClassUtils.isPresent("com.fasterxml.jackson.datatype.guava.GuavaModule",
                    JsonRedisTemplate.class.getClassLoader());
    private static final boolean HAS_JSR310_MODULE =
            ClassUtils.isPresent("com.fasterxml.jackson.datatype.jsr310.JSR310Module",
                    JsonRedisTemplate.class.getClassLoader());

    private static final byte[][] EMPTY_BYTES = new byte[0][0];

    private final StringRedisTemplate stringOperations;
    private final Class<T> targetClass;

    public JsonRedisTemplate(Class<T> targetClass) {
        this.targetClass = targetClass;
        stringOperations = new StringRedisTemplate();

        StringRedisSerializer stringSerializer = new StringRedisSerializer(UTF_8);
        ObjectMapper objectMapper = initObjectMapper();
        Jackson2JsonRedisSerializer<T> jsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(targetClass);
        jsonRedisSerializer.setObjectMapper(objectMapper);

        setKeySerializer(stringSerializer);
        setValueSerializer(jsonRedisSerializer);
        setHashKeySerializer(stringSerializer);
        setHashValueSerializer(new JdkSerializationRedisSerializer());
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        stringOperations.setConnectionFactory(getConnectionFactory());
        stringOperations.afterPropertiesSet();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RedisSerializer<String> getKeySerializer() {
        return (RedisSerializer<String>) super.getKeySerializer();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RedisSerializer<T> getValueSerializer() {
        return (RedisSerializer<T>) super.getValueSerializer();
    }

    @Override
    @SuppressWarnings("unchecked")
    public RedisSerializer<String> getHashKeySerializer() {
        return (RedisSerializer<String>) super.getHashKeySerializer();
    }

    /**
     * 返回内部使用的字符串操作接口。
     */
    public RedisOperations<String, String> innerOps() {
        return stringOperations;
    }

    /**
     * 序列化指定的对象的键。
     *
     * @param key 要转换的key。
     * @return 返回序列化的字节数组。
     */
    public byte[] rawKey(Object key) {
        requireNonNull(key, "Not null key required");
        if (getKeySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }
        requireNonNull(getKeySerializer(), "KeySerializer null");
        final String strKey = (key instanceof String) ? (String) key : key.toString();
        return getKeySerializer().serialize(strKey);
    }

    @Nonnull
    public byte[][] rawKeys(Collection<Object> keys) {
        if (keys == null || keys.isEmpty()) {
            return EMPTY_BYTES;
        }
        final byte[][] rawKeys = new byte[keys.size()][];
        int i = 0;
        for (Object key : keys) {
            rawKeys[i] = rawKey(key);
        }
        return rawKeys;
    }

    @SuppressWarnings("unchecked")
    public byte[] rawValue(Object value) {
        requireNonNull(value, "Not null value required");
        if (getValueSerializer() == null && value instanceof byte[]) {
            return (byte[]) value;
        }
        requireNonNull(getValueSerializer(), "ValueSerializer null");
        Verify.verify(targetClass.isAssignableFrom(value.getClass()),
                "Value is not a %s", targetClass);
        return getValueSerializer().serialize((T) value);
    }

    public String deserializeKey(byte[] bytes) {
        return getKeySerializer() != null ?
                getKeySerializer().deserialize(bytes) :
                new String(bytes, UTF_8);
    }

    public T deserializeValue(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return getValueSerializer().deserialize(bytes);
    }

    @Nonnull
    public List<T> deserializeValues(byte[][] bytes) {
        if (bytes == null || bytes.length == 0) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>(bytes.length);
        int i = 0;
        for (byte[] aByte : bytes) {
            list.add(i++, deserializeValue(aByte));
        }
        return list;
    }

    private ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        if (HAS_JODA_MODULE) {
            objectMapper.registerModule(new JodaModule());
        }
        if (HAS_GUAVA_MODULE) {
            objectMapper.registerModule(new GuavaModule());
        }
        if (HAS_JSR310_MODULE) {
            objectMapper.registerModule(new JSR310Module());
        }
        return objectMapper;
    }
}
