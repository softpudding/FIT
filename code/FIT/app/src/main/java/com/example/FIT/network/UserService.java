package com.example.FIT.network;

import com.example.FIT.User;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UserService {
    //@Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/usr/login")
    @FormUrlEncoded
    Observable<String> login(@Field("account") String account,@Field("password") String password);
}
