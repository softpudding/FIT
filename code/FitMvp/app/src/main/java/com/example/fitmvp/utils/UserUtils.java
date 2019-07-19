package com.example.fitmvp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.im.android.api.model.UserInfo;

public class UserUtils {
    public static String getGender(UserInfo userInfo){
        UserInfo.Gender gender = userInfo.getGender();
        if (gender != null) {
            if (gender.equals(UserInfo.Gender.male)) {
                return "男";
            } else if (gender.equals(UserInfo.Gender.female)) {
                return "女";
            } else {
                return "保密";
            }
        } else {
            return "保密";
        }
    }

    public static String getBirthday(UserInfo userInfo){
        long birthday = userInfo.getBirthday();
        Date date = new Date(birthday);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
