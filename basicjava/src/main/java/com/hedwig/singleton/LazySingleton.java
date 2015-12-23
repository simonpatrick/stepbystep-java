package com.hedwig.singleton;

import java.util.Random;

/**
 * Created by patrick on 15/12/2.
 */
public class LazySingleton {

    private static LazySingleton instance;
    private LazySingleton(){}

    public static LazySingleton getInstance(){
        if(instance==null){
            synchronized (LazySingleton.class){
                instance=new LazySingleton();
            }
        }

        return instance;
    }
}
