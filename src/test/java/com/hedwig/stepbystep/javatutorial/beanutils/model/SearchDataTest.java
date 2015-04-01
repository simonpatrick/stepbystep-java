package com.hedwig.stepbystep.javatutorial.beanutils.model;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.hamcrest.beans.PropertyUtil;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.testng.Assert.*;

public class SearchDataTest {
  @Test
  public void setListValue() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
      SearchData data = new SearchData();
      SearchData data1 = new SearchData();
      List<String> area = Lists.newArrayList("123","2345");

      BeanUtils.setProperty(data,"areaFilter",area);
      System.out.println(data);
      ((List)PropertyUtils.getProperty(data1, "areaFilter")).add(null);
      BeanUtils.setProperty(data1,"areaFilter[0]","test");
      System.out.println(data1);
  }
}