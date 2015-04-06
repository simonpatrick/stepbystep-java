package com.springdemo.learningMVC.data.src.main.java.com.data.zk.core;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import org.apache.zookeeper.data.Stat;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class AbstractZNode<T> implements ZNode<T>,Serializable {
    private static final long serialVersionUID = 1697064690293586868L;
    private String path;
    private T data;
    private ZNode<?> parent;
    private Set<ZNode<?>> children; // no duplicated
    private transient Stat stat;

    public AbstractZNode(T data, Stat stat) {
        this.data = data;
        this.stat = stat;
    }

    @Override
    public String getPath() {
        return path;
    }

    @Override
    public T getData() {
        return data;
    }

    @Override
    public long getCzxid() {
        return stat == null ? -1L : stat.getCzxid();
    }

    @Override
    public long getMzxid() {
        return stat == null ? -1L : stat.getMzxid();
    }

    @Override
    public long getCtime() {
        return stat==null?-1L:stat.getCtime();
    }

    @Override
    public long getMtime() {
        return stat==null?-1L:stat.getMtime();
    }

    @Override
    public int getVersion() {
        return stat==null?-1:stat.getVersion();
    }

    @Override
    public int getCversion() {
        return stat==null?-1:stat.getCversion();
    }

    @Override
    public int getAversion() {
        return stat==null?-1:stat.getAversion();
    }

    @Override
    public long getEphemeralOwner() {
        return stat==null?-1:stat.getEphemeralOwner();
    }

    @Override
    public int getDataLength() {
        return stat==null?-1:stat.getDataLength();
    }

    @Override
    public int getNumChildren() {
        return stat==null?0:stat.getNumChildren();
    }

    @Override
    public long getPzxid() {
        return stat==null?-1:stat.getPzxid();
    }

    @Override
    public ZNode<?> getParent() {
        return parent;
    }

    @Override
    public List<ZNode<?>> getChildren() {
        return children==null?null: ImmutableList.copyOf(children);
    }

    public AbstractZNode<T> addChildren(Collection<? extends ZNode<?>> col) {
        if (col == null || col.isEmpty()) {
            return this;
        }
        if (children == null) {
            children = new HashSet<>(col);
        } else {
            children.addAll(col);
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <Z extends AbstractZNode<T>> Z addChild(ZNode<?> node) {
        if (node == null) {
            return (Z) this;
        }
        if (children == null) {
            children = new HashSet<>();
        } else {
            children.add(node);
        }
        return (Z) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractZNode)) return false;

        AbstractZNode that = (AbstractZNode) o;

        if (path != null ? !path.equals(that.path) : that.path != null) return false;
        if (stat != null ? !stat.equals(that.stat) : that.stat != null) return false;
        if (data != null ? !data.equals(that.data) : that.data != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (stat != null ? stat.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(getClass())
                .add("path", getPath())
                .add("stat", getStat())
                .add("data", getData())
                .add("children", getChildren() == null ? null :
                        getChildren().stream().map(ZNode::getPath)
                                .collect(Collectors.toList()))
                .toString();
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setParent(ZNode<?> parent) {
        this.parent = parent;
    }

    public void setChildren(Set<ZNode<?>> children) {
        this.children = children;
    }

    public Stat getStat() {
        return stat;
    }

    public void setStat(Stat stat) {
        this.stat = stat;
    }
}
