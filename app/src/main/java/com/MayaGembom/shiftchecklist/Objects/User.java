package com.MayaGembom.shiftchecklist.Objects;

public class User {
    private String uid;
    private String userName;
    private String userLastName;
    private String userPhoneNumber;
    private String imageURL;
    private String workerID;


    public User() {

    }

    public User(String uid, String userName, String userLastName, String userPhoneNumber, String imageURL, String workerID) {
        this.uid = uid;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userPhoneNumber = userPhoneNumber;
        this.imageURL = imageURL;
        this.workerID = workerID;
    }

    public String getWorkerID() {
        return workerID;
    }

    public User setWorkerID(String workerID) {
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
