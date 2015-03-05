package com.hedwig.stepbystep.javatutorial.beanutils.model;

import com.beust.jcommander.internal.Maps;
import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Employee {

    private String firstName;
    private String lastName;
    private Date hireDate;
    private boolean isManager;
    private Address address;
    private List<Employee> subs = new ArrayList<Employee>();
    private Map<String,User> deps = Maps.newHashMap();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean isManager) {
        this.isManager = isManager;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Employee> getSubs() {
        return subs;
    }

    public void setSubs(List<Employee> subs) {
        this.subs = subs;
    }


    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("hireDate", hireDate)
                .add("isManager", isManager)
                .add("address", address)
                .add("subs", subs)
                .add("deps", deps)
                .toString();
    }

    public Map<String, User> getDeps() {
        return deps;
    }

    public void setDeps(Map<String, User> deps) {
        this.deps = deps;
    }

    public User getUser(String type){

        return deps.get(type);
    }
}