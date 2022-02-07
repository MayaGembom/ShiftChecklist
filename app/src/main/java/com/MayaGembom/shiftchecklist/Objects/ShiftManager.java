package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_ShiftManager;

public class ShiftManager extends User implements Interface_ShiftManager {

    public ShiftManager() {
    }

    public ShiftManager(String uid, String userName, String userLastName, String userPhoneNumber, String imageURL, String workerID) {
        super(uid, userName, userLastName, userPhoneNumber, imageURL, workerID);
    }
}
