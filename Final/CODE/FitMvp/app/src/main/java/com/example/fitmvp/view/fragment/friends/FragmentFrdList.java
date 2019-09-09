package com.example.fitmvp.view.fragment.friends;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.presenter.FriendPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.SpUtils;
import com.example.fitmvp.view.activity.FriendDetailActivity;
import com.example.fitmvp.view.activity.FriendRecommendActivity;
import com.example.fitmvp.view.activity.FriendSearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.model.UserInfo;

public class FragmentFrdList extends BaseFragment<FriendPresenter>
        implements FriendContract.View {

    private List<FriendEntry> friendList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    BaseAdapter<FriendEntry> adapter;
    private FloatingActionButton addFriend;
    private LinearLayout recommends;
    private TextView cachedNewFriendNum;


    @Override
    protected Integer getLayoutId(){
        return R.layout.friendlist;
    }

    @Override
    protected FriendPresenter loadPresenter() {
        return new FriendPresenter();
    }

    @Override
    protected void initView(){
        recyclerView = view.findViewById(R.id.friend_list);
        recyclerView.setLayoutManager(linearLayoutManager);

        addFriend = view.findViewById(R.id.add_friend);
        recommends = view.findViewById(R.id.friend_recommend);
        cachedNewFriendNum = view.findViewById(R.id.friend_unread_msg);
        //注册刷新Fragment数据的方法
        registerReceiver();
    }

    public void setCachedNewFriendNum(){
        new Handler().post(new Runnable() {
            public void run() {
                //在这里来写你需要刷新的地方
                SpUtils spUtils = new SpUtils();
                Integer num = spUtils.getCachedNewFriendNum();
                LogUtils.e("set cache num",num.toString());
                if(num>0){
                    cachedNewFriendNum.setText(String.format("%d",num));
                    cachedNewFriendNum.setVisibility(View.VISIBLE);
                }
                else{
                    cachedNewFriendNum.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void initData(){

        mPresenter.initCacheNum();
        mPresenter.initFriendList();
        // 获取好友列表
        friendList = mPresenter.getFriendList();
        // 创建adapter实例
        adapter = new BaseAdapter<FriendEntry>(friendList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.friendlist_item;
            }

            @Override
            public void convert(final MyHolder holder, FriendEntry data, int position) {
                // 显示给好友的备注名或昵称
                holder.setText(R.id.friend_name,data.displayName);

                // 设置头像
                if(data.avatar!=null){
                    LogUtils.e("avatar",data.avatar);
                    holder.setImage(R.id.friend_photo, BitmapFactory.decodeFile(data.avatar));
                }
                else{
                    JMessageClient.getUserInfo(data.username, new GetUserInfoCallback() {
                        @Override
                        public void gotResult(int i, String s, UserInfo userInfo) {
                            if(i == 0){
                                userInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Bitmap bitmap) {
                                        if (i == 0) {
                                            holder.setImage(R.id.friend_photo,bitmap);
                                        }else {
                                            // 设置为默认头像
                                            holder.setImage(R.id.friend_photo,R.drawable.default_portrait80);
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        };
        // 设置adapter监听事件
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            // 跳转至对应的好友详情页面
            @Override
            public void onItemClick(View view, int position) {
                final Intent intent = new Intent(getActivity(), FriendDetailActivity.class);
                // 传参
                FriendEntry friend = friendList.get(position);
                intent.putExtra("isFriend",true);
                intent.putExtra("phone",friend.username);
                intent.putExtra("nickname",friend.nickName);
                intent.putExtra("notename",friend.noteName);
                intent.putExtra("avatar",friend.avatar);
                intent.putExtra("gender",friend.gender);
                intent.putExtra("birthday",friend.birthday);
                intent.putExtra("buttonType",1);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position){}
        });
        //设置adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener(){
        addFriend.setOnClickListener(this);
        recommends.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.add_friend:
                toAddFriend();
                break;
            case R.id.friend_recommend:
                toRecommend();
                break;
        }
    }

    private void toAddFriend(){
        Intent intent = new Intent(getActivity(), FriendSearchActivity.class);
        startActivity(intent);
    }
    private void toRecommend(){
        Intent intent = new Intent(getActivity(), FriendRecommendActivity.class);
        startActivity(intent);
        SpUtils spUtils = new SpUtils();
        spUtils.setCachedNewFriendNum(0);
        new Handler().post(new Runnable() {
            public void run() {
                setCachedNewFriendNum();
            }
        });
    }

    public void onEvent(ContactNotifyEvent event){
        LogUtils.e("onEvent","start");
        String reason = event.getReason();
        String fromUsername = event.getFromUsername();
        ContactNotifyEvent.Type type = event.getType();
        mPresenter.handleEvent(fromUsername,reason,type);
    }

    public void updateData(){
        LogUtils.e("update_list","update data");
        // 获取好友列表
        friendList = mPresenter.getFriendList();
        adapter.setDataList(friendList);
        adapter.notifyDataSetChanged();
        setCachedNewFriendNum();
    }

    private LocalBroadcastManager broadcastManager;

    //注册广播接收器
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateFriendList");
        intentFilter.addAction("updateFriendNoteName");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if ("updateFriendList".equals(action) || "updateFriendNoteName".equals(action)) {
                // 在主线程中刷新UI，用Handler来实现
                new Handler().post(new Runnable() {
                    public void run() {
                        //在这里来写你需要刷新的地方
                        updateData();
                    }
                });
            }
        }
    };

    @Override
    public void onDetach() {
        super.onDetach();
        //注销广播
        broadcastManager.unregisterReceiver(mRefreshReceiver);
    }
}