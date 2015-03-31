package com.hedwig.stepbystep.javatutorial.helpers;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Map;

/**
 * map 工具类
 */
public class MapsHelper {
    private MapsHelper(){}

    /**
     * put key/value into map
     * @param source
     * @param key
     * @param value
     * @param <K>
     * @param <V>
     */
    public static <K,V> void put(Map<K, List<V>> source,K key,V value){
        if(source.get(key)==null){
            source.put(key, Lists.newArrayList(value));
        }else{
            source.get(key).add(value);
        }
    }

}
