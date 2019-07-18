package com.example.fitmvp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.UserInfo;

import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.contact_deleted;
import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.invite_accepted;
import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.invite_declined;
import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.invite_received;

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

    public static String getState(ContactNotifyEvent.Type type){
        switch (type) {
            //收到好友邀请
            case invite_received:
               return "请求加为好友";
            //对方接收了你的好友邀请
            case invite_accepted:
                return "对方已同意";
            //对方拒绝了你的好友邀请
            case invite_declined:
                return "对方已拒绝";
            //对方将你从好友中删除
            case contact_deleted:
                return "被对方删除";
            default:
                return "等待对方确认";
        }
    }
}
