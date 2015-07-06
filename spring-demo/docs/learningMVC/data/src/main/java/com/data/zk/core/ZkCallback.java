package com.springdemo.learningMVC.data.src.main.java.com.data.zk.core;

import com.github.zkclient.ZkClient;

@FunctionalInterface
public interface ZkCallback<T> {
    T doInZkClient(ZkClient zkClient);
}
