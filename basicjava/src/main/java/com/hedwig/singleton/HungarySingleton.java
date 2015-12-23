package com.hedwig.singleton;

/**
 * Created by patrick on 15/12/2.
 */
public class HungarySingleton {

    //类加载时就初始化
    private static final HungarySingleton instance = new HungarySingleton();

    private HungarySingleton() {
    }

    public static HungarySingleton getInstance() {
        return instance;
    }
}
