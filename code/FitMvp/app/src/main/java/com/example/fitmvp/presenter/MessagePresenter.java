package com.example.fitmvp.presenter;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.bean.ConversationEntity;
import com.example.fitmvp.contract.MessageContract;
import com.example.fitmvp.model.MessageModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.fragment.friends.FragmentMsg;
import com.nostra13.universalimageloader.utils.L;

import java.util.HashMap;
import java.util.List;

public class MessagePresenter extends BasePresenter<FragmentMsg>
        implements MessageContract.Presenter {
    private MessageModel messageModel = new MessageModel();

    @Override
    public List<ConversationEntity> getConvList(){
        List<ConversationEntity> list = messageModel.getConvList();
        return list;
    }

    @Override
    public void deleteConv(ConversationEntity entity){
        if(messageModel.deleteMsg(entity)){
            getIView().updateData();
        }
    }
}
