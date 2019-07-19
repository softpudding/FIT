package com.example.fitmvp.view.fragment.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.R;
import com.example.fitmvp.database.UserEntry;
import com.example.fitmvp.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.event.OfflineMessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;


public class FragmentMsg extends Fragment implements View.OnClickListener {
    private Button test;
    private Button receive;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.message, container, false);
        test = ButterKnife.findById(view, R.id.test_event);
        receive = ButterKnife.findById(view,R.id.receive_msg);
        test.setOnClickListener(this);
        receive.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.test_event:
                UserEntry user = BaseApplication.getUserEntry();
                LogUtils.e("send_msg_to",user.username);
                Message message = JMessageClient.createSingleTextMessage("15022265465",user.appKey,
                        user.username+"发送了一条消息");
                JMessageClient.sendMessage(message);
                break;
            case R.id.receive_msg:
                Integer num = JMessageClient.getAllUnReadMsgCount();
                LogUtils.e("unreadmsg",num.toString());
                List<Conversation> list = JMessageClient.getConversationList();
                if(list!=null && list.size() >0){
                    LogUtils.e("conversation_list",String.format("%d",list.size()));
                    Conversation conversation = list.get(0);
                    String msg = conversation.toJsonString();
                    LogUtils.e("content",msg);
                }
                else{
                    LogUtils.e("conversation_list","no conversation");
                }
        }
    }

    @Subscribe
    public void onEvent(MessageEvent event) {
        Message newMessage = event.getMessage();//获取此次离线期间会话收到的新消息列表
        LogUtils.e("recieve_msg",String.format(Locale.SIMPLIFIED_CHINESE, "收到一条来自%s的在线消息。\n", newMessage.getFromUser()));
    }

    /**
     * 接收离线消息
     *
     * @param event 离线消息事件
     */
    public void onEvent(OfflineMessageEvent event) {
        Conversation conv = event.getConversation();
        LogUtils.e("接收离线消息",conv.getMessage(0).toJson());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 事件接收类注册
        JMessageClient.registerEventReceiver(this);
        EventBus.getDefault().register(this);
    }



    @Override
    public void onDestroy() {
        //注销消息接收
        JMessageClient.unRegisterEventReceiver(this);
        super.onDestroy();
    }

}
