package com.MayaGembom.shiftchecklist.Objects;

public class User {
    private String imageURL;
    private String userFirstName;
    private String userLastName;
    private String workerID;

    public User() {

    }

    public User(String imageURL, String userFirstName, String userLastName, String workerID) {
        this.imageURL = imageURL;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.workerID = workerID;
    }


    public String getImageURL() {
        return imageURL;
    }

    public User setImageURL(String imageURL) {
        this.imageURL = imageURL;
        return this;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public User setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
        return this;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public User setUserLastName(String userLastName) {
        this.userLastName = userLastName;
        return this;
    }

    public String getWorkerID() {
        return workerID;
    }

    public User setWorkerID(String workerID) {
        this.workerID = workerID;
        return this;
    }
}
