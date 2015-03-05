package com.hedwig.stepbystep.javatutorial.beanutils.model;

import com.google.common.base.MoreObjects;

/**
 * Created by patrick on 15/3/5.
 *
 * @version $Id$
 */


public class User {
    private String name;
    private int age;
    private String nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public static User getUser(){
        User user = new User();
        user.setNickName("nick1");
        user.setName("name1");
        user.setAge(23);
        return user;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("age", age)
                .add("nickName", nickName)
                .toString();
    }
}
