package com.example.fitmvp.view.fragment.friends;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.bean.FriendInfo;
import com.example.fitmvp.contract.FriendContract;
import com.example.fitmvp.presenter.FriendPresenter;
import com.example.fitmvp.utils.PictureUtil;
import com.example.fitmvp.view.activity.FriendDetailActivity;
import com.example.fitmvp.view.activity.SearchFriendActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class FragmentFrdList extends BaseFragment<FriendPresenter>
        implements FriendContract.View {

    private List<UserInfo> friendList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
    private FloatingActionButton addFriend;

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
    }

    @Override
    protected void initData(){
        // 获取好友列表
        mPresenter.initFriendList();
        // 创建adapter实例
        BaseAdapter<UserInfo> adapter = new BaseAdapter<UserInfo>(friendList) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.friendlist_item;
            }

            @Override
            public void convert(final MyHolder holder, UserInfo data, int position) {
                // 显示给好友的备注名
                if(data.getNotename()!=null){
                    holder.setText(R.id.friend_name,data.getNotename());
                }
                // 显示用户昵称
                else{
                    holder.setText(R.id.friend_name,data.getNickname());
                }
                // 设置头像
                if(data.getAvatar()!=null){
                    data.getAvatarBitmap(new GetAvatarBitmapCallback(){
                        @Override
                        public void gotResult(int status, String desc, Bitmap bitmap) {
                            if (status == 0) {
                                holder.setImage(R.id.friend_photo,bitmap);
                            }else {
                                // 设置为默认头像
                                holder.setImage(R.id.friend_photo,R.drawable.default_portrait36);
                            }
                        }
                    });
                }
                else{
                    // 设置为默认头像
                    holder.setImage(R.id.friend_photo,R.drawable.default_portrait36);
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
                UserInfo friend = friendList.get(position);
                intent.putExtra("isFriend",true);
                intent.putExtra("phone",friend.getUserName());
                intent.putExtra("nickname",friend.getNickname());
                intent.putExtra("gender",friend.getGender());
                intent.putExtra("birthday",friend.getBirthday());
                intent.putExtra("notename",friend.getNotename());
                friend.getAvatarBitmap(new GetAvatarBitmapCallback() {
                    @Override
                    public void gotResult(int i, String s, Bitmap bitmap) {
                        if(i == 0){
                            if(bitmap != null){
                                byte[] bytes = PictureUtil.Bitmap2Bytes(bitmap);
                                intent.putExtra("avatar",bytes);
                            }
                        }
                    }
                });
                startActivity(intent);
            }
        });
        // 设置adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initListener(){
        addFriend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.add_friend:
                toAddFriend();
                break;
        }
    }

    public void setFriendList(List<UserInfo> list){
        friendList = list;
    }

    private void toAddFriend(){
        Intent intent = new Intent(getActivity(), SearchFriendActivity.class);
        startActivity(intent);
    }
}