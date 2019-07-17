package com.example.fitmvp.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.POST;

public interface PhotoService {
    @POST("classify/")
    Call<String> photoSend(@Field("obj_type")Integer type, @Field("pic") String pic);
}
