package com.hedwig.stepbystep.javatutorial.beanutils.model;

import com.google.common.base.MoreObjects;

/**
 * Created by patrick on 15/3/13.
 *
 * @version $Id$
 */


public class ClassB {

    @Alias(values = {"username","user_name"})
    private String userName;
    @Alias(values = {"username","user_name"})
    private String orderId;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userName", userName)
                .add("orderId", orderId)
                .add("password", password)
                .toString();
    }
}
