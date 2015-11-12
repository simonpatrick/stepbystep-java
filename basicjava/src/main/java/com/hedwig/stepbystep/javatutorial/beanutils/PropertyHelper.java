package com.hedwig.stepbystep.javatutorial.beanutils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class PropertyHelper {
    private PropertyHelper(){}

    public static <T> Object getSimpleValue(T instance,String name){
        try {
            return PropertyUtils.getSimpleProperty(instance, StringUtils.uncapitalize(name));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> Object getIndexValue(T instance,String name){
        try {
            return PropertyUtils.getIndexedProperty(instance,name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static <T> Object getIndexValue(T instance,String name,int index){
        try {
            return PropertyUtils.getIndexedProperty(instance,name,index);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> Object getMappedValue(T instance,String name){

        try {
            return PropertyUtils.getMappedProperty(instance,name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> Object getMappedValue(T instance,String name,String key){

        try {
            return PropertyUtils.getMappedProperty(instance,name,key);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static <T> Object getValue(T instance,String name){
        try {
            return PropertyUtils.getProperty(instance,name);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }
}
