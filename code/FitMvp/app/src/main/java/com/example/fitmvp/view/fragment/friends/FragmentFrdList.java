package com.example.fitmvp.view.fragment.friends;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.database.FriendEntry;
import com.example.fitmvp.presenter.FriendPresenter;
import com.example.fitmvp.view.activity.FriendDetailActivity;
import com.example.fitmvp.view.activity.FriendRecommendActivity;
import com.example.fitmvp.view.activity.FriendSearchActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FragmentFrdList extends BaseFragment<FriendPresenter>
        implements FriendContract.View {

    private List<FriendEntry> friendList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    private FloatingActionButton addFriend;
    private LinearLayout recommends;

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
        recyclerView = ButterKnife.findById(view,R.id.friend_list);
        recyclerView.setLayoutManager(linearLayoutManager);
        addFriend = ButterKnife.findById(view,R.id.add_friend);
        recommends = ButterKnife.findById(view,R.id.friend_recommend);
    }

    @Override
    protected void initData(){
        // 获取好友列表
        friendList = mPresenter.getFriendList();
        // 创建adapter实例
        BaseAdapter<FriendEntry> adapter = new BaseAdapter<FriendEntry>(friendList) {
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
                                            holder.setImage(R.id.friend_photo,R.drawable.default_portrait36);
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
        });
        // 设置adapter
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
    }

}