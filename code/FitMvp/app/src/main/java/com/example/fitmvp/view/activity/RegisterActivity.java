package com.example.fitmvp.view.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.RegisterContract;
import com.example.fitmvp.presenter.RegisterPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View{
    @Bind(R.id.button_register)
    FloatingActionButton register;
    @Bind(R.id.input_phone)
    EditText inputPhone;
    @Bind(R.id.input_name)
    EditText inputName;
    @Bind(R.id.input_pwd)
    EditText inputPwd;
    @Bind(R.id.input_pwd_again)
    EditText inputPwdAgain;
    @Bind(R.id.input_msg)
    EditText inputMsg;
    @Bind(R.id.get_msg)
    Button getMsg;

    private String targetMsg;

    public void setTargetMsg(String msg){
        targetMsg = msg;
    }
    @Override
    protected void setBar(){
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("注册");
    }

    @Override
    protected RegisterPresenter loadPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        register.setOnClickListener(this);
        getMsg.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.register;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_register:
                mPresenter.register(getPhone(),getName(),getPassword());
                break;
            case R.id.get_msg:
                // 发送证码
                mPresenter.sendMsg(getPhone());
        }
    }
    @Override
    protected void otherViewClick(View view) {
        mPresenter.register(getPhone(),getName(),getPassword());
    }

    @Override
    public String getPhone(){
        return inputPhone.getText().toString().trim();
    }
    @Override
    public String getName(){
        return inputName.getText().toString().trim();
    }
    @Override
    public String getPassword(){
        return inputPwd.getText().toString().trim();
    }
    @Override
    public String getPwdAgain(){
        return inputPwdAgain.getText().toString().trim();
    }

    public String getMsg(){
        return inputMsg.getText().toString().trim();
    }

    // 检查输入
    @Override
    public Boolean check(){
        Boolean flag = true;
        if (TextUtils.isEmpty(getPhone())) {
            inputPhone.setError("手机号不能为空");
            flag = false;
        }
        else if(TextUtils.isEmpty(getName())){
            inputName.setError("用户名不能为空");
            flag = false;
        }
        else if (TextUtils.isEmpty(getPassword())) {
            inputPwd.setError("密码不能为空");
            flag = false;
        }
        else if(getPassword().length()<4){
            inputPwd.setError("密码不能小于4位");
            flag = false;
        }
        else if(getPassword().length()>128){
            inputPwd.setError("密码不能大于128位");
            flag = false;
        }
        else if(TextUtils.isEmpty(getPwdAgain())){
            inputPwdAgain.setError("请确认密码");
            flag = false;
        }
        else if(!getPassword().equals(getPwdAgain())){
            inputPwdAgain.setError("两次密码不一致，请确认密码");
            flag = false;
        }
        else if(TextUtils.isEmpty(getMsg())){
            inputMsg.setError("请输入验证码");
            flag = false;
        }
        else if(!getMsg().equals(targetMsg)){
            inputMsg.setError("验证码错误");
            flag = false;
        }
        return flag;
    }

    @Override
    public Boolean checkMsg(){
        Boolean flag = true;
        if (TextUtils.isEmpty(getPhone())) {
            inputPhone.setError("手机号不能为空");
            flag = false;
        }
        return flag;
    }

    @Override
    public void registerSuccess(String str){
        AlertDialog.Builder builder  = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("提示" ) ;
        builder.setMessage(str) ;
        builder.setPositiveButton("是" ,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                RegisterActivity.this.finish();
            }} );
        builder.show();
    }
    @Override
    public void registerFail(String title,String msg){
        AlertDialog.Builder builder  = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle(title) ;
        builder.setMessage(msg) ;
        builder.setPositiveButton("是" ,  null );
        builder.show();
    }
}
