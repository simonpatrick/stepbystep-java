
package com.springdemo.learningMVC.common.src.main.java.com.common.web.shiro.authc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import com.common.web.shiro.subject.CustomPrincipalCollection;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * The custom {@link SimpleAuthenticationInfo} implementation based on JSON serialization.
 */
@JsonTypeInfo(use = Id.CLASS, include = As.PROPERTY, property = "class")
public class DefaultAuthenticationInfo extends SimpleAuthenticationInfo {

    public static final String PROP_CREDENTIALS_SALT = "credentials_salt";
    public static final String PROP_PRINCIPALS = "principals";
    public static final String PROP_CREDENTIALS = "credentials";
    public static final String PROP_CLAZZ = "clazz";

    private static final long serialVersionUID = -1225525892817911147L;

    @JsonCreator
    @SuppressWarnings("unused")
    public static DefaultAuthenticationInfo of(
            @JsonProperty(PROP_PRINCIPALS)
            @JsonDeserialize(as = CustomPrincipalCollection.class) PrincipalCollection principals,
            @JsonProperty(PROP_CREDENTIALS) Object hashedCredentials,
            @JsonProperty(PROP_CREDENTIALS_SALT)
            @JsonDeserialize(as = CustomByteSource.class) ByteSource credentialsSalt,
            @JsonProperty(PROP_CLAZZ) Class<DefaultAuthenticationInfo> clazz) {
        return new DefaultAuthenticationInfo(principals, hashedCredentials, credentialsSalt, clazz);
    }

    private final Class<DefaultAuthenticationInfo> clazz = DefaultAuthenticationInfo.class;

    public DefaultAuthenticationInfo(
            PrincipalCollection principals, Object hashedCredentials,
            ByteSource credentialsSalt) {
        this(principals, hashedCredentials, credentialsSalt, null);
    }

    DefaultAuthenticationInfo(
            PrincipalCollection principals, Object hashedCredentials,
            ByteSource credentialsSalt,
            @SuppressWarnings("unused") Class<DefaultAuthenticationInfo> clazz) {
        this.principals = principals;
        this.credentials = hashedCredentials;
        this.credentialsSalt = credentialsSalt;
    }

    @Override
    @JsonProperty(PROP_CREDENTIALS_SALT)
    public ByteSource getCredentialsSalt() {
        return super.getCredentialsSalt();
    }

    @Override
    public PrincipalCollection getPrincipals() {
        return super.getPrincipals();
    }

    @Override
    public Object getCredentials() {
        return super.getCredentials();
    }

    @JsonIgnore
    @SuppressWarnings("unused")
    public Class<DefaultAuthenticationInfo> getClazz() {
        return clazz;
    }
}