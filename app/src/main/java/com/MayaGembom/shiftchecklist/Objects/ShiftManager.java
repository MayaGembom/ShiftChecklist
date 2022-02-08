package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_ShiftManager;

public class ShiftManager extends User implements Interface_ShiftManager {

    private String whichDepartment;

    public ShiftManager() {
    }

    public ShiftManager(String imageURL, String userFirstName, String userLastName, String workerID, String whichDepartment) {
        super(imageURL, userFirstName, userLastName, workerID);
        this.whichDepartment = whichDepartment;
    }


    public String getWhichDepartment() {
        return whichDepartment;
    }

    public ShiftManager setWhichDepartment(String whichDepartment) {
        this.whichDepartment = whichDepartment;
        return this;
    }
}
