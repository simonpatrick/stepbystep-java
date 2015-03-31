package com.hedwig.stepbystep.javatutorial.servicetest.base;

import com.alibaba.fastjson.JSON;
import com.hedwig.stepbystep.javatutorial.helpers.CollectionsHelper;
import com.hedwig.stepbystep.javatutorial.helpers.ReflectionHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;

/**
 * Json Entity的基类，用来比对json返回值,重写了equals，hashcode方法
 */
public class BaseJsonEntity {
    private static final Logger logger = LogManager.getLogger(BaseJsonEntity.class.getName());

    /**
     * 比对返回值时可以忽略某些字段
     * @param target source
     * @param fieldName
     * @return
     */
    public boolean equalsIgnoreFields(Object target, String ... fieldName){
        if (this == target) return true;
        if (target == null || getClass() != target.getClass()) return false;
        for (Field field : this.getClass().getDeclaredFields()) {
            if(!CollectionsHelper.arrayContains(fieldName, field.getName())){
                if(!compareField(target,field)) return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        for (Field field : this.getClass().getDeclaredFields()) {
           if(!compareField(o,field)) return false;
        }
        return true;
    }

    /**
     * compare field value
     * @param target target resource
     * @param field
     * @return true or false
     */
    private boolean compareField(Object target,Field field){

        Object o1 = null;
        Object o2 =null;
        try {
            o1 = ReflectionHelper.getFieldValue(this, field);

        } catch (IllegalAccessException e) {
            logger.error("get_field_error={}", e);
        }
        try {
            o2 = ReflectionHelper.getFieldValue(target, field);
        } catch (IllegalAccessException e) {
            logger.error("get_field_error={}", e);
        }

        if (o1 != null ? !o1.equals(o2) : o2 != null) {
            logger.info("expected field:{} value is {} but actual it is {}",field.getName(),o1,o2);
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {

        int result=0;
        for (Field field : this.getClass().getDeclaredFields()) {
            Object o = null;
            try {
                o = ReflectionHelper.getFieldValue(this, field);
            } catch (IllegalAccessException e) {
                logger.error("get_field_error={}",e);
            }
            result = 31*result +(o!=null?o.hashCode():0);
        }
        return result;
    }

    @Override
    public String toString() {

        return this.getClass().getName()+":"+JSON.toJSON(this).toString();
    }
}
