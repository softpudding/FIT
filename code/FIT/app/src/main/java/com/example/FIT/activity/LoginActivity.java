package com.example.FIT.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.FIT.R;
import com.example.FIT.User;
import com.example.FIT.network.NetWorkManager;
import com.example.FIT.network.Response;
import com.example.FIT.network.UserService;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.reactivestreams.Subscriber;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
/*
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
*/

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText accountText;
    private EditText pwText;
    private ImageButton loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        accountText = findViewById(R.id.text_account);
        pwText = findViewById(R.id.text_pw);
        loginButton = findViewById(R.id.button_login);
        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_login:
                String account = accountText.getText().toString();
                String password = pwText.getText().toString();
                Log.d("Click login","acount="+account);
                Log.d("Click login","ps = "+password);
                login2(account,password);
        }
    }
    private void login2(String accout,String password){
        // 创建实例
        NetWorkManager.getInstance().init();
        Retrofit retrofit = NetWorkManager.getInstance().getRetrofit();
        UserService request = retrofit.create(UserService.class);

        // 创建网络请求接口实例
        Observable<String> observable = request.login(accout,password);
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

                    private AlertDialog.Builder builder  = new AlertDialog.Builder(LoginActivity.this);
                    @Override
                    public void onError(Throwable e) {
                        System.err.println("onError");
                        builder.setTitle("错误" ) ;
                        builder.setMessage("get response null") ;
                        builder.setPositiveButton("是" ,  null );
                        builder.show();
                    }

                    @Override
                    public void onNext(String flag) {
                        System.out.println(flag);

                        switch(flag){
                            case "100":
                                Intent intent = new Intent();
                                intent.setClass(LoginActivity.this, MainActivity.class);
                                intent.putExtra("id",0);
                                startActivity(intent);
                                break;
                            case "101":
                                builder.setTitle("错误" ) ;
                                builder.setMessage("账号不存在" ) ;
                                builder.setPositiveButton("是" ,  null );
                                builder.show();
                                break;
                            case "102":
                                builder.setTitle("错误" ) ;
                                builder.setMessage("密码错误" ) ;
                                builder.setPositiveButton("是" ,  null );
                                builder.show();
                                break;
                            case "103":
                                builder.setTitle("错误" ) ;
                                builder.setMessage("账号被禁用" ) ;
                                builder.setPositiveButton("是" ,  null );
                                builder.show();
                                break;
                            default:
                                builder.setTitle("错误" ) ;
                                builder.setMessage("response: " + flag) ;
                                builder.setPositiveButton("是" ,  null );
                                builder.show();
                                break;
                        }
                    }

                });
    }
    public void toMain(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        intent.putExtra("id",0);
        startActivity(intent);
    }
    public void toRegister(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
