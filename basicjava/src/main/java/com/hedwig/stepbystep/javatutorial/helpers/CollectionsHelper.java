package com.hedwig.stepbystep.javatutorial.helpers;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;

/**
 * 集合操作工具类
 */
public class CollectionsHelper {

    private CollectionsHelper(){}

     /**
     * 根据条件过滤数组元素
     * @param source
     * @param condition
     * @param <T>
     * @return 如果全部过滤完则返回null
     */
    public static <T> T filterByCondition(T[] source, Predicate<T> condition){

        for (T s : source) {
            if (condition.apply(s)) return s;
        }

        return null;
    }


    /**
     *根据多个条件过滤集合元素
     * @param source
     * @param condition
     * @param <T>
     * @return 如果全部过滤完则返回null
     */
    public static <T> T filterByCondition(Collection<T> source, Predicate<T> condition){

        for (T s : source) {
            if (condition.apply(s)) return s;
        }

        return null;
    }

    /**
     *根据条件过滤集合元素
     * @param source
     * @param conditions
     * @param <T>
     * @return 如果全部过滤完则返回null
     */
    public static <T> T filterByOrConditions(Collection<T> source, Predicate<T> ...conditions){

        for (T s : source) {
            for (Predicate<T> condition : conditions) {
                if(condition.apply(s)) return s;
            }
        }

        return null;
    }

    /**
     * add all elements to target
     * @param target
     * @param source
     * @param <T>
     */
    public static <T> void addAll(Collection<T> target,T...source){
        for (T t : target) {
          target.add(t);
        }
    }

    /**
     * 根据条件过滤集合元素
     * @param source
     * @param condition
     * @param <T>
     * @return 如果全部过滤完则返回空ArrayList
     */
    public static <T> List<T> filter(Collection<T> source, Predicate<T> condition){
        List<T> result = Lists.newArrayList();
        for (T s : source) {
            if(s==null) continue;
            if(condition.apply(s)) result.add(s);
        }

        return result;
    }

    /**
     * return if one array contains another array
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> boolean arrayContains(T[] source,T target){
        for (T t : source) {
           if(t.equals(target)) return true;
        }
        return false;
    }

    /**
     * convert array to List
     * @param <T>
     * @param objects
     * @return a ArrayList contains the objects
     */
    public static <T> List<T> arrayToList(T[] objects){
        List<T> result = Lists.newArrayList();
        for (T object : objects) {
            result.add(object);
        }
        return result;
    }

    public static <T> String  join(List<T> source, Function<T,String> function,String separator){
        StringBuilder sb = new StringBuilder();
        for (T t : source) {
            sb.append(function.apply(t));
            sb.append(separator);
        }

        return  sb.toString();
    }
}
