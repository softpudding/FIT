package com.example.fitmvp.network;

import com.example.fitmvp.bean.LoginUserBean;
import com.example.fitmvp.bean.MyResponse;
import com.example.fitmvp.bean.PhotoType1Bean;
import com.example.fitmvp.bean.RegisterUserBean;
import com.example.fitmvp.bean.TypeOne;
import com.google.gson.JsonObject;

import java.sql.Timestamp;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

// 所有网络请求接口
public interface HttpService {
    // 登录
    @POST("user/login")
    @FormUrlEncoded
    Observable<MyResponse<LoginUserBean>> login(@Field("tel") String account, @Field("password") String password);

    // 注册
    @POST("user/register")
    @FormUrlEncoded
    Observable<MyResponse<RegisterUserBean>> register(@Field("tel") String tel, @Field("nickName") String nickName, @Field("password") String password);

    // 检查验证码
    @POST("/user/sendMessage")
    @FormUrlEncoded
    Observable<String> sendMessage(@Field("tel") String tel);
    
    // 修改密码
    @POST("user/changePassword")
    @FormUrlEncoded
    Observable<String> changePw(@Field("tel") String tel, @Field("password") String password);

    //图片传输
    @POST("classify/")
    @FormUrlEncoded
    Observable<PhotoType1Bean> photoSend(@Field("obj_type")Integer obj_type,@Field("img")String img);
}
