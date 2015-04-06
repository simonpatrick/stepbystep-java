package com.springdemo.learningMVC.common.src.main.java.com.common.web.shiro.subject;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.common.json.JsonMapper;
import com.common.json.JsonParseException;
import org.apache.shiro.subject.MutablePrincipalCollection;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ClassUtils;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * 自定义实现的{@code MutablePrincipalCollection}。
 *
 */
public class CustomPrincipalCollection implements MutablePrincipalCollection {

    public static final String PROP_MAPPING = "mapping";

    private static final long serialVersionUID = 7565650380496695040L;

    private Map<String, Set<Object>> realmPrincipals;

    // cached toString() result, as this can be printed many times in logging
    private transient String cachedToString;

    @JsonCreator
    @SuppressWarnings("unchecked")
    public static <T> CustomPrincipalCollection of(
            @JsonProperty(PROP_MAPPING) Map<String, Set<T>> mapping) {
        CustomPrincipalCollection principals = new CustomPrincipalCollection();
        if (mapping != null && !mapping.isEmpty()) {
            for (Map.Entry<String, Set<T>> entry : mapping.entrySet()) {
                if (entry.getKey() == null || entry.getValue() == null) {
                    continue;
                }
                Set<T> value = entry.getValue();
                Set<Object> realValue = Sets.newHashSetWithExpectedSize(value.size());
                for (T obj : value) {
                    if (obj == null) {
                        continue;
                    }
                    if (obj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        Map<String, Object> map = (Map<String, Object>) obj;
                        if (map.containsKey("clazz")) {
                            realValue.add(JsonMapper.getDefault().convertValue(map,
                                    ClassUtils.forName((String) map.get("clazz"))));
                            continue;
                        }
                    }
                    realValue.add(obj);
                }
                principals.addAll(realValue, entry.getKey());
            }
        }
        return principals;
    }

    public CustomPrincipalCollection() {
    }

    public CustomPrincipalCollection(Object principal, String realmName) {
        if (principal instanceof Collection) {
            addAll((Collection) principal, realmName);
        } else {
            add(principal, realmName);
        }
    }

    public CustomPrincipalCollection(Collection<?> principals, String realmName) {
        addAll((Collection) principals, realmName);
    }

    public CustomPrincipalCollection(PrincipalCollection principals) {
        addAll(principals);
    }

    protected Collection<Object> getPrincipalsLazy(String realmName) {
        if (realmPrincipals == null) {
            realmPrincipals = new LinkedHashMap<>();
        }
        Set<Object> principals = realmPrincipals.get(realmName);
        if (principals == null) {
            principals = new LinkedHashSet<>();
            realmPrincipals.put(realmName, principals);
        }
        return principals;
    }

    /**
     * Returns the first available principal from any of the {@code Realm} principals, or {@code null} if there are
     * no principals yet.
     * <p/>
     * The 'first available principal' is interpreted as the principal that would be returned by
     * <code>{@link #iterator() iterator()}.{@link java.util.Iterator#next() next()}.</code>
     *
     * @inheritDoc
     */
    @JsonIgnore
    public Object getPrimaryPrincipal() {
        if (isEmpty()) {
            return null;
        }
        return iterator().next();
    }

