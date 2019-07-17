package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.bean.FriendInfo;
import com.example.fitmvp.bean.RecordItem;
import com.example.fitmvp.contract.AddFriendContract;
import com.example.fitmvp.presenter.AddFriendPresenter;
import com.example.fitmvp.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class AddFriendActivity extends BaseActivity<AddFriendPresenter> implements AddFriendContract.View {
    @InjectView(R.id.search)
    TextView search;
    @InjectView(R.id.search_result)
    RecyclerView recyclerView;
    @InjectView(R.id.input_search)
    EditText inputPhone;

    // 查找结果为 0 or 1 个
    private List<FriendInfo> searchList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private BaseAdapter<FriendInfo> adapter;

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
        actionbar.setTitle("添加好友");
    }

    protected AddFriendPresenter loadPresenter() {
        return new AddFriendPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        search.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.add_friend;
    }

    @Override
    protected void otherViewClick(View view) {
        String searchingPhone = getInputPhone();
        // 输入不为空时进行查找
        if(!TextUtils.isEmpty(searchingPhone)){
            mPresenter.search(searchingPhone);
        }
    }

    public void initAdapter(){
        adapter = new BaseAdapter<FriendInfo>(searchList){
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.friendlist_item;
            }

            @Override
            public void convert(final MyHolder holder, FriendInfo user, int position){
                UserInfo info = user.getFriendInfo();
                holder.setText(R.id.friend_name,info.getNickname());
                holder.setText(R.id.friend_account,info.getUserName());
                if(info.getAvatar() != null){
                    info.getAvatarBitmap(new GetAvatarBitmapCallback() {
                        @Override
                        public void gotResult(int i, String s, Bitmap bitmap) {
                            if(i == 0){
                                holder.setImage(R.id.friend_photo,bitmap);
                            }
                        }
                    });
                }
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(AddFriendActivity.this,FriendDetailActivity.class);
                // 传参
                FriendInfo item = searchList.get(position);
                intent.putExtra("isFriend",item.getIsFriend());
                //intent.putExtra("friend",item);
                // 传项目中图片
                //intent.putExtra("image", item.getImage());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private String getInputPhone(){
        return inputPhone.getText().toString().trim();
    }

    @Override
    public void setSearchList(List<FriendInfo> list) {
        searchList = list;
    }
}
