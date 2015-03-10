package com.hedwig.stepbystep.javatutorial.webtest.pageobjects.pageresource;

import com.hedwig.stepbystep.javatutorial.BaseWebTest;
import com.hedwig.stepbystep.javatutorial.webtest.annotation.PageObject;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class BaiduHomePageResourceTest extends BaseWebTest{

    @Test
    public void test(){

    }

    private void  initElement(Class<?> clazz) throws IllegalAccessException, InstantiationException {

        if(clazz.getAnnotationsByType(PageObject.class).length >0){

        }
    }

}