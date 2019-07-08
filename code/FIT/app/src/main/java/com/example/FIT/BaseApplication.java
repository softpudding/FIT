package com.example.FIT;

import android.app.Application;

import com.example.FIT.network.NetWorkManager;

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
       // NetWorkManager.getInstance().init();
    }
}
