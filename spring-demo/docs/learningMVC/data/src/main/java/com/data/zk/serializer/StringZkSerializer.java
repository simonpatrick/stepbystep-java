package com.springdemo.learningMVC.data.src.main.java.com.data.zk.serializer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class StringZkSerializer implements ZkSerializer<String> {

    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private Charset charset;

    public StringZkSerializer() {
        this(DEFAULT_CHARSET);
    }

    public StringZkSerializer(Charset charset){
        this.charset = charset;
    }

    @Override
    public byte[] serialize(String s) throws ZkSerializationException {
        return s != null ? s.getBytes(getCharset()) : null;
    }

    @Override
    public String deserialize(byte[] bytes) throws ZkSerializationException {
        return bytes == null ? null : new String(bytes, getCharset());
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    public void setCharEncoding(String encoding) {
        this.charset = Charset.forName(encoding);
    }
}
