package com.example.fitmvp.model;

import com.example.fitmvp.BaseApplication;
import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.ConversationEntity;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.TimeFormat;
import com.example.fitmvp.contract.MessageContract;

import java.io.File;
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
    private static List<ConversationEntity> list  = new ArrayList<>();

    public List<ConversationEntity> getConvList(){
        // 从本地数据库中获取会话列表，默认按照会话的最后一条消息的时间，降序排列
        List<Conversation> convList = JMessageClient.getConversationList();
        list.clear();
        if(convList==null){
            return null;
        }
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

    public List<ConversationEntity> updateConv(Conversation conv){
        for(ConversationEntity entity: list){
            Integer index = list.indexOf(entity);
            UserInfo userInfo = (UserInfo) conv.getTargetInfo();
            // 找到对应的会话后更新，跳出循环
            if(entity.getUsername().equals(userInfo.getUserName())){
                entity = getEntity(conv);
                list.remove(index);
                list.add(entity);
                break;
            }
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
        File file = user.getAvatarFile();
        if(file!=null){
            // 头像
            entity.setAvatar(user.getAvatarFile().getAbsolutePath());
        }
        else{
            entity.setAvatar(null);
        }
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

    // 删除对话返回true，没有可删除的对话返回false
    public Boolean deleteConv(String username){
        ConversationEntity entity = findConv(username);
        if(entity!=null){
            JMessageClient.deleteSingleConversation(username, null);
            list.remove(entity);
            return true;
        }
        else{
            return false;
        }
    }

    // 删除会话中的消息，不删除会话
    public Boolean deleteMsg(ConversationEntity entity){
        Conversation conv = JMessageClient.getSingleConversation(entity.getUsername());
        if(conv!=null){
            conv.deleteAllMessage();
            list.remove(entity);
            return true;
        }
        else{
            return false;
        }
    }

    private ConversationEntity findConv(String username){
        for(ConversationEntity entity:list){
            if(entity.getUsername().equals(username)){
                return entity;
            }
        }
        return null;
    }
}
