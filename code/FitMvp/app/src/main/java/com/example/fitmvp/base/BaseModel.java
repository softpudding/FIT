package com.example.fitmvp.base;

import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.network.Http;
import com.example.fitmvp.network.HttpService;

public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }
}
