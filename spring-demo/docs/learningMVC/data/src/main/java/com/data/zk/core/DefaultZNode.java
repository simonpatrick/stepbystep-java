package com.springdemo.learningMVC.data.src.main.java.com.data.zk.core;

import org.apache.zookeeper.data.Stat;

public class DefaultZNode<T> extends AbstractZNode<T> {

    private static final long serialVersionUID = -3433279396182551907L;

    public DefaultZNode(T data, Stat stat) {
        this(data, stat,null);
    }

    public DefaultZNode(T data, Stat stat, String path) {
        super(data, stat);
        setPath(path);
    }

}
