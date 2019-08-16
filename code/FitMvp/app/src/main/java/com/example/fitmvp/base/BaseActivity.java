package com.example.fitmvp.base;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fitmvp.mvp.IView;
import com.example.fitmvp.utils.LogUtils;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.LoginStateChangeEvent;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity
        implements IView, View.OnClickListener {
    protected View view;
    protected P mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置actionBar内容
        setBar();
        setContentView(getView());
        initView();
        mPresenter = loadPresenter();
        initCommonData();
        initListener();
        initData();
        // 事件接收类注册,子类只要重写onEvent就能收到消息
        JMessageClient.registerEventReceiver(this);
    }

    protected abstract void setBar();
    protected abstract P loadPresenter();

    private void initCommonData() {
        if (mPresenter != null)
            mPresenter.attachView(this);
    }

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract void initView();

    protected abstract int getLayoutId();

    protected abstract void otherViewClick(View view);

    /**
     * @return 显示的内容
     */
    public View getView() {
        view = View.inflate(this, getLayoutId(), null);
        return view;
    }

    // 点击的事件的统一的处理
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
    }

    // ActionBar 功能
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home://actionbar的左侧图标的点击事件处理
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onEventMainThread(LoginStateChangeEvent event){

    }
}