    public void add(Object principal, String realmName) {
        if (realmName == null) {
            throw new IllegalArgumentException("realmName argument cannot be null.");
        }
        if (principal == null) {
            throw new IllegalArgumentException("principal argument cannot be null.");
        }
        this.cachedToString = null;
        getPrincipalsLazy(realmName).add(principal);
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void addAll(Collection principals, String realmName) {
        if (realmName == null) {
            throw new IllegalArgumentException("realmName argument cannot be null.");
        }
        if (principals == null) {
            throw new IllegalArgumentException("principals argument cannot be null.");
        }
        if (principals.isEmpty()) {
            throw new IllegalArgumentException("principals argument cannot be an empty collection.");
        }
        this.cachedToString = null;
        getPrincipalsLazy(realmName).addAll(principals);
    }

    public void addAll(PrincipalCollection principals) {
        if (principals.getRealmNames() != null) {
            for (String realmName : principals.getRealmNames()) {
                for (Object principal : principals.fromRealm(realmName)) {
                    add(principal, realmName);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T oneByType(Class<T> type) {
        if (realmPrincipals == null || realmPrincipals.isEmpty()) {
            return null;
        }
        Collection<Set<Object>> values = realmPrincipals.values();
        for (Set<Object> set : values) {
            for (Object o : set) {
                if (type.isAssignableFrom(o.getClass())) {
                    return (T) o;
                } else if (o instanceof Map) {
                    return JsonMapper.getDefault().convertValue((Map<String, Object>) o, type);
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> Collection<T> byType(Class<T> type) {
        if (realmPrincipals == null || realmPrincipals.isEmpty()) {
            return ImmutableSet.of();
        }
        Set<T> typed = new LinkedHashSet<>();
        Collection<Set<Object>> values = realmPrincipals.values();
        for (Set<Object> set : values) {
            for (Object o : set) {
                if (type.isAssignableFrom(o.getClass())) {
                    typed.add((T) o);
                } else if (o instanceof Map) {
                    try {
                        Object val = JsonMapper.getDefault().convertValue((Map) o, type);
                        typed.add((T) val);
                    } catch (JsonParseException ex) {
                        // ignore
                        ex.printStackTrace();
                    }
                }
            }
        }
        if (typed.isEmpty()) {
            return ImmutableSet.of();
        }
        return ImmutableSet.copyOf(typed);
    }

    public List<Object> asList() {
        Set<?> all = asSet();
        if (all.isEmpty()) {
            return ImmutableList.of();
        }
        return ImmutableList.copyOf(all);
    }

    public Set<Object> asSet() {
        if (realmPrincipals == null || realmPrincipals.isEmpty()) {
            return ImmutableSet.of();
        }
        Set<Object> aggregated = new LinkedHashSet<>();
        Collection<Set<Object>> values = realmPrincipals.values();
        for (Set<Object> set : values) {
            aggregated.addAll(set);
        }
        if (aggregated.isEmpty()) {
            return ImmutableSet.of();
        }
        return ImmutableSet.copyOf(aggregated);
    }

    public Collection<Object> fromRealm(String realmName) {
        if (realmPrincipals == null || realmPrincipals.isEmpty()) {
            return ImmutableSet.of();
        }
        Set<Object> principals = realmPrincipals.get(realmName);
        if (principals == null || principals.isEmpty()) {
            principals = ImmutableSet.of();
        }
        return ImmutableSet.copyOf(principals);
    }

    @JsonProperty(PROP_MAPPING)
    @SuppressWarnings("unused")
    public Map<String, Set<Object>> getRealmPrincipals() {
        return ImmutableMap.copyOf(realmPrincipals);
    }

    @JsonIgnore
    public Set<String> getRealmNames() {
        if (realmPrincipals == null) {
            return null;
        } else {
            return realmPrincipals.keySet();
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return realmPrincipals == null || realmPrincipals.isEmpty();
    }

    public void clear() {
        this.cachedToString = null;
        if (realmPrincipals != null) {
            realmPrincipals.clear();
            realmPrincipals = null;
        }
    }

    public Iterator<?> iterator() {
        return asSet().iterator();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof CustomPrincipalCollection) {
            CustomPrincipalCollection other = (CustomPrincipalCollection) o;
            return this.realmPrincipals != null ? this.realmPrincipals.equals(other.realmPrincipals) : other.realmPrincipals == null;
        }
        return false;
    }

    public int hashCode() {
        if (this.realmPrincipals != null && !realmPrincipals.isEmpty()) {
            return realmPrincipals.hashCode();
        }
        return super.hashCode();
    }

    /**
     * Returns a simple string representation suitable for printing.
     *
     * @return a simple string representation suitable for printing.
     * @since 1.0
     */
    public String toString() {
        if (this.cachedToString == null) {
            Set<?> principals = asSet();
            if (!CollectionUtils.isEmpty(principals)) {
                this.cachedToString = StringUtils.toString(principals.toArray());
            } else {
                this.cachedToString = "empty";
            }
        }
        return this.cachedToString;
    }


    /**
     * Serialization write support.
     * <p/>
     * NOTE: Don't forget to change the serialVersionUID constant at the top of this class
     * if you make any backwards-incompatible serializatoin changes!!!
     * (use the JDK 'serialver' program for this)
     *
     * @param out output stream provided by Java serialization
     * @throws java.io.IOException if there is a stream error
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        boolean principalsExist = !CollectionUtils.isEmpty(realmPrincipals);
        out.writeBoolean(principalsExist);
        if (principalsExist) {
            out.writeObject(realmPrincipals);
        }
    }

    /**
     * Serialization read support - reads in the Map principals collection if it exists in the
     * input stream.
     * <p/>
     * NOTE: Don't forget to change the serialVersionUID constant at the top of this class
     * if you make any backwards-incompatible serializatoin changes!!!
     * (use the JDK 'serialver' program for this)
     *
     * @param in input stream provided by
     * @throws java.io.IOException            if there is an input/output problem
     * @throws ClassNotFoundException if the underlying Map implementation class is not available to the classloader.
     */
    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        boolean principalsExist = in.readBoolean();
        if (principalsExist) {
            this.realmPrincipals = (Map<String, Set<Object>>) in.readObject();
        }
    }
}