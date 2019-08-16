package com.example.fitmvp.model;

import com.example.fitmvp.contract.WelcomeContract;
import com.example.fitmvp.utils.SpUtils;

public class WelcomeModel implements WelcomeContract.Model {
    @Override
    public Boolean isLogin(SpUtils spUtils){
        if(spUtils==null){
            spUtils = new SpUtils();
        }
        Boolean isLogin;
        // 默认值为false，即查找结果为null时返回false
        isLogin = (Boolean)spUtils.get("isLogin",false);
        return isLogin;
    }
}
