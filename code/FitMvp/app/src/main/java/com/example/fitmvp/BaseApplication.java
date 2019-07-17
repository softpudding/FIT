package com.example.fitmvp;

import android.app.Application;
import android.content.Context;

import cn.jpush.im.android.api.JMessageClient;

public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        JMessageClient.init(this);
        JMessageClient.setDebugMode(true);
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getmContext() {
        return mContext;
    }
}
