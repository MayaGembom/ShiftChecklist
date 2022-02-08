package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_Owner;

public class Owner extends User implements Interface_Owner {

    public Owner() {
    }

    public Owner(String imageURL, String userFirstName, String userLastName, String workerID) {
        super(imageURL, userFirstName, userLastName, workerID);
    }
}
