package com.example.fitmvp.view.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.presenter.LoginPresenter;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View {
    @InjectView(R.id.input_account)
    EditText inputAccount;
    @InjectView(R.id.input_pw)
    EditText inputPassword;
    @InjectView(R.id.button_login)
    ImageButton login;

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


    public boolean checkNull() {
        boolean isNull = false;
        if (TextUtils.isEmpty(getAccount())) {
            inputAccount.setError("账号不能为空");
            isNull = true;
        } else if (TextUtils.isEmpty(getPassword())) {
            inputPassword.setError("密码不能为空");
            isNull = true;
        }
        return isNull;
    }

}
