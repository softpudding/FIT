package com.example.fitmvp.network;

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
    @POST(":30231/user/login")
    @FormUrlEncoded
    Observable<String> login(@Field("account") String account, @Field("password") String password);

    // 注册
    @POST(":30231/user/register")
    @FormUrlEncoded
    Observable<String> register(@Field("tel") String tel, @Field("nickName") String nickName, @Field("password") String password);

    // 修改密码
    @POST(":30231/user/changePassword")
    @FormUrlEncoded
    Observable<String> changePw(@Field("tel") String tel, @Field("password") String password);

    //图片传输
    @POST(":30232/classify")
    @FormUrlEncoded
    Call<String> photoSend(@Field("obj_type") Integer obj_type, @Field("pic") String pic);
}
