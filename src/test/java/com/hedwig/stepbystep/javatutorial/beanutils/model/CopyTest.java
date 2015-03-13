package com.hedwig.stepbystep.javatutorial.beanutils.model;

import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */


public class CopyTest {

    private ClassA a = new ClassA();
    private ClassB b;

    @BeforeTest
    public void init(){
        a.setOrder_id("test");
        a.setPassword("password");
        a.setUser_name("username");
    }
    @Test
    public void testCopy() throws IllegalAccessException, InstantiationException {
        b= ClassB.class.newInstance();
        Field[] fields = ClassB.class.getDeclaredFields();
        for (Field field : fields) {

            Alias alias = field.getDeclaredAnnotation(Alias.class);
            if(alias ==null){
                field.setAccessible(true);
                field.set(b,getValueFromA(a, field.getName()));
            }else{
                field.setAccessible(true);
                field.set(b,getValueFromA(a,field.getName(),alias.values()));
            }

        }

        System.out.println(b);

    }

    private String convertToCamelString(String input){
        String[] temp = input.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <temp.length ; i++) {
            if(i ==0) {
                sb.append(temp[i]);
            }else{
                sb.append(StringUtils.capitalize(temp[i]));
            }
        }

        return sb.toString();
    }

    private <T> String getValueFromA(T a,String fieldName, String ... aliasNames) throws IllegalAccessException {
        Field[] fields = a.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if(field.getName().equalsIgnoreCase(fieldName)) return field.get(a).toString();
            for (String s : aliasNames) {
                if(s.equalsIgnoreCase(field.getName())){
                    return field.get(a).toString();
                }
            }
        }

        return null;
    }


}
