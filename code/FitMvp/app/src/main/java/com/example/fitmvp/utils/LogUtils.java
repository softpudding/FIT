package com.example.fitmvp.utils;

import android.util.Log;
import com.example.fitmvp.BuildConfig;

public class LogUtils {

    public static final boolean isDebug = BuildConfig.DEBUG;

    // 输出log信息
    public static void d(String tag, String msg) {
        if (isDebug) {
            Log.d("fit_" + tag, msg);
        }
    }

    // 输出error信息
    public static void e(String tag, String msg) {
        if (isDebug) {
            Log.e("fit_" + tag, msg);
        }
    }

    // 输出error信息
    public static void e(Class cls, String msg) {
        if (isDebug) {
            Log.e("fit_" + cls.getSimpleName(), msg);
        }
    }
}
