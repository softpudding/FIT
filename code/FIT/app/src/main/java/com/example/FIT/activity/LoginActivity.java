package com.example.FIT.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.FIT.R;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
                login(account,password);
        }
    }
    private void login(String account,String password){
        // 发送http POST请求
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody requestBody = new FormBody.Builder().add("account",account)
                                                        .add("password",password)
                                                        .build();
        Request request = new Request.Builder().url("http://202.120.40.8:30231/usr/login")
                                                .post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        // 请求加入调度,重写回调方法
        call.enqueue(new Callback() {

            AlertDialog.Builder builder  = new AlertDialog.Builder(LoginActivity.this);
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("fail",e.getMessage());
                builder.setTitle("错误" ) ;
                builder.setMessage("发送登录请求失败" ) ;
                builder.setPositiveButton("是" ,  null );
                builder.show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String flag = response.body().string();
                if(flag!=null){
                    if(flag.equals("100")){
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        intent.putExtra("id",0);
                        startActivity(intent);
                    }
                    else if(flag.equals("101")){
                        builder.setTitle("错误" ) ;
                        builder.setMessage("账号不存在" ) ;
                        builder.setPositiveButton("是" ,  null );
                        builder.show();
                    }
                    else if(flag.equals("102")){
                        builder.setTitle("错误" ) ;
                        builder.setMessage("密码错误" ) ;
                        builder.setPositiveButton("是" ,  null );
                        builder.show();
                    }
                    else if(flag.equals("103")){
                        builder.setTitle("错误" ) ;
                        builder.setMessage("账号被禁用" ) ;
                        builder.setPositiveButton("是" ,  null );
                        builder.show();
                    }
                    else{
                        builder.setTitle("错误" ) ;
                        builder.setMessage("response: " + flag) ;
                        builder.setPositiveButton("是" ,  null );
                        builder.show();
                    }
                }
                else{
                    builder.setTitle("错误" ) ;
                    builder.setMessage("get response null") ;
                    builder.setPositiveButton("是" ,  null );
                    builder.show();
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
    public void toRepassword(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, Repassword.class);
        startActivity(intent);
    }
}
