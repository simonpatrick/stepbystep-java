package com.hedwig.stepbystep.javatutorial.beanutils.model;

import com.beust.jcommander.internal.Maps;
import com.google.common.collect.Lists;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class EmployeeBuilder {
    private EmployeeBuilder(){}

    private static Random random = new Random();

    public static EmployeeBuilder getBuilder(){
        return new EmployeeBuilder();
    }

    public Employee build(String type){
        if(type.contains("manager")){
            return buildManagerEmployee();
        }

        return buildNoManagerEmployee();
    }

    private Address buildAddress(){
        Address a = new Address();
        a.setRoad("test road"+random.nextInt(1000));
        a.setName("location name"+random.nextInt(1000));
        return a;
    }

    private Employee buildManagerEmployee(){
        Employee e = new Employee();
        e.setAddress(buildAddress());
        e.setLastName("Last Name");
        e.setFirstName("First Name");
        e.setHireDate(new Date());
        e.setManager(true);
        e.setSubs(buildSubEmployees());
        return e;
    }

    private Employee buildNoManagerEmployee(){
        Employee e = new Employee();
        e.setAddress(buildAddress());
        e.setLastName("Last Name"+random.nextInt(1000));
        e.setFirstName("First Name"+random.nextInt(1000));
        e.setHireDate(new Date());
        e.setManager(false);
        e.setDeps(buildPeerEmployees());
        return e;
    }

    private List<Employee> buildSubEmployees(){
        List<Employee> list = Lists.newArrayList();
        int end = random.nextInt(3)+1;
        for (int i = 0; i <end ; i++) {
            list.add(buildNoManagerEmployee());
        }
        return list;
    }

    private Map<String,User> buildPeerEmployees(){
        Map<String,User> peers = Maps.newHashMap();
        peers.put("manager",User.getUser());
        peers.put("dev1",User.getUser());
        peers.put("dev2",User.getUser());
        peers.put("dev3",User.getUser());
        return peers;
    }
}
