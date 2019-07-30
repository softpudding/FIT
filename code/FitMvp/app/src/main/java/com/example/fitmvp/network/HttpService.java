package com.example.fitmvp.network;

import com.alibaba.fastjson.JSONObject;
import com.example.fitmvp.bean.LoginUserBean;
import com.example.fitmvp.bean.MyResponse;
import com.example.fitmvp.bean.NutriBean;
import com.example.fitmvp.bean.PhotoType1Bean;
import com.example.fitmvp.bean.PhotoTypetBean;
import com.example.fitmvp.bean.PredictionBean;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.bean.RegisterUserBean;

import com.alibaba.fastjson.JSONArray;
import com.example.fitmvp.bean.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

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
    Observable<PhotoType1Bean<PredictionBean, NutriBean>> photoSend(@Field("tel")String tel, @Field("obj_type")Integer obj_type, @Field("img")String img);

    //多种食物
    @POST("classify/")
    @FormUrlEncoded
    Observable<PhotoTypetBean> multifood(@Field("tel")String tel, @Field("obj_type")Integer obj_type,
                                         @Field("img")String img, @Field("plate_type")Integer ptype);

    // 更新用户信息
    @POST("/user/changeUserInfo")
    Observable<Boolean> updateInfo(@Body UserInfoBean user);
    @POST("user/saveRecord")
   // @Multipart
    Observable<String> saveRecord(@Body JSONArray array);

    // 获取最新五条识别记录
    @POST("record/getFiveRecord")
    @FormUrlEncoded
    Observable<List<RecordBean>> getFiveRecord(@Field("tel") String tel);

    // 获取全部识别记录
    @POST("record/getAllRecord")
    @FormUrlEncoded
    Observable<List<RecordBean>> getAllRecord(@Field("tel") String tel);

    // 获取当天记录的能量总值
    @POST("record/sumCal")
    @FormUrlEncoded
    Observable<JSONObject> getSumCal(@Field("tel") String tel);
}
