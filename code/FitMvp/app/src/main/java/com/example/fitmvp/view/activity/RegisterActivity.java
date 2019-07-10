package com.example.fitmvp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.RegisterContract;
import com.example.fitmvp.presenter.RegisterPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.View{
    @InjectView(R.id.button_register)
    Button register;
    @InjectView(R.id.input_phone)
    EditText inputPhone;
    @InjectView(R.id.input_name)
    EditText inputName;
    @InjectView(R.id.input_pwd)
    EditText inputPwd;
    @InjectView(R.id.input_pwd_again)
    EditText inputPwdAgain;

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
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.register;
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
        else if(TextUtils.isEmpty(getPwdAgain())){
            inputPwdAgain.setError("请确认密码");
            flag = false;
        }
        else if(!getPassword().equals(getPwdAgain())){
            inputPwdAgain.setError("两次密码不一致，请确认密码");
        }
        return flag;
    }

    @Override
    public void registerSuccess(){
        AlertDialog.Builder builder  = new AlertDialog.Builder(RegisterActivity.this);
        builder.setTitle("提示" ) ;
        builder.setMessage("注册成功，请登录" ) ;
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
