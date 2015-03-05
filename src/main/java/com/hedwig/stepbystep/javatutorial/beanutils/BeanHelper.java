package com.hedwig.stepbystep.javatutorial.beanutils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class BeanHelper {
    private BeanHelper(){}

    public static <T> String getSimpleValue(T instance,String name){
        try {
            return BeanUtils.getSimpleProperty(instance, StringUtils.uncapitalize(name));
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        }
        return null;
    }
}
