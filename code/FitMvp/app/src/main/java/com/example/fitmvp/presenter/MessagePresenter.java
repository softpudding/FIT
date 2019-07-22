package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.ConversationEntity;
import com.example.fitmvp.contract.MessageContract;
import com.example.fitmvp.model.MessageModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.fragment.friends.FragmentMsg;

import java.util.HashMap;
import java.util.List;

public class MessagePresenter extends BasePresenter<FragmentMsg>
        implements MessageContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new MessageModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("message", models[0]);
        return map;
    }

    @Override
    public List<ConversationEntity> getConvList(){
        MessageModel messageModel = (MessageModel) getiModelMap().get("message");
        List<ConversationEntity> list = messageModel.getConvList();
        return list;
    }
}
