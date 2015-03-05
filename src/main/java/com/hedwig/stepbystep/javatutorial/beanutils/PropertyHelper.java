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
}
