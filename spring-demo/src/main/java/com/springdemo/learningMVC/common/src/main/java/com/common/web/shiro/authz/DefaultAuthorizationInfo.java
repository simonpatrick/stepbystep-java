package com.springdemo.learningMVC.common.src.main.java.com.common.web.shiro.authz;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.common.json.JsonMapper;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;

import java.util.Set;

/**
 * Default {@code AuthorizationInfo} implementation.
 *
 */
public class DefaultAuthorizationInfo extends SimpleAuthorizationInfo {

    public static final String PROP_ROLES = "roles";
    public static final String PROP_STR_PERMS = "str_perms";
    public static final String PROP_OBJ_PERMS = "obj_perms";
    public static final String PROP_CLAZZ = "clazz";

    private static final long serialVersionUID = 1836467160398910402L;

    private final Class<DefaultAuthorizationInfo> clazz = DefaultAuthorizationInfo.class;

    public static DefaultAuthorizationInfo fromJSON(String jsonString) {
        if (jsonString == null || (jsonString = jsonString.trim()).length() == 0) {
            return null;
        }
        if (!(jsonString.startsWith("{") && jsonString.endsWith("}"))) {
            return null;
        }
        if (jsonString.contains("\"stringPermissions\":") ||
                jsonString.contains("\"objectPermissions\":")) {
            jsonString = jsonString.replace("stringPermissions", PROP_STR_PERMS)
                    .replace("objectPermissions", PROP_OBJ_PERMS);
        }

        return JsonMapper.getDefault().readValue(jsonString, DefaultAuthorizationInfo.class);
    }

    /**
     * Default no-argument constructor.
     */
    public DefaultAuthorizationInfo() {
        super();
    }

    /**
     * Creates a new instance with the specified roles and no permissions.
     * @param roles the roles assigned to the realm account.
     */
    public DefaultAuthorizationInfo(Set<String> roles) {
        super(roles);
    }

    public Class<DefaultAuthorizationInfo> getClazz() {
        return clazz;
    }

    /**
     * Returns the current class {@code Objects.ToStringHelper}.
     */
    protected MoreObjects.ToStringHelper toStringHelper() {
        return MoreObjects.toStringHelper(getClass())
                .add(PROP_CLAZZ, getClazz())
                .add(PROP_ROLES, getRoles())
                .add(PROP_STR_PERMS, getStringPermissions())
                .add(PROP_OBJ_PERMS, getObjectPermissions());
    }

    @Override
    @JsonProperty(PROP_STR_PERMS)
    public Set<String> getStringPermissions() {
        return super.getStringPermissions();
    }

    @Override
    @JsonProperty(PROP_OBJ_PERMS)
    public Set<Permission> getObjectPermissions() {
        return super.getObjectPermissions();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DefaultAuthorizationInfo)) return false;

        DefaultAuthorizationInfo that = (DefaultAuthorizationInfo) o;

        return Objects.equal(that.getRoles(), getRoles()) &&
                Objects.equal(that.getStringPermissions(), getStringPermissions()) &&
                Objects.equal(that.getObjectPermissions(), getObjectPermissions());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClazz(),
                getRoles(), getStringPermissions(), getObjectPermissions());
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

}