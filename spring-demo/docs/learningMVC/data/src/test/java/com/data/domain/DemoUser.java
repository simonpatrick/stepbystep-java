
package com.springdemo.learningMVC.data.src.test.java.com.data.domain;


import com.common.base.RandomStrings;
import com.data.qdomain.AbstractVersionedEntity;

public class DemoUser extends AbstractVersionedEntity<Integer, DemoUser> {

    private static final long serialVersionUID = -7176922098996920852L;

    public static DemoUser newUser(String username) {
        DemoUser user = new DemoUser();
        user.setUsername(username);
        user.setPassword("111111");
        return user;
    }

    public static DemoUser newRandomUser() {
        return newUser(RandomStrings.randomAlphabetic(10));
    }

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Integer getId() {
        return getUserId();
    }

    @Override
    public void setId(Integer id) {
        setUserId(id);
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "DemoUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
