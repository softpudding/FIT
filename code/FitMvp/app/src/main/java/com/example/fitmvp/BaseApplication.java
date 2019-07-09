package com.example.fitmvp;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getmContext() {
        return mContext;
    }
}
