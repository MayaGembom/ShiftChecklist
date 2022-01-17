package com.MayaGembom.shiftchecklist.More;

import android.app.Application;

import com.MayaGembom.shiftchecklist.Objects.MyFirebase;


public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MyFirebase.init();
    }
}
