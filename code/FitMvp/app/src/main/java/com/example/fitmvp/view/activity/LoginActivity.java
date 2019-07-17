package com.example.fitmvp.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.presenter.LoginPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @InjectView(R.id.input_account)
    EditText inputAccount;
    @InjectView(R.id.input_password)
    EditText inputPassword;
    @InjectView(R.id.button_login)
    FloatingActionButton login;
    @InjectView(R.id.button_toRegister)
    Button buttonToRegister;
    @InjectView(R.id.toRepassword)
    TextView changePassword;
    @InjectView(R.id.button2)
    Button toMain;

    @Override
    protected void setBar(){
    }
    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        login.setOnClickListener(this);
        buttonToRegister.setOnClickListener(this);
        changePassword.setOnClickListener(this);
        toMain.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                otherViewClick(view);
                break;
            case R.id.button_toRegister:
                toRegister();
                break;
            case R.id.toRepassword:
                toRePassword();
                break;
            // 直接去主页，调试用
            case R.id.button2:
                loginSuccess();
                break;
            default:
                otherViewClick(view);
                break;
        }
    }

    @Override
    protected void otherViewClick(View view) {
        mPresenter.login(getAccount(), getPassword());
    }

    @Override
    public String getAccount() {
        return inputAccount.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return inputPassword.getText().toString().trim();
    }

    @Override
    public void loginSuccess(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, MainActivity.class);
        intent.putExtra("id",0);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void loginFail(String title, String str){
        AlertDialog.Builder builder  = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle(title) ;
        builder.setMessage(str) ;
        builder.setPositiveButton("是" ,  null );
        builder.show();
    }

    public void toRegister(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void toRePassword(){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, ChangePwActivity.class);
        startActivity(intent);
    }

    @Override
    public void setAccountError(String str){
        inputAccount.setError(str);
    }
    @Override
    public void setPwError(String str){
        inputPassword.setError(str);
    }
}
