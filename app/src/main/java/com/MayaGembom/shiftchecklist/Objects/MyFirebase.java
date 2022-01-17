package com.MayaGembom.shiftchecklist.Objects;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class MyFirebase {

    private static MyFirebase instance;
    private FirebaseAuth auth;
    private FirebaseDatabase fdb;
    private FirebaseStorage fst;

    private MyFirebase() {
        fdb = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        fst = FirebaseStorage.getInstance();
    }

    public static void init() {
        if (instance == null) {
            instance = new MyFirebase();
        }
    }

    public static MyFirebase getInstance() {
        return instance;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public FirebaseDatabase getFdb() {
        return fdb;
    }

    public FirebaseUser getUser() {
        return auth.getCurrentUser();
    }

    public FirebaseStorage getFst() {
        return fst;
    }




}
