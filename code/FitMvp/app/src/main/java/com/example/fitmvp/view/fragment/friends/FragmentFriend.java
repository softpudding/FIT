package com.example.fitmvp.view.fragment.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.fitmvp.R;
import com.example.fitmvp.adapter.FragmentOrderListAdapter;
import com.example.fitmvp.utils.LogUtils;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;

public class FragmentFriend extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;

    private FragmentMsg fragmentMsg = new FragmentMsg();

    public FragmentFriend() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 事件接收类注册
        JMessageClient.registerEventReceiver(this);
        //EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.friends, container, false);
        viewPager = view.findViewById(R.id.friend_pager);
        tabLayout = view.findViewById(R.id.tab_friend);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragmentMsg);
        fragments.add(new FragmentFrdList());
        FragmentPagerAdapter adapter = new FragmentOrderListAdapter(getActivity().getSupportFragmentManager(),fragments, new String[]{"消息","好友"});
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        return view;
    }

    @Override
    public void onDestroy() {
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

    public void onEventMainThread(MessageEvent event) {

        LogUtils.e("接收在线消息","handle event at FragmentFriend");
        fragmentMsg.updateData();
    }

    public void onEventMainThread(OfflineMessageEvent event) {

        LogUtils.e("接收离线消息","handle event at FragmentFriend");
        fragmentMsg.updateData();
    }

//    public void onEvent(ContactNotifyEvent event){
//        LogUtils.e("接收contactNotifyEvnent","handle event at FragmentFriend");
//        String reason = event.getReason();
//        String fromUsername = event.getFromUsername();
//        ContactNotifyEvent.Type type = event.getType();
////        mPresenter.handleEvent(fromUsername,reason,type);
//    }

}
