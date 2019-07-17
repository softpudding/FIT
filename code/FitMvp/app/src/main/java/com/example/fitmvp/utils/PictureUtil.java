package com.example.fitmvp.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import com.example.fitmvp.bean.PhotoType1Bean;
import com.example.fitmvp.bean.TypeOne;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.network.Http;
import com.example.fitmvp.network.PhotoService;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PictureUtil {
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }
    public static String bitmapToBase64(Bitmap bitmap) {
        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    //图片传送接口
    public static void passPhoto1(Bitmap bitmap){
        Integer obj_type=1;
        System.out.println(233333);
        String pic= PictureUtil.bitmapToBase64(bitmap);
        System.out.println(obj_type);
        TypeOne one= new TypeOne();
        one.setObjtype(obj_type);
        one.setImg(pic);
//        Retrofit retrofit=new Retrofit.Builder()
//                .baseUrl("http://202.120.40.8:30232/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        PhotoService photoService=retrofit.create(PhotoService.class);
//        Call<String> call=Http.getHttpService().photoSend(obj_type,pic);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                System.out.println(response);
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
        Http.getHttpService(2).photoSend(obj_type,pic)
                .compose(new ThreadTransformer<PhotoType1Bean>())
                .subscribe(new CommonObserver<PhotoType1Bean>() {
                    // 请求成功返回后检查登录结果
                    @Override
                    public void onNext(PhotoType1Bean response) {
                        System.out.println(response.getFoodname());
                        System.out.println(response.getProbsbility());
                        }
                    @Override
                    public void onError(ApiException e){
                        System.err.println("onError: "+ e.getMessage());
                    }
                });

    }

    public static void passPhoto2(Bitmap bitmap){

    }
}
