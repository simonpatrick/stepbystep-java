package com.hedwig.stepbystep.javatutorial.beanutils;

import com.alibaba.fastjson.JSON;
import com.hedwig.stepbystep.javatutorial.beanutils.model.Address;
import com.hedwig.stepbystep.javatutorial.beanutils.model.Employee;
import com.hedwig.stepbystep.javatutorial.helpers.ReflectionHelper;
import com.hedwig.stepbystep.javatutorial.servicetest.base.RequestData;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class BeanHelper {
    public BeanHelper(){}

    public static <T> String getSimpleValue(T instance,String name){
        try {
            return BeanUtils.getSimpleProperty(instance, StringUtils.uncapitalize(name));
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e) {
        } catch (NoSuchMethodException e) {
        }
        return null;
    }

    public void testIndex(int t){
        System.out.println(t);
    }
    public static void settingGenericType() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        RequestData data = new RequestData();
        Address address = new Address();
        address.setName("test");
        address.setRoad("testRoad");
        data.setBody(address);
        BeanUtils.setProperty(data, "pathParameters(for)", "test"); //set to Map
        BeanUtils.setProperty(data,"pathParameters(for2)","test2");
        Field[] fields = RequestData.class.getDeclaredFields();
        for (Field field : fields) {
            if(field.getName().equalsIgnoreCase("testList")){
                System.out.println(field.getType());
                System.out.println(ReflectionHelper.getGenericParameterClass(field));
            }
        }
        System.out.println(data);
    }



    public static void SetListValue() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Employee ee = new Employee();
        ee.getSubs().add(new Employee());
        BeanUtils.setProperty(ee,"subs[0].firstName","firstName");
        BeanUtils.setProperty(ee, "subs[0].lastName", "lastName");
        System.out.println(PropertyUtils.getProperty(ee, "subs"));
        System.out.println(ee);
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        settingGenericType();
        SetListValue();

        BeanHelper h = new BeanHelper();
        Method m = ReflectionHelper.getAccessibleMethod(h,"testIndex",int.class);
        m.invoke(h,Integer.valueOf("1234"));
        System.out.println(m);

        RequestData<Address> data = new RequestData<>();
        data.setBody(new Address());

        RequestData data1 = new RequestData();
        BeanUtils.setProperty(data1,"body","testboday");
        System.out.println(data1);
        
        

    }


}
