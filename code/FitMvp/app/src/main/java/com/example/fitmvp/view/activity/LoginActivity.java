package com.example.fitmvp.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.presenter.LoginPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @Bind(R.id.input_account)
    EditText inputAccount;
    @Bind(R.id.input_password)
    EditText inputPassword;
    @Bind(R.id.button_login)
    Button login;
    @Bind(R.id.toRegister)
    TextView buttonToRegister;
    @Bind(R.id.toRepassword)
    TextView changePassword;
    @Bind(R.id.wait_login)
    ProgressBar waiting;

    @Override
    protected void setBar(){
        ActionBar actionbar = getSupportActionBar();
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("登录");
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
        login.setEnabled(true);
        login.setOnClickListener(this);
        buttonToRegister.setOnClickListener(this);
        changePassword.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        waiting.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
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
            case R.id.toRegister:
                toRegister();
                break;
            case R.id.toRepassword:
                toRePassword();
                break;
            default:
                otherViewClick(view);
                break;
        }
    }

    @Override
    protected void otherViewClick(View view) {
        // 点击登录后暂时不能再点击
        waiting.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        mPresenter.login(getAccount(), getPassword());
    }

    public void setButton(){
        waiting.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
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
        waiting.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);
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
