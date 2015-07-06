package com.springdemo.learningMVC.common.src.main.java.com.common.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class JacksonJsonMapper implements JsonMapper {

    public static final JacksonJsonMapper DEFAULT_MAPPER = new JacksonJsonMapper();

    static {
        DEFAULT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        DEFAULT_MAPPER.initialize();
    }

    private ObjectMapper objectMapper;

    private JsonInclude.Include serializationInclusion = null;
    private MapperFeature[] enabledMapperFeatures = null;
    private MapperFeature[] disabledMapperFeatures = null;
    private SerializationFeature[] enabledSerializationFeatures = null;
    private SerializationFeature[] disabledSerializationFeatures = null;
    private DeserializationFeature[] enabledDeserializationFeatures = null;
    private DeserializationFeature[] disabledDeserializationFeatures = null;

    private FilterProvider filterProvider = null;

    public JacksonJsonMapper() {
        this(new ObjectMapper());
    }

    public JacksonJsonMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void initialize() {
        objectMapper.findAndRegisterModules();

        if (serializationInclusion != null)
            objectMapper.setSerializationInclusion(serializationInclusion);
        if (enabledMapperFeatures != null && enabledMapperFeatures.length > 0) {
            objectMapper.enable(enabledMapperFeatures);
        }
        if (disabledMapperFeatures != null && disabledMapperFeatures.length > 0) {
            objectMapper.disable(disabledMapperFeatures);
        }

        initSerializationFeatures();
        initDeserializationFeatures();

        if (filterProvider != null) {
            objectMapper.setFilters(filterProvider);
        }
    }

    private void initSerializationFeatures() {
        if (enabledSerializationFeatures != null && enabledSerializationFeatures.length > 0) {
            if (enabledSerializationFeatures.length == 1) {
                objectMapper.enable(enabledSerializationFeatures[0]);
            } else {
                objectMapper.enable(enabledSerializationFeatures[0],
                        Arrays.copyOfRange(enabledSerializationFeatures, 1,
                                enabledSerializationFeatures.length));
            }
        }
        if (disabledSerializationFeatures != null && disabledSerializationFeatures.length > 0) {
            if (disabledSerializationFeatures.length == 1) {
                objectMapper.disable(disabledSerializationFeatures[0]);
            } else {
                objectMapper.disable(disabledSerializationFeatures[0],
                        Arrays.copyOfRange(disabledSerializationFeatures, 1,
                                disabledSerializationFeatures.length));
            }
        }
    }

    private void initDeserializationFeatures() {
        if (enabledDeserializationFeatures != null && enabledDeserializationFeatures.length > 0) {
            if (enabledDeserializationFeatures.length == 1) {
                objectMapper.enable(enabledDeserializationFeatures[0]);
            } else {
                objectMapper.enable(enabledDeserializationFeatures[0],
                        Arrays.copyOfRange(enabledDeserializationFeatures, 1,
                                enabledDeserializationFeatures.length));
            }
        }
        if (disabledDeserializationFeatures != null && disabledDeserializationFeatures.length > 0) {
            if (disabledDeserializationFeatures.length == 1) {
                objectMapper.disable(disabledDeserializationFeatures[0]);
            } else {
                objectMapper.disable(disabledDeserializationFeatures[0],
                        Arrays.copyOfRange(disabledDeserializationFeatures, 1,
                                disabledDeserializationFeatures.length));
            }
        }
    }

    private String trimToEmpty(String input) {
        checkArgument(input != null, "The given input com.common.json must not be null!");
        assert input != null;
        return input.trim();
    }

    private JsonParseException rethrow(Object input, Type type, Throwable ex) {
        String targetJson;
        if (input instanceof String) {
            targetJson = (String) input;
        } else if (input instanceof byte[]) {
            targetJson = new String((byte[]) input);
        } else {
            targetJson = input.toString();
        }
        return new JsonParseException(
                String.format("The target com.common.json (%s) cannot parse to %s.", targetJson, type), ex);
    }

    private <T extends Type> void checkType(T type) {
        checkArgument(type != null, "The given type must not be null!");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T> T convertValue(Map<String, Object> hashMap, Class<T> clazz) {
        checkArgument(clazz != null);
        if (hashMap == null || hashMap.isEmpty()) {
            return null;
        }
        T result;
        try {
            result = objectMapper.convertValue(hashMap, clazz);
        } catch (Exception ex) {
            throw new JsonParseException(ex);
        }
        return result;
    }

    @Override
    public String toJSONString(Object o) {
        checkNotNull(o);
        String json;
        try {
            json = objectMapper.writeValueAsString(o);
        } catch (Exception ex) {
            throw new JsonParseException(
                    String.format("The target object (%s) cannot parse to JSON string.", o), ex);
        }
        return json;
    }

    @Override
    public byte[] toJSONBytes(Object o) {
        checkNotNull(o);
        byte[] bytes;
        try {
            bytes = objectMapper.writeValueAsBytes(o);
        } catch (Exception ex) {
            throw new JsonParseException(
                    String.format("The target object (%s) cannot parse to JSON string.", o), ex);
        }
        return bytes;
    }

    @Override
    public <T> T readValue(String input, Class<T> clazz) {
        return readValue(input, (Type) clazz);
    }

    @Override
    public <T> T readValue(byte[] bytes, Class<T> clazz) {
        return readValue(bytes, (Type) clazz);
    }

    @Override
    public <T> T readValue(String input, Type type) {
        return readValueInner(input, type, true);
    }

    @Override
    public <T> T readValue(byte[] bytes, Type type) {
        return readValueInner(bytes, type, false);
    }

    private <T> T readValueInner(Object value, Type type, boolean checkNull) {
        if (checkNull) {
            checkArgument(value != null, "The given target value must not be null!");
        } else {
            if (value == null) {
                return null;
            }
        }
        assert value != null;
        String json;
        if (value instanceof String) {
            json = trimToEmpty((String) value);
        } else if (value instanceof byte[]) {
            byte[] bytes = (byte[]) value;
            json = new String(bytes, StandardCharsets.UTF_8);
        } else {
            json = value.toString();
        }
        if (json.length() == 0 || "{}".equals(json)) {
            return null;
        }
        T result;
        try {
            result = objectMapper.readValue(json, TypeFactory.defaultInstance().constructType(type));
        } catch (Exception ex) {
            throw rethrow(json, type, ex);
        }
        return result;
    }

    @Override
    public <T> List<T> readToList(String input, Class<T> eClass) {
        return readToList(input, (Type) eClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> readToList(String input, Type eType) {
        return readToCollection(input, List.class, eType);
    }

    @Override
    public <T> List<T> readToList(byte[] bytes, Class<T> eClass) {
        return readToList(bytes, (Type) eClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> readToList(byte[] bytes, Type eType) {
        return readToCollection(bytes, List.class, eType);
    }

    @Override
    public <T> Set<T> readToSet(String input, Class<T> eClass) {
        return readToSet(input, (Type) eClass);
    }

    @Override
    public <T> Set<T> readToSet(byte[] bytes, Class<T> eClass) {
        return readToSet(bytes, (Type) eClass);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<T> readToSet(String input, Type eType) {
        return readToCollection(input, Set.class, eType);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<T> readToSet(byte[] bytes, Type eType) {
        return (Set<T>) readToSet(bytes, TypeFactory.rawClass(eType));
    }

    @Override
    public <K, V> Map<K, V> readToMap(String input, Class<K> keyClass, Class<V> valClass) {
        return readToMapInner(input, keyClass, valClass);
    }

    @Override
    public <K, V> Map<K, V> readToMap(String input, Type keyType, Type valType) {
        return readToMapInner(input, keyType, valType);
    }

    @Override
    public <K, V> Map<K, V> readToMap(byte[] bytes, Class<K> keyClass, Class<V> valClass) {
        return readToMapInner(bytes, keyClass, valClass);
    }

    @Override
    public <K, V> Map<K, V> readToMap(byte[] bytes, Type keyType, Type valType) {
        return readToMapInner(bytes, keyType, valType);
    }

    private <K, V> Map<K, V> readToMapInner(Object input, Type keyClass, Type valClass) {
        checkArgument(input != null);
        assert input != null;
        String json;
        if (input instanceof String) {
            json = trimToEmpty((String) input);
        } else if (input instanceof byte[]) {
            json = new String((byte[]) input, StandardCharsets.UTF_8);
        } else {
            json = input.toString();
        }
        if (json.length() == 0 || "{}".equals(json)) {
            return ImmutableMap.of();
        }
        MapType mapType = TypeFactory.defaultInstance().constructMapType(
                Map.class, TypeFactory.rawClass(keyClass), TypeFactory.rawClass(valClass));
        Map<K, V> result;
        try {
            result = objectMapper.readValue(json, mapType);
        } catch (Exception ex) {
            throw rethrow(json, mapType, ex);
        }
        return result;
    }

    private <C extends Collection<T>, T> C readToCollection(
            byte[] input, Class<C> collectType, Type elementType) {
        return readToCollection(new String(input), collectType, elementType);
    }

    @SuppressWarnings("unchecked")
    private <C extends Collection<T>, T> C readToCollection(
            String input, Class<C> collectType, Type elementType) {
        final String json = trimToEmpty(input);
        if (json.isEmpty() || "[]".equals(json)) {
            if (collectType.isAssignableFrom(List.class) ||
                    collectType.isAssignableFrom(Iterable.class)) {
                return (C) ImmutableList.of();
            } else if (collectType.isAssignableFrom(Set.class)) {
                return (C) ImmutableSet.of();
            } else {
                return (C) Collections.EMPTY_LIST;
            }
        }
        C result;
        CollectionType type = null;
        try {
            type = TypeFactory.defaultInstance().constructCollectionType(
                    collectType, TypeFactory.rawClass(elementType));
            result = objectMapper.readValue(json, type);
        } catch (Exception ex) {
            throw rethrow(json, type, ex);
        }
        return result;
    }

    @Override
    public <T> T readValue(File targetFile, Class<T> type) throws IOException {
        return readValue(targetFile, (Type) type);
    }

    @Override
    public <T> T readValue(File targetFile, Type type) throws IOException {
        checkArgument(targetFile != null);
        checkType(type);

        T value;
        try {
            value = objectMapper.readValue(targetFile, TypeFactory.defaultInstance().constructType(type));
        } catch (com.fasterxml.jackson.core.JsonParseException | JsonMappingException ex) {
            throw rethrow(targetFile, type, ex);
        }
        return value;
    }

    @Override
    public <T> T readValue(InputStream input, Class<T> type) throws IOException {
        return readValue(input, (Type) type);
    }

    @Override
    public <T> T readValue(InputStream input, Type type) throws IOException {
        checkArgument(input != null, "The given InputStream must not be null!");
        checkType(type);

        T value;
        try {
            value = objectMapper.readValue(input, TypeFactory.defaultInstance().constructType(type));
        } catch (com.fasterxml.jackson.core.JsonParseException | JsonMappingException ex) {
            throw rethrow(input, type, ex);
        }
        return value;
    }

    @Override
    public void writeValue(File resultFile, Object target) throws IOException {
        checkArgument(resultFile != null);
        checkArgument(target != null);
        assert resultFile != null && target != null;
        writeValue(Files.newOutputStream(resultFile.toPath()), target);
    }

    @Override
    public void writeValue(OutputStream out, Object target) throws IOException {
        checkArgument(out != null);
        checkArgument(target != null);
        assert out != null && target != null;

        try {
            objectMapper.writeValue(out, target);
        } catch (com.fasterxml.jackson.core.JsonParseException | JsonMappingException ex) {
            throw new JsonParseException(
                    String.format("The target (%s) cannot parse to JSON string", out), ex);
        }
    }

    public void setEnabledMapperFeatures(MapperFeature[] enabledMapperFeatures) {
        this.enabledMapperFeatures = enabledMapperFeatures;
    }

    public void setDisabledMapperFeatures(MapperFeature[] disabledMapperFeatures) {
        this.disabledMapperFeatures = disabledMapperFeatures;
    }

    public void setEnabledSerializationFeatures(SerializationFeature[] enabledSerializationFeatures) {
        this.enabledSerializationFeatures = enabledSerializationFeatures;
    }

    public void setDisabledSerializationFeatures(SerializationFeature[] disabledSerializationFeatures) {
        this.disabledSerializationFeatures = disabledSerializationFeatures;
    }

    public void setEnabledDeserializationFeatures(DeserializationFeature[] enabledDeserializationFeatures) {
        this.enabledDeserializationFeatures = enabledDeserializationFeatures;
    }

    public void setDisabledDeserializationFeatures(DeserializationFeature[] disabledDeserializationFeatures) {
        this.disabledDeserializationFeatures = disabledDeserializationFeatures;
    }

    public void setFilterProvider(FilterProvider filterProvider) {
        this.filterProvider = filterProvider;
    }

    public void setSerializationInclusion(JsonInclude.Include serializationInclusion) {
        this.serializationInclusion = serializationInclusion;
    }
}