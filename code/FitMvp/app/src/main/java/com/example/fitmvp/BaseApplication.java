package com.example.fitmvp;

import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.example.fitmvp.database.UserEntry;

import cn.jpush.im.android.api.JMessageClient;

public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        // 初始化数据库
        // 如果 BaseApplication 继承 com.activeandroid.app.Application，则不用初始化
        ActiveAndroid.initialize(this);
        // 初始化JMessage
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

    @Override
    public void onTerminate(){
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static UserEntry getUserEntry() {
        return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
    }
}
