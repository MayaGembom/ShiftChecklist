package com.MayaGembom.shiftchecklist.Objects;

public class User {

    private String name;
    private String imgUrl;
    private String uid;


    public User(String name, String imgUrl, String uid) {
        this.name = name;
        this.imgUrl = "default";
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }
}
