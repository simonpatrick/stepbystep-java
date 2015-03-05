package com.hedwig.stepbystep.javatutorial.beanutils;

import com.hedwig.stepbystep.javatutorial.beanutils.model.Address;
import com.hedwig.stepbystep.javatutorial.beanutils.model.Employee;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

public class PropertyHelperTest {

    private Employee e = new Employee();
    private Address a = new Address();

    @BeforeSuite
    public void init(){
        a.setName("Address-1");
        a.setRoad("Road-1");
        e.setAddress(a);
        e.setFirstName("FirstName");
        e.setLastName("LastName");
        e.setHireDate(new Date());
    }

    @Test
    public void testGetSimpleValue() throws Exception {
        System.out.println(PropertyHelper.getSimpleValue(e,"firstName"));
        Assert.assertEquals(PropertyHelper.getSimpleValue(e, "firstName"), "FirstName");
    }

    @Test
    public void testGetSimpleValue_Capital() throws Exception {
        System.out.println(PropertyHelper.getSimpleValue(e,"firstName"));
        Assert.assertEquals(PropertyHelper.getSimpleValue(e,"FirstName"),"FirstName");
    }

    @Test
    public void testGetSimpleValue_Null() throws Exception {
        System.out.println(PropertyHelper.getSimpleValue(e,"firstName"));
        Assert.assertEquals(PropertyHelper.getSimpleValue(e,"FirstName1"),null);
    }

    @Test
    public void testGetSimpleValue_Objects() throws Exception {
        System.out.println(PropertyHelper.getSimpleValue(e,"address"));
        Assert.assertEquals(PropertyHelper.getSimpleValue(e,"address"),a);
    }


}