package com.example.fitmvp.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.chat.view.TipItem;
import com.example.fitmvp.chat.view.TipView;
import com.example.fitmvp.contract.FriendRecommendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.database.FriendRecommendEntry;
import com.example.fitmvp.presenter.FriendRecommendPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class FriendRecommendActivity extends BaseActivity<FriendRecommendPresenter>
        implements FriendRecommendContract.View{
    @Bind(R.id.recommend_list)
    RecyclerView recyclerView;
    @Bind(R.id.recommend_hint)
    TextView hint;
    @Bind(R.id.recommend_view)
    RelativeLayout mView;

    private List<FriendRecommendEntry> recommendList = new ArrayList<>();
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private BaseAdapter<FriendRecommendEntry> adapter;

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
        actionbar.setTitle("验证消息");
    }

    protected FriendRecommendPresenter loadPresenter() {
        return new FriendRecommendPresenter();
    }

    @Override
    protected void initData() {
        // 获取列表数据
        recommendList = mPresenter.getRecommendList();
        if(recommendList.size()==0){
            hint.setVisibility(View.VISIBLE);
        }
        else{
            hint.setVisibility(View.GONE);
        }
        // 初始化adapter
        initAdapter();
    }

    @Override
    protected void initListener() {}

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        //注册刷新Fragment数据的方法
        registerReceiver();
        recyclerView.setLayoutManager(linearLayoutManager);
        hint.setVisibility(View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.friend_recommend;
    }

    @Override
    protected void otherViewClick(View view) {}

    public void initAdapter() {
        adapter = new BaseAdapter<FriendRecommendEntry>(recommendList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.message_item;
            }

            @Override
            public void convert(final MyHolder holder, FriendRecommendEntry user, int position) {
                holder.setText(R.id.message_name, user.nickName);
                holder.setText(R.id.message, user.reason);
                holder.setText(R.id.message_time,user.state);
                // 设置头像
                if (user.avatar != null) {
                    holder.setImage(R.id.message_photo, BitmapFactory.decodeFile(user.avatar));
                } else {
                    JMessageClient.getUserInfo(user.username, new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            if (i == 0) {
                                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Bitmap bitmap) {
                                        if (i == 0) {
                                            holder.setImage(R.id.message_photo, bitmap);
                                        } else {
                                            // 设置为默认头像
                                            holder.setImage(R.id.message_photo, R.drawable.default_portrait36);
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        };
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                final Intent intent = new Intent(FriendRecommendActivity.this,FriendDetailActivity.class);
                // 传参
                FriendRecommendEntry item = recommendList.get(position);
                FriendEntry friend = mPresenter.getUser(item.username);

                Boolean isFriend;
                Integer buttonType;
                String phone = item.username;
                String nickname = item.nickName;
                String avatar = item.avatar;
                String notename, gender, birthday;
                if(friend != null){
                    isFriend = true;
                    buttonType = 1;
                    notename = friend.noteName;
                    gender = friend.gender;
                    birthday = friend.birthday;
                }
                else{
                    if(item.state.equals("请求加为好友")){
                        isFriend = false;
                        buttonType = 2;
                        notename = "";
                        gender = "";
                        birthday = "";
                    }
                    // 应该在好友列表中 不会到这里
                    else if(item.state.equals("对方已同意") || item.state.equals("已同意")){
                        isFriend = true;
                        buttonType = 1;
                        notename = item.noteName;
                        gender = "";
                        birthday = "";
                    }
                    else{
                        isFriend = false;
                        buttonType = 0;
                        notename = "";
                        gender = "";
                        birthday = "";
                    }
                }
                intent.putExtra("isFriend",isFriend);
                intent.putExtra("buttonType",buttonType);
                intent.putExtra("phone",phone);
                intent.putExtra("nickname",nickname);
                intent.putExtra("notename",notename);
                intent.putExtra("avatar",avatar);
                intent.putExtra("gender", gender);
                intent.putExtra("birthday",birthday);
                startActivity(intent);
                // FriendRecommendActivity.this.finish();
            }
            // 长按删除
            @Override
            public void onItemLongClick(View view, int position){
                final FriendRecommendEntry item = recommendList.get(position);
                int[] location = new int[2];
                view.getLocationOnScreen(location);
                float OldListY = (float) location[1];
                float OldListX = (float) location[0];
                new TipView.Builder(FriendRecommendActivity.this, mView, (int) OldListX + view.getWidth() / 2, (int) OldListY + view.getHeight())
                        .addItem(new TipItem("删除"))
                        .setOnItemClickListener(new TipView.OnItemClickListener() {
                            @Override
                            public void onItemClick(String name, int position) {
                                // 删除
                                FriendRecommendEntry.deleteEntry(item);
                                recommendList.remove(item);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }

                            @Override
                            public void dismiss() {

                            }
                        })
                        .create();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    public void updateData(){
        recommendList = mPresenter.getRecommendList();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setDataList(recommendList);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private LocalBroadcastManager broadcastManager;

    //注册广播接收器
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateRecommend");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    // 接收广播，刷新好友备注名
    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            String action= intent.getAction();
            if ("updateRecommend".equals(action)) {
                updateData();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        //注销广播
        broadcastManager.unregisterReceiver(mRefreshReceiver);
    }
}
