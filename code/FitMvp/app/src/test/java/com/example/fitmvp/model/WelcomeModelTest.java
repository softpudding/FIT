package com.example.fitmvp.model;

import com.example.fitmvp.utils.SharePreferenceHelper;
import com.example.fitmvp.utils.SpUtils;

import org.junit.Test;

import static org.junit.Assert.*;

public class WelcomeModelTest {
    @Test
    public void isLoginTest(){
        SpUtils spUtils = new SpUtils(SharePreferenceHelper.newInstance());
        WelcomeModel model = new WelcomeModel();
        assertFalse(model.isLogin(spUtils));
        spUtils.put("isLogin",true);
        assertTrue(model.isLogin(spUtils));
    }

}