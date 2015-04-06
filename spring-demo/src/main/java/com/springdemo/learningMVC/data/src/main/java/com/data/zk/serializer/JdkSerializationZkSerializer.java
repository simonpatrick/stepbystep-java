package com.springdemo.learningMVC.data.src.main.java.com.data.zk.serializer;


import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;

public class JdkSerializationZkSerializer implements ZkSerializer<Object> {

    private static final byte[] EMPTY_BYTES = new byte[0];
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();

    @Override
    public byte[] serialize(Object o) throws ZkSerializationException {
        if (o == null) {
            return EMPTY_BYTES;
        }
        try {
            return serializer.convert(o);
        } catch (Exception ex) {
            throw new ZkSerializationException("Cannot serialize", ex);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkSerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return deserializer.convert(bytes);
        } catch (Exception ex) {
            throw new ZkSerializationException("Cannot deserialize", ex);
        }
    }
}