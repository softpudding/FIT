package com.example.fitmvp;


import android.content.Context;
import android.content.Intent;

import com.example.fitmvp.view.activity.MainActivity;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.event.NotificationClickEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;

public class NotificationClickEventReceiver {
    private Context mContext;

    public NotificationClickEventReceiver(Context context) {
        mContext = context;
        //注册接收消息事件
        JMessageClient.registerEventReceiver(this);
    }

    /**
     * 收到消息处理
     * @param notificationClickEvent 通知点击事件
     */
    public void onEvent(NotificationClickEvent notificationClickEvent) {
        if (null == notificationClickEvent) {
            return;
        }
        Message msg = notificationClickEvent.getMessage();
        if (msg != null) {
            String targetId = msg.getTargetID();
            String appKey = msg.getFromAppKey();
            ConversationType type = msg.getTargetType();
            Conversation conv;
            Intent notificationIntent = new Intent(mContext, MainActivity.class);
            notificationIntent.putExtra("id",2);
            if (type == ConversationType.single) {
                conv = JMessageClient.getSingleConversation(targetId, appKey);
                notificationIntent.putExtra("targtid", targetId);
                notificationIntent.putExtra("targetappkey", appKey);
            }
            else {
                conv = JMessageClient.getGroupConversation(Long.parseLong(targetId));
                notificationIntent.putExtra("groupid", Long.parseLong(targetId));
            }
            notificationIntent.putExtra("convtitle", conv.getTitle());
            conv.resetUnreadCount();
//        notificationIntent.setAction(Intent.ACTION_MAIN);
            notificationIntent.putExtra("fromGroup", false);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(notificationIntent);
        }
    }
}
