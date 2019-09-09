package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.contract.FriendSearchContract;
import com.example.fitmvp.presenter.FriendSearchPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.UserUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendSearchActivity extends BaseActivity<FriendSearchPresenter> implements FriendSearchContract.View {
    @Bind(R.id.search)
    TextView search;
    @Bind(R.id.search_result)
    RecyclerView recyclerView;
    @Bind(R.id.input_search)
    EditText inputPhone;

    // 查找结果为 0 or 1 个
    private List<UserInfo> searchList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private BaseAdapter<UserInfo> adapter;

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

    protected FriendSearchPresenter loadPresenter() {
        return new FriendSearchPresenter();
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
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        initAdapter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.search_friend;
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
        adapter = new BaseAdapter<UserInfo>(searchList){
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.friendlist_item;
            }

            @Override
            public void convert(final MyHolder holder, UserInfo user, int position){
                holder.setText(R.id.friend_name,user.getNickname());
                holder.setText(R.id.friend_account,user.getUserName());
                // 设置头像
//                    JMessageClient.getUserInfo(user.getUserName(), new GetUserInfoCallback() {
//                        @Override
//                        public void gotResult(int i, String s, UserInfo userInfo) {
//                            if(i == 0){
//                                LogUtils.e("debug", "start to get friend avatar");
                                user.getAvatarBitmap(new GetAvatarBitmapCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Bitmap bitmap) {
                                        if (i == 0) {
                                            LogUtils.e("debug", "get friend avatar");
                                            holder.setImage(R.id.friend_photo,bitmap);
                                        }else {
                                            // 设置为默认头像
                                            holder.setImage(R.id.friend_photo,R.drawable.default_portrait36);
                                        }
                                    }
                                });
//                            }
//                        }
//                    });
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent intent = new Intent(FriendSearchActivity.this,FriendDetailActivity.class);
                // 传参
                UserInfo friend = searchList.get(position);
                Boolean isFriend = friend.isFriend();
                intent.putExtra("isFriend",isFriend);
                intent.putExtra("phone",friend.getUserName());
                intent.putExtra("nickname",friend.getNickname());
                intent.putExtra("notename",friend.getNotename());
                File file = friend.getAvatarFile();
                String path;
                if(file == null){
                    path = null;
                }
                else{
                    path = file.getAbsolutePath();
                }
                intent.putExtra("avatar",path);
                intent.putExtra("gender", UserUtils.getGender(friend));
                intent.putExtra("birthday",UserUtils.getBirthday(friend));
                Integer buttonType = 0;
                if (isFriend){
                    buttonType = 1;
                }
                intent.putExtra("buttonType",buttonType);
                startActivity(intent);
                FriendSearchActivity.this.finish();
            }

            @Override
            public void onItemLongClick(View view, int position){}
        });
        recyclerView.setAdapter(adapter);
    }

    private String getInputPhone(){
        return inputPhone.getText().toString().trim();
    }

    public void updateList(List<UserInfo> list){
        searchList = list;
        adapter.setDataList(searchList);
        adapter.notifyDataSetChanged();
    }
}
