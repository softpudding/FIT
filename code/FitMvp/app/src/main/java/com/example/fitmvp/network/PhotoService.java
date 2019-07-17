package com.example.fitmvp.network;

import com.example.fitmvp.bean.TypeOne;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PhotoService {
    @POST("classify/")
    Call<String> photoSend(@Body TypeOne one);
}
