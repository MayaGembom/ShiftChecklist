package com.MayaGembom.shiftchecklist.Objects;

public abstract class User {

    private String userLastName;
    private String userName;
    private String imageURL;
    private String uid;
    private String userPhoneNumber;
    private int workerID;


    public User() {

    }

    public User(String id, String userLastName, String username,String userPhoneNumber, String imageURL) {
        this.uid = id;
        this.userLastName = userLastName;
        this.userName = username;
        this.userPhoneNumber = userPhoneNumber;
        this.imageURL = imageURL;
    }


    public int getWorkerID() {
        return workerID;
    }

    public User setWorkerID(int workerID) {
        this.workerID = workerID;
        return this;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public User setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public User setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }
}
