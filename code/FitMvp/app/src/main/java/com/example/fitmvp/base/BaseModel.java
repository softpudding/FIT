package com.example.fitmvp.base;

import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.network.Http;
import com.example.fitmvp.network.HttpService;

public class BaseModel implements IModel {
    protected static HttpService httpService1;
    protected static HttpService httpService2;

    static{
        httpService1 = Http.getHttpService(1);
        httpService2 = Http.getHttpService(2);
    }
}
