package com.springdemo.learningMVC.common.src.main.java.com.common.web.shiro.authc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.shiro.util.SimpleByteSource;

public class CustomByteSource extends SimpleByteSource {

    public static CustomByteSource of(String source) {
        return new CustomByteSource(source, null);
    }

    @JsonCreator
    @SuppressWarnings("unused")
    public CustomByteSource(
            @JsonProperty("bytes") String string,
            @JsonProperty("empty") Boolean empty) {
        super(string);
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return super.isEmpty();
    }
}