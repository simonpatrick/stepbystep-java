package com.springdemo.learningMVC.data.src.main.java.com.data.zk.serializer;

public interface ZkSerializer<T> {

    public byte[] serialize(T t) throws ZkSerializationException;

    public T deserialize(byte[] bytes) throws ZkSerializationException;
}
