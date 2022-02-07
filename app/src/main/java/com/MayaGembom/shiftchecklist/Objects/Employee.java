package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_Employee;

public class Employee extends User implements Interface_Employee {

    public Employee(){

    }

    public Employee(String uid, String userName, String userLastName, String userPhoneNumber, String imageURL, String workerID) {
        super(uid, userName, userLastName, userPhoneNumber, imageURL, workerID);
    }
}
