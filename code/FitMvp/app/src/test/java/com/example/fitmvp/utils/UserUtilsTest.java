package com.example.fitmvp.utils;

import com.example.fitmvp.bean.LoginUserBean;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.im.android.api.model.UserInfo;

import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.contact_deleted;
import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.contact_updated_by_dev_api;
import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.invite_accepted;
import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.invite_declined;
import static cn.jpush.im.android.api.event.ContactNotifyEvent.Type.invite_received;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserUtilsTest {

    @Test
    public void getGender() {
        String result;
        UserInfo user = mock(UserInfo.class);
        when(user.getGender()).thenReturn(UserInfo.Gender.male);
        result = UserUtils.getGender(user);
        assertEquals("男",result);
        when(user.getGender()).thenReturn(UserInfo.Gender.female);
        result = UserUtils.getGender(user);
        assertEquals("女",result);
        when(user.getGender()).thenReturn(UserInfo.Gender.unknown);
        result = UserUtils.getGender(user);
        assertEquals("保密",result);
        when(user.getGender()).thenReturn(null);
        result = UserUtils.getGender(user);
        assertEquals("保密",result);
    }

    @Test
    public void getGender1() {
        LoginUserBean user = new LoginUserBean();
        String result;
        // 保密
        result = UserUtils.getGender(user);
        assertEquals("保密",result);
        user.setGender(0);
        result = UserUtils.getGender(user);
        assertEquals("保密",result);
        // 男
        user.setGender(1);
        result = UserUtils.getGender(user);
        assertEquals("男",result);
        // 女
        user.setGender(2);
        result = UserUtils.getGender(user);
        assertEquals("女",result);
        // 未知
        user.setGender(11);
        result = UserUtils.getGender(user);
        assertEquals("未知",result);
    }

    @Test
    public void getBirthday() {
        UserInfo userInfo = mock(UserInfo.class);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try{
            date = formatter.parse("2019-08-01");
        }
        catch (ParseException e){
            System.out.println(e.getMessage());
        }
        when(userInfo.getBirthday()).thenReturn(date.getTime());
        String result = UserUtils.getBirthday(userInfo);
        assertEquals("2019-08-01",result);
    }

    @Test
    public void getState() {
        String result;
        result = UserUtils.getState(invite_received);
        assertEquals("请求加为好友",result);

        result = UserUtils.getState(invite_accepted);
        assertEquals("对方已同意",result);

        result = UserUtils.getState(invite_declined);
        assertEquals("对方已拒绝",result);

        result = UserUtils.getState(contact_deleted);
        assertEquals("被对方删除",result);

        result = UserUtils.getState(contact_updated_by_dev_api);
        assertEquals("等待对方确认",result);

        result = UserUtils.getState(null);
        assertEquals("等待对方确认",result);
    }
}