package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_Owner;

public class Owner extends User implements Interface_Owner {

    public Owner() {
    }

    public Owner(String uid, String userName, String userLastName, String userPhoneNumber, String imageURL, String workerID) {
        super(uid, userName, userLastName, userPhoneNumber, imageURL, workerID);
    }
}
