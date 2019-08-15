package com.example.fitmvp.model;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.contract.WelcomeContract;
import com.example.fitmvp.utils.SpUtils;

public class WelcomeModel extends BaseModel implements WelcomeContract.Model {
    @Override
    public Boolean isLogin(){
        Boolean isLogin;
        // 默认值为false，即查找结果为null时返回false
        SpUtils spUtils = new SpUtils();
        isLogin = (Boolean)spUtils.get("isLogin",false);
        return isLogin;
    }
}
