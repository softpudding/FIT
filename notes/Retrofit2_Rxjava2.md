# Rxjava+Retrofit2+OkHttp3 使用

前端的http网络访问使用 Rxjava2, Retrofit2, OkHttp3

## 准备

添加依赖

```gradle
// okhttp3
implementation 'com.squareup.okhttp3:okhttp:3.10.0'
implementation 'com.squareup.okio:okio:1.14.0'
// rxjava
implementation "io.reactivex.rxjava2:rxjava:2.1.0"
implementation "io.reactivex.rxjava2:rxandroid:2.0.1" // 切线程时需要用到
// retrofit
implementation 'com.squareup.retrofit2:retrofit:2.3.0'
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0' // 和Rxjava结合必须用到
implementation 'com.squareup.retrofit2:converter-gson:2.3.0' // 解析json字符所用
```

Rxjava2 - 使异步操作保持简洁

Retrofit2 - 将一个基本的Java接口通过动态代理的方式翻译成一个HTTP请求，并通过OkHttp去发送请求。所以也需要加入OkHttp的依赖。

添加网络权限

在AndroidManifest.xml中添加

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

## 使用Rxjava的步骤（以Login为例）

1. 创建**接收服务器返回数据**的类
   根据后端传回来的数据结构创建对应的类

2. 创建**用于描述网络请求**的接口

    登录请求的接口

   ```java
   @POST("/usr/login")
    @FormUrlEncoded
    Observable<String> login(@Field("account") String account,@Field("password") String password);
    ```

    ```@FormUrlEncoded```表示发送form-encoded的数据
    ```@Field("key")```发送Post请求时提交请求的表单字段，和```@FormUrlEncoded```配合使用

    对应后端使用```@RequestParam```

3. 创建 Retrofit 实例

   封装在init()中

    ```java
    public void init() {
        // 初始化okhttp
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        // 初始化Retrofit
        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("http://202.120.40.8:30231/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
    ```

    converter - 数据解析

    callAdapter - 网络请求适配器

4. 创建网络请求接口实例

    ```java
    UserService request = retrofit.create(UserService.class);
    Observable<String> observable = request.login(accout,password);
    ```

5. 发送异步请求并处理返回数据(使用rxjava)

    ```java
    observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Observer<String>(){
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        System.err.println("onError");
                    }

                    @Override
                    public void onNext(String flag) {
                        System.out.println(flag);

                        switch(flag){
                            case "100":
                            ...
                        }
                    }

                });
    ```

## Tip

rxjava1.x和rxjava2.x在使用上有一些差异，例如rxjava2在Subscriber和Observer类中增加了onSubscribe的方法，需要Override。

在网上找到的一些教程可能用的是rxjava1，所以有些地方直接拿过来用会报错

>*其他详细的用法和特点在后面遇到后再补充*

## 参考

[给 Android 开发者的 RxJava 详解](http://gank.io/post/560e15be2dca930e00da1083)

[Retrofit 2.0 使用教程](https://blog.csdn.net/carson_ho/article/details/73732076)

[RxJava2.0](https://blog.csdn.net/jdsjlzx/article/details/54845517)

[RxJava2 Observable](https://www.cnblogs.com/zhujiabin/p/8193028.html
)