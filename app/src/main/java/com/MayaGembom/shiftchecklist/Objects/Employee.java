package com.MayaGembom.shiftchecklist.Objects;

import com.MayaGembom.shiftchecklist.Interfaces.Interface_Employee;

public class Employee extends User implements Interface_Employee {

    public Employee(){

    }

    public Employee(String id, String userLastName, String username, String userPhoneNumber, String imageURL) {
        super(id, userLastName, username, userPhoneNumber, imageURL);
    }



}
