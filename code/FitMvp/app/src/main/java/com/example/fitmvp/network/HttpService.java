package com.example.fitmvp.network;

import com.example.fitmvp.bean.LoginUserBean;
import com.example.fitmvp.bean.MyResponse;
import com.example.fitmvp.bean.RegisterUserBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

// 所有网络请求接口
public interface HttpService {
    // 登录
    @POST("/user/login")
    @FormUrlEncoded
    Observable<MyResponse<LoginUserBean>> login(@Field("tel") String account, @Field("password") String password);

    // 注册
    @POST("/user/register")
    @FormUrlEncoded
    Observable<MyResponse<RegisterUserBean>> register(@Field("tel") String tel, @Field("nickName") String nickName, @Field("password") String password);

    // 检查验证码
    @POST("/user/sendMessage")
    @FormUrlEncoded
    Observable<String> sendMessage(@Field("tel") String tel);
    
    // 修改密码
    @POST("/user/changePassword")
    @FormUrlEncoded
    Observable<String> changePw(@Field("tel") String tel,@Field("password") String password);
}
