package com.hedwig.stepbystep.javatutorial.beanutils;

import com.hedwig.stepbystep.javatutorial.beanutils.model.Employee;
import com.hedwig.stepbystep.javatutorial.beanutils.model.EmployeeBuilder;
import org.testng.annotations.BeforeSuite;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class BeanUtilsBaseTest {

    protected Employee manager;
    protected Employee ic;

    @BeforeSuite
    public void init(){
        manager = EmployeeBuilder.getBuilder().build("manager");
        System.out.println(manager);
        ic = EmployeeBuilder.getBuilder().build("ic");
        System.out.println(ic);
    }
}
