package com.springdemo.mvc.model;


import org.codehaus.jackson.map.annotate.JsonView;

import java.io.Serializable;

public class UserModel implements Serializable {
    public static interface OnlyIdView {}
    public static interface OnlyNameView {}
    public static interface AllView extends OnlyIdView, OnlyNameView {}

    @JsonView(OnlyIdView.class)
    private Long id;

    @JsonView(OnlyNameView.class)
    private String name;

    public UserModel() {
    }

    public UserModel(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
