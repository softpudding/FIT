package com.example.fitmvp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ListView;

import com.activeandroid.ActiveAndroid;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.utils.LogUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Message;

public class BaseApplication extends Application {
    public static final String CONV_TITLE = "conv_title";

    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final String TARGET_ID = "targetId";
    public static final String TARGET_APP_KEY = "targetAppKey";

    public static List<Message> ids = new ArrayList<>();

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        // 初始化数据库
        // 如果 BaseApplication 继承 com.activeandroid.app.Application，则不用初始化
        ActiveAndroid.initialize(this);
        // 初始化JMessage
        JMessageClient.init(this);
        JMessageClient.setDebugMode(true);
        //注册Notification点击的接收器
        new NotificationClickEventReceiver(getApplicationContext());
        // 全局监控activity
        registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getmContext() {
        return context;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static UserEntry getUserEntry() {
        return UserEntry.getUser(JMessageClient.getMyInfo().getUserName(), JMessageClient.getMyInfo().getAppKey());
    }

    public static String getAppKey(){
        return JMessageClient.getMyInfo().getAppKey();
    }

    // 全局监控
    private ActivityLifecycleCallbacks lifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            fixViewMutiClickInShortTime(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

    //防止短时间内多次点击，弹出多个activity 或者 dialog ，等操作
    private void fixViewMutiClickInShortTime(final Activity activity) {
        activity.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                proxyOnclick(activity.getWindow().getDecorView(),5);
            }
        });
    }

    private void proxyOnclick(View view, int recycledContainerDeep) {
        if (view.getVisibility() == View.VISIBLE) {
            boolean forceHook = recycledContainerDeep == 1;
            if (view instanceof ViewGroup) {
                boolean existAncestorRecycle = recycledContainerDeep > 0;
                ViewGroup p = (ViewGroup) view;
                if (!(p instanceof AbsListView || p instanceof ListView) || existAncestorRecycle) {
                    getClickListenerForView(view);
                    if (existAncestorRecycle) {
                        recycledContainerDeep++;
                    }
                } else {
                    recycledContainerDeep = 1;
                }
                int childCount = p.getChildCount();
                for (int i = 0; i < childCount; i++) {
                    View child = p.getChildAt(i);
                    proxyOnclick(child, recycledContainerDeep);
                }
            }
            else if(view instanceof Menu) {
                LogUtils.e("menu",String.valueOf(view.getId()));
            }
            else {
                getClickListenerForView(view);
            }
        }
    }

    /**
     * 通过反射  查找到view 的clickListener
     * @param view
     */
    public static void getClickListenerForView(View view) {
        try {
            Class viewClazz = Class.forName("android.view.View");
            //事件监听器都是这个实例保存的
            Method listenerInfoMethod = viewClazz.getDeclaredMethod("getListenerInfo");
            if (!listenerInfoMethod.isAccessible()) {
                listenerInfoMethod.setAccessible(true);
            }
            Object listenerInfoObj = listenerInfoMethod.invoke(view);

            Class listenerInfoClazz = Class.forName("android.view.View$ListenerInfo");

            Field onClickListenerField = listenerInfoClazz.getDeclaredField("mOnClickListener");

            if (null != onClickListenerField) {
                if (!onClickListenerField.isAccessible()) {
                    onClickListenerField.setAccessible(true);
                }
                View.OnClickListener mOnClickListener = (View.OnClickListener) onClickListenerField.get(listenerInfoObj);
                if (!(mOnClickListener instanceof ProxyOnclickListener) && mOnClickListener!=null) {
                    //自定义代理事件监听器
                    View.OnClickListener onClickListenerProxy = new ProxyOnclickListener(mOnClickListener);
                    //更换
                    onClickListenerField.set(listenerInfoObj, onClickListenerProxy);
                }else{
                    //Log.e("OnClickListenerProxy", "setted proxy listener ");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //自定义的代理事件监听器
    static class ProxyOnclickListener implements View.OnClickListener {
        private View.OnClickListener onclick;

        // 最短的连续点击时间 1000ms
        private int MIN_CLICK_DELAY_TIME = 2000;

        private long lastClickTime = 0;

        public ProxyOnclickListener(View.OnClickListener onclick) {
            this.onclick = onclick;
        }

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.type_whole && onclick!=null){
                onclick.onClick(v);
                return;
            }
            if(v.getId()==R.id.friendmenu){
//                onclick.onClick(v);
                LogUtils.e("press","friend menu");
                return;
            }
            //点击时间控制
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
                lastClickTime = currentTime;
                //Log.e("OnClickListenerProxy", "OnClickListenerProxy"+this);
                if (onclick != null) onclick.onClick(v);
                else{
                    LogUtils.e("onClick","onclick is null "+v.getId());
                }
            }
        }
    }
}
