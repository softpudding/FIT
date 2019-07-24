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
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.utils.UserUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class LoginModel extends BaseModel implements LoginContract.Model {
    private Boolean isLogin = false;
    private String token;

    @Override
    public Boolean login(@NonNull String account, @NonNull String password, @NonNull final InfoHint
            infoHint) {
        if (infoHint == null)
            throw new RuntimeException("InfoHint不能为空");

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
                                case "100":
                                    infoHint.successInfo(response.getUser().getTel());
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
    public void saveUser(String username, final InfoHint infoHint){
        // 登录状态设为true
        SpUtils.put("isLogin",true);
        // 保存token
        SpUtils.put("token",token);
        // LogUtils.d("token",(String)SpUtils.get("token",""));
        // 保存账号、昵称、头像、生日、身高、体重、性别信息

        JMessageClient.getUserInfo(username, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {
                if(i==0){
                    LogUtils.e("save_info",userInfo.getUserName()+" "+userInfo.getNickname());
                    String phone = userInfo.getUserName();
                    String appKey = userInfo.getAppKey();
                    SpUtils.put("phone",phone);
                    SpUtils.put("appKey",appKey);
                    SpUtils.put("nickname",userInfo.getNickname());
                    UserInfo.Gender gender = userInfo.getGender();
                    // 性别
                    SpUtils.put("gender", UserUtils.getGender(userInfo));
                    // 生日
                    SpUtils.put("birthday",UserUtils.getBirthday(userInfo));
                    // 保存身高、体重

                    // 更新数据库中用户数据
                    UserEntry userEntry = UserEntry.getUser(phone,appKey);
                    if(userEntry==null){
                        LogUtils.e("save_user",phone);
                        UserEntry newUser = new UserEntry(phone,appKey);
                        newUser.save();
                    }
                    infoHint.successInfo("");
                }
                else{
                    LogUtils.e("find_user",s);
            }
            }
        });
    }

    @Override
    public Boolean logout() {
        JMessageClient.logout();
        SpUtils.clear();
        return true;
    }
}
