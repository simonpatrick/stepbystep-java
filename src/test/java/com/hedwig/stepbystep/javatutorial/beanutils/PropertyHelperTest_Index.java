package com.hedwig.stepbystep.javatutorial.beanutils;

import com.hedwig.stepbystep.javatutorial.beanutils.model.Employee;
import com.hedwig.stepbystep.javatutorial.beanutils.model.EmployeeBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PropertyHelperTest_Index {
    private Employee manager;
    private Employee ic;

    @BeforeSuite
    public void init(){
        manager = EmployeeBuilder.getBuilder().build("manager");
        System.out.println(manager);
        ic = EmployeeBuilder.getBuilder().build("ic");
        System.out.println(ic);
    }
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