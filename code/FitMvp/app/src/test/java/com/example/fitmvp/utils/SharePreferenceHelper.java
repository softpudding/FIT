package com.example.fitmvp.utils;

import android.content.SharedPreferences;

public class SharePreferenceHelper {

    public static SharedPreferences newInstance() {
        return new ShadowSharedPreference();
    }
}
