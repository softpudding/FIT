package com.example.fitmvp.model;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.ConversationEntity;
import com.example.fitmvp.chat.utils.TimeFormat;
import com.example.fitmvp.contract.MessageContract;
import com.nostra13.universalimageloader.utils.L;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.model.UserInfo;

public class MessageModel extends BaseModel implements MessageContract.Model {
    public List<ConversationEntity> getConvList(){
        // 从本地数据库中获取会话列表，默认按照会话的最后一条消息的时间，降序排列
        List<Conversation> convList = JMessageClient.getConversationList();
        List<ConversationEntity> list = new ArrayList<>();
        for(Conversation conv : convList) {
            if(conv!=null){
                ConversationEntity entity = getEntity(conv);
                if(entity!=null){
                    list.add(entity);
                }
            }
        }
        if(list!=null){
            // 按时间降序
            Collections.sort(list, new Comparator<ConversationEntity>() {
                @Override
                public int compare(ConversationEntity t1, ConversationEntity t2) {
                    Long time1 = t1.getRawTime();
                    Long time2 = t2.getRawTime();
                    Long diff = time1 - time2;
                    if (diff > 0) {
                        return -1;
                    }
                    else if (diff < 0) {
                        return 1;
                    }
                    return 0;
                }
            });
        }
        return list;
    }

    public ConversationEntity getEntity(Conversation conv){
        ConversationEntity entity = new ConversationEntity();
        entity.setId(conv.getId());
        // 未读数量
        entity.setNewMsgNum(conv.getUnReadMsgCnt());
        // 单聊
        UserInfo user = (UserInfo) conv.getTargetInfo();
        // 头像
        entity.setAvatar(user.getAvatar());
        // 显示的名字
        String name = user.getNotename();
        if (name == null || name.equals("")) {
            name = user.getNickname();
        }
        if (name == null || name.equals("")) {
            name = user.getUserName();
        }
        entity.setUsername(user.getUserName());
        entity.setTitle(name);
        Message latestMessage = conv.getLatestMessage();
        // 跳过
        if(latestMessage == null){
            return null;
        }
        // 时间
        Long time = latestMessage.getCreateTime();
        entity.setRawTime(time);
        TimeFormat timeFormat = new TimeFormat(BaseApplication.getmContext(),time);
        entity.setTime(timeFormat.getTime());
        // 消息 text
        String content = ((TextContent) latestMessage.getContent()).getText();
        entity.setMessage(content);
        return entity;
    }
}
