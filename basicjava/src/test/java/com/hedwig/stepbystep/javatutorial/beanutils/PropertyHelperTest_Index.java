package com.hedwig.stepbystep.javatutorial.beanutils;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;


public class PropertyHelperTest_Index extends BeanUtilsBaseTest {

    @Test
    public void testGetIndexValue() throws Exception {
        System.out.println(PropertyHelper.getIndexValue(manager, "subs[0]"));
        Assert.assertNotNull(PropertyHelper.getIndexValue(manager, "subs[0]"));
    }

    @Test
    public void testGetIndexValue1() throws Exception {
        System.out.println(PropertyHelper.getIndexValue(manager, "subs",0));
        Assert.assertNotNull(PropertyHelper.getIndexValue(manager, "subs",0));
    }
}