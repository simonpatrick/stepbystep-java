package com.hedwig.stepbystep.javatutorial.beanutils;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PropertyHelperTest_mapped extends BeanUtilsBaseTest {

    @Test
    public void testGetMappedValue() throws Exception {
        System.out.println(PropertyHelper.getMappedValue(ic, "deps(dev1)"));
        Assert.assertNotNull(PropertyHelper.getMappedValue(ic, "deps(manager)"));
    }

    @Test
    public void testGetMappedValue1() throws Exception {
        System.out.println(PropertyHelper.getMappedValue(ic, "deps","dev1"));
        Assert.assertNotNull(PropertyHelper.getMappedValue(ic, "deps","manager"));
    }

    @Test
    public void testGetMappedValue2(){
        System.out.println(PropertyHelper.getMappedValue(ic, "user(manager)"));
        Assert.assertNotNull(PropertyHelper.getMappedValue(ic, "user", "dev1"));
    }

    @Test
    public void testGetValue() throws Exception {
        System.out.println(PropertyHelper.getValue(ic,"deps(dev1)"));
        System.out.println(PropertyHelper.getValue(manager,"subs[0].firstName"));
        System.out.println(PropertyHelper.getValue(manager,"firstName"));
    }
}