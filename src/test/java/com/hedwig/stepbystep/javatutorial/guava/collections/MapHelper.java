package com.hedwig.stepbystep.javatutorial.guava.collections;

import com.google.common.base.Function;

import java.util.Map;

/**
 * Created by patrick on 15/3/16.
 *
 * @version $Id$
 */


public class MapHelper {

    public static <K,V,R> void  putOrUpdateMap(Map<K,V> map,K key, V value,Function<V,R> function){
        if(map.get(key)!=null){
            function.apply(map.get(key));
        }{
            map.put(key,value);
        }
    }
}
