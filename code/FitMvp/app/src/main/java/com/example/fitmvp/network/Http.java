package com.example.fitmvp.network;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.utils.NetworkUtil;
import com.example.fitmvp.utils.SpUtils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Http {
    private static Http mInstance;
    private static volatile HttpService httpService1;
    private static volatile HttpService httpService2;
    private  static volatile Retrofit retrofit1;
    private  static volatile Retrofit retrofit2;

    // 单例
    public static Http getInstance() {
        if (mInstance == null) {
            synchronized (Http.class) {
                if (mInstance == null) {
                    mInstance = new Http();
                }
            }
        }
        return mInstance;
    }

    // retrofit的底层利用反射的方式, 获取所有的api接口的类
    public static HttpService getHttpService(Integer integer) {
        switch (integer){
            case 1:
                if(httpService1 == null){
                    httpService1 = getRetrofit(1).create(HttpService.class);
                }
                return httpService1;
            case 2:
                if(httpService2 == null){
                    httpService2 = getRetrofit(2).create(HttpService.class);
                }
                return  httpService2;
            default:
                if(httpService1 == null){
                    httpService1 = getRetrofit(1).create(HttpService.class);
                }
                return httpService1;
        }
    }

    private static Retrofit getRetrofit(Integer flag) {
        if (retrofit1 == null || retrofit2 == null) {
            synchronized (Http.class) {
                //添加一个log拦截器,打印所有的log
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                //可以设置请求过滤的水平,body,basic,headers
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                //设置 请求的缓存的大小跟位置
                File cacheFile = new File(BaseApplication.getmContext().getCacheDir(), "cache");
                Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //50Mb 缓存的大小

                OkHttpClient client = new OkHttpClient
                        .Builder()
                        .addInterceptor(addQueryParameterInterceptor())  //参数添加
                        .addInterceptor(addHeaderInterceptor()) // token过滤
                        .addInterceptor(httpLoggingInterceptor) //日志,所有的请求响应度看到
                        .cache(cache)  //添加缓存
                        .connectTimeout(60l, TimeUnit.SECONDS)
                        .readTimeout(60l, TimeUnit.SECONDS)
                        .writeTimeout(60l, TimeUnit.SECONDS)
                        .build();
                if (retrofit1 == null) {
                    String baseUrl = "http://202.120.40.8:30231/";
                    // 获取retrofit的实例
                    retrofit1 = new Retrofit
                            .Builder()
                            .baseUrl(baseUrl)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
                if (retrofit2 == null){
                    String baseUrl = "http://202.120.40.8:30232/";
                    // 获取retrofit的实例
                    retrofit2 = new Retrofit
                            .Builder()
                            .baseUrl(baseUrl)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
                }
            }
        }
        if(flag ==1){
            return retrofit1;
        }
        else{
            return retrofit2;
        }
    }


    // 设置公共参数
    private static Interceptor addQueryParameterInterceptor() {
        Interceptor addQueryParameterInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                Request request;
                HttpUrl modifiedUrl = originalRequest.url().newBuilder()
                        // Provide your custom parameter here
                        //.addQueryParameter("phoneSystem", "")
                        //.addQueryParameter("phoneModel", "")
                        .build();
                request = originalRequest.newBuilder().url(modifiedUrl).build();
                return chain.proceed(request);
            }
        };
        return addQueryParameterInterceptor;
    }

    // 设置头
    private static Interceptor addHeaderInterceptor() {
        Interceptor headerInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                SpUtils spUtils = new SpUtils();
                Request originalRequest = chain.request();
                Request.Builder requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
                        .header("token", (String) spUtils.get("token", ""))
//                        .header("phone",(String)SpUtils.get("phone",""))
                        .method(originalRequest.method(), originalRequest.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
        return headerInterceptor;
    }

    // 设置缓存
    private static Interceptor addCacheInterceptor() {
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetworkUtil.isNetworkAvailable(BaseApplication.getmContext())) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtil.isNetworkAvailable(BaseApplication.getmContext())) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时 ,意思就是不读取缓存数据,只对get有用,post没有缓冲
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("Retrofit")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周  只对get有用,post没有缓冲
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" +
                                    maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        return cacheInterceptor;
    }
}
