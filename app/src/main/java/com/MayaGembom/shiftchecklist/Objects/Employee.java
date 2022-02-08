package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_Employee;

public class Employee extends User implements Interface_Employee {

    private String whichDepartment;

    public Employee(){

    }

    public Employee(String imageURL, String userFirstName, String userLastName, String workerID, String whichDepartment) {
        super(imageURL, userFirstName, userLastName, workerID);
        this.whichDepartment = whichDepartment;
    }

    public String getWhichDepartment() {
        return whichDepartment;
    }

    public Employee setWhichDepartment(String whichDepartment) {
        this.whichDepartment = whichDepartment;
        return this;
    }
}
