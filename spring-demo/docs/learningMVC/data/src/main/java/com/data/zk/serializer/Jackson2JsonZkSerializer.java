package com.springdemo.learningMVC.data.src.main.java.com.data.zk.serializer;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Jackson2JsonZkSerializer<T> implements ZkSerializer<T> {

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private static final byte[] EMPTY_BYTES = new byte[0];

    private final JavaType javaType;

    private ObjectMapper objectMapper = new ObjectMapper();

    public Jackson2JsonZkSerializer(Class<T> targetType) {
        this.javaType = TypeFactory.defaultInstance().constructType(targetType);
    }

    public Jackson2JsonZkSerializer(Type targetType) {
        this.javaType = TypeFactory.defaultInstance().constructType(targetType);
    }

    @Override
    public byte[] serialize(T t) throws ZkSerializationException {
        if (t == null) {
            return EMPTY_BYTES;
        }
        try {
            return this.objectMapper.writeValueAsBytes(t);
        } catch (Exception ex) {
            throw new ZkSerializationException("Could not write JSON: " + ex.getMessage(), ex);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(byte[] bytes) throws ZkSerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return (T) this.objectMapper.readValue(bytes, 0, bytes.length, javaType);
        } catch (Exception ex) {
            throw new ZkSerializationException("Could not read JSON: " + ex.getMessage(), ex);
        }
    }

    /**
     * Sets the {@code ObjectMapper} for this view. If not set, a default {@link com.fasterxml.jackson.databind.ObjectMapper#ObjectMapper() ObjectMapper}
     * is used.
     * <p>
     * Setting a custom-configured {@code ObjectMapper} is one way to take further control of the JSON serialization
     * process. For example, an extended {@link com.fasterxml.jackson.databind.ser.SerializerFactory} can be configured that provides custom serializers for
     * specific types. The other option for refining the serialization process is to use Jackson's provided annotations on
     * the types to be serialized, in which case a custom-configured ObjectMapper is unnecessary.
     */
    public void setObjectMapper(ObjectMapper objectMapper) {

        Assert.notNull(objectMapper, "'objectMapper' must not be null");
        this.objectMapper = objectMapper;
    }

    /**
     * Returns the Jackson {@link com.fasterxml.jackson.databind.JavaType} for the specific class.
     * <p>
     * Default implementation returns {@link com.fasterxml.jackson.databind.type.TypeFactory#constructType(java.lang.reflect.Type)}, but this can be
     * overridden in subclasses, to allow for custom generic collection handling. For instance:
     *
     * <pre class="code">
     * protected JavaType getJavaType(Class&lt;?&gt; clazz) {
     * 	if (List.class.isAssignableFrom(clazz)) {
     * 		return TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, MyBean.class);
     * 	} else {
     * 		return super.getJavaType(clazz);
     * 	}
     * }
     * </pre>
     *
     * @param clazz the class to return the java type for
     * @return the java type
     */
    protected JavaType getJavaType(Class<?> clazz) {
        return TypeFactory.defaultInstance().constructType(clazz);
    }
}
