
package com.springdemo.learningMVC.common.src.main.java.com.common.web.shiro.authc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * The custom implementation for {@code Hash}.
 *
 */
public class CustomHash extends SimpleHash {

    private static final long serialVersionUID = 9098363303471387627L;

    public static final String PROP_ALGORITHM = "algorithmName";
    public static final String PROP_BYTES = "bytes";
    public static final String PROP_SALT = "salt";
    public static final String PROP_ITERATIONS = "iterations";

    @JsonCreator
    @SuppressWarnings("unused")
    public CustomHash(
            @JsonProperty(PROP_ALGORITHM) String algorithmName,
            @JsonProperty(PROP_BYTES) Object source,
            @JsonProperty(PROP_SALT) Object salt,
            @JsonProperty(PROP_ITERATIONS) int hashIterations) {
        super(algorithmName, source, salt, hashIterations);
    }

    @Override
    public String getAlgorithmName() {
        return super.getAlgorithmName();
    }

    @Override
    public ByteSource getSalt() {
        return super.getSalt();
    }

    @Override
    @JsonDeserialize(as = CustomByteSource.class)
    public void setSalt(ByteSource salt) {
        super.setSalt(salt);
    }

    @Override
    @JsonIgnore
    public boolean isEmpty() {
        return super.isEmpty();
    }
}