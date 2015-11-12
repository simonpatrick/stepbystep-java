package com.hedwig.stepbystep.javatutorial.beanutils.datafixture;

import com.google.common.base.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by kewu on 14-2-27.
 */
public class ReflectionUtils {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtils.class);
    public static<T> String getFieldValue(T instance,String fieldName) {

        //search fields
        //get value
        Field[] fields = instance.getClass().getDeclaredFields();
        String result=null;
        for (Field field : fields) {
            if(field.getName().equalsIgnoreCase(fieldName)){

                field.setAccessible(true);
                try {
                    result =(String)field.get(instance);
                } catch (IllegalAccessException e) {
                   logger.error("error_message={}",e);
                }
            }
        }

        return result;
    }

    //todo to understand why Field can't apply
   /* public static <T> String getFieldValue(T instance,Predicate<T> predicate){

        Field[] fields = instance.getClass().getDeclaredFields();
        String result=null;
        List<Field> filtered = CollectionsUtils.filter(fields
                ,predicate);


        return result;
    }*/

}
