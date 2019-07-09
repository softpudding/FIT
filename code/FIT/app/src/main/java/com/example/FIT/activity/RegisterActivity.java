package com.example.FIT.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.FIT.R;
import com.example.FIT.network.NetWorkManager;
import com.example.FIT.network.UserService;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText tPhone;
    private EditText tPsw;
    private EditText tPswc;
    private EditText tName;
    private EditText tVer;
    private ImageButton regisButton;
    private Button gainVeri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        tPhone = findViewById(R.id.r_phone);
        tVer=findViewById(R.id.r_verify);
        tName=findViewById(R.id.r_name);
        tPsw=findViewById(R.id.r_psw);
        tPswc=findViewById(R.id.r_pswc);
        gainVeri=findViewById(R.id.gain_veri);
        regisButton=findViewById(R.id.button_regis);
        regisButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        String tel = tPhone.getText().toString();
        String veri = tVer.getText().toString();
        String nickName=tName.getText().toString();
        String password=tPsw.getText().toString();
        String pswc=tPswc.getText().toString();
        switch (view.getId()){
            case R.id.gain_veri:
                if(tel.equals("")){
                    Log.d("no phone","gainVerification");
                }
            case R.id.button_regis:
                if(tel.equals("")){
                    new AlertDialog.Builder(RegisterActivity.this).
                            setTitle("提示").setMessage("请输入手机号").setNegativeButton("OK",null)
                            .show();
                }
                else if(nickName.equals("")||password.equals("")){
                    new AlertDialog.Builder(RegisterActivity.this).
                            setTitle("提示").setMessage("请输入用户名及密码").setNegativeButton("OK",null)
                            .show();
                }
                else if(!password.equals(pswc)){
                    new AlertDialog.Builder(RegisterActivity.this).
                            setTitle("提示").setMessage("请确认密码").setNegativeButton("OK",null)
                            .show();
                }
                else if(!veri.equals("1")){
                    new AlertDialog.Builder(RegisterActivity.this).
                            setTitle("提示").setMessage("验证码错误").setNegativeButton("OK",null)
                            .show();
                }
                else {
                    Log.d("Click regis","tel="+tel);
                    Log.d("Click regis","psw = "+password);
                    Log.d("Click regis","name = "+nickName);
                    regis2(tel,password,nickName);}
        }
    }
    private void regis2(String tel,String password,String nickName) {
        // 创建实例
        NetWorkManager.getInstance().init();
        Retrofit retrofit = NetWorkManager.getInstance().getRetrofit();
        UserService request = retrofit.create(UserService.class);
        // 创建网络请求接口实例
        Observable<String> observable = request.register(tel,password,nickName);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //最后在主线程中执行
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
                    private AlertDialog.Builder builder  = new AlertDialog.Builder(RegisterActivity.this);
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
                            case "1":
                                builder.setTitle("提示" ) ;
                                builder.setMessage("注册成功，请登录" ) ;
                                builder.setPositiveButton("是" ,  null );
                                builder.show();
                                //这里需要加个延时，还没写
                                Intent intent = new Intent();
                                intent.setClass(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
//                                intent.setClass(RegisterActivity.this, LoginActivity.class);
//                                intent.putExtra("id",0);
//                                startActivity(intent);
                                break;
                            case "0":
                                builder.setTitle("错误" ) ;
                                builder.setMessage("注册失败" ) ;
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
    public void toLogin(View view){
        Intent intent = new Intent();
        intent.setClass(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
