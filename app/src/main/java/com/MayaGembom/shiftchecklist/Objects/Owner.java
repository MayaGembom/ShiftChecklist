package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_Owner;

public class Owner extends User implements Interface_Owner {

    public Owner() {
    }

    public Owner(String id, String userLastName, String username, String userPhoneNumber, String imageURL) {
        super(id, userLastName, username, userPhoneNumber, imageURL);
    }
}
