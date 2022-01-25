package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_ShiftManager;

public class ShiftManager extends User implements Interface_ShiftManager {

    public ShiftManager() {
    }

    public ShiftManager(String id, String userLastName, String username, String userPhoneNumber, String imageURL) {
        super(id, userLastName, username, userPhoneNumber, imageURL);
    }
}
