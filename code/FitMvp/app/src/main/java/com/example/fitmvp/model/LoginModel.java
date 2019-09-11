package com.example.fitmvp.model;

import androidx.annotation.NonNull;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.LoginUserBean;
import com.example.fitmvp.bean.MyResponse;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.utils.UserUtils;

import cn.jpush.im.android.api.JMessageClient;

public class LoginModel extends BaseModel implements LoginContract.Model {
    private Boolean isLogin = false;
    private String token;

    // 向后端发送登录请求，处理返回结果
    @Override
    public Boolean login(@NonNull String account, @NonNull String password, @NonNull final InfoHint
            infoHint) {
        // 发送登录请求
        httpService1.login(account, password)
                .compose(new ThreadTransformer<MyResponse<LoginUserBean>>())
                .subscribe(new CommonObserver<MyResponse<LoginUserBean>>() {
                    // 请求成功返回后检查登录结果
                    @Override
                    public void onNext(MyResponse<LoginUserBean> response) {
                        if(response!=null){
                            if(response.getToken()==null){
                                LogUtils.e("error","null");
                                return;
                            }
                            switch(response.getResult()){
                                // 登录成功
                                case "100":
                                    infoHint.successInfo(response.getUser());
                                    token = response.getToken();
                                    isLogin = true;
                                    break;
                                case "101":
                                    infoHint.failInfo("账号不存在");
                                    isLogin = false;
                                    break;
                                case "102":
                                    infoHint.failInfo("密码不正确");
                                    isLogin = false;
                                    break;
                                case "103":
                                    infoHint.failInfo("账号被禁用");
                                    isLogin = false;
                                    break;
                                default:
                                    infoHint.failInfo(response.getResult());
                                    isLogin = false;
                                    break;
                            }
                        }

                    }

                    @Override
                    public void onError(ApiException e){
                        infoHint.errorInfo(e.message);
                        System.err.println("onError: "+ e.getMessage());
                    }
                });
        return isLogin;
    }

    // 后端和JMessage都登录成功后，保存用户信息
    public void saveUser(final LoginUserBean user, final InfoHint infoHint){
        SpUtils spUtils = new SpUtils();
        // 登录状态设为true
        spUtils.put("isLogin",true);
        // 保存token
        spUtils.put("token",token);
        // 保存账号、昵称、头像、生日、身高、体重、性别信息
        String phone = user.getTel();
        spUtils.put("phone",phone);
        spUtils.put("nickname",user.getNickName());
        // 性别
        spUtils.put("gender", UserUtils.getGender(user));
        // 生日
        spUtils.put("birthday",user.getBirthday());
        // 保存身高、体重
        spUtils.put("height",String.valueOf(user.getHeight()));
        spUtils.put("weight",String.valueOf(user.getWeight()));

        String appKey = BaseApplication.getAppKey();
         // 更新数据库中用户数据
        UserEntry userEntry = UserEntry.getUser(phone,appKey);
        if(userEntry==null){
            LogUtils.e("save_user",phone);
            UserEntry newUser = new UserEntry(phone,appKey);
            newUser.save();
        }
        else{
            LogUtils.e("find user",phone);
        }
        UserEntry userEntry1 = BaseApplication.getUserEntry();
        if(userEntry1 == null){
            LogUtils.e("user entry is null","   ");

        }
        else{
            LogUtils.e("user entry is not null", userEntry1.username);
        }
        infoHint.loginSuccess();
    }

    // 登出
    @Override
    public Boolean logout() {
        JMessageClient.logout();
        SpUtils spUtils = new SpUtils();
        spUtils.clear();
        return true;
    }
}
