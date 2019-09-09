package com.example.fitmvp.contract;

import com.example.fitmvp.bean.ConversationEntity;

import java.util.List;

import cn.jpush.im.android.api.model.Conversation;

public interface MessageContract {
    interface Model {
    }

    interface View {
    }

    interface Presenter {
        List<ConversationEntity> getConvList();
        void deleteConv(ConversationEntity entity);
    }
}
