package com.hedwig.stepbystep.javatutorial.beanutils.datafixture;


import com.google.common.base.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by kewu on 14-2-27.
 */
public class CollectionsUtils {

    public static <T> List<T> filter(List<T> collections,Predicate<T> predicate){
        List<T> result = new ArrayList<T>();

        for (T collection : collections) {
            if(predicate.apply(collection)){
                result.add(collection);
            }
        }

        return result;
    }


    public static <T> List<T> filter(T[] collections,Predicate<T> predicate){
        List<T> result = new ArrayList<T>();

        for (T collection : collections) {
            if(predicate.apply(collection)){
                result.add(collection);
            }
        }

        return result;
    }
}
