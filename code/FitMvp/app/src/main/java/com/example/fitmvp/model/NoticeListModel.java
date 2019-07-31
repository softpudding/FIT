package com.example.fitmvp.model;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.ConversationEntity;
import com.example.fitmvp.bean.NoticeBean;
import com.example.fitmvp.contract.NoticeListContract;
import com.example.fitmvp.exception.ApiException;
import com.example.fitmvp.observer.CommonObserver;
import com.example.fitmvp.transformer.ThreadTransformer;
import com.example.fitmvp.utils.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoticeListModel extends BaseModel implements NoticeListContract.Model {
    @Override
    public void getNotice(final Callback callback) {
       httpService1.getNotice()
               .compose(new ThreadTransformer<List<NoticeBean>>())
               .subscribe(new CommonObserver<List<NoticeBean>>() {
                   @Override
                   public void onNext(List<NoticeBean> list) {
                        // 按时间降序
                       Collections.sort(list, new Comparator<NoticeBean>() {
                           @Override
                           public int compare(NoticeBean t1, NoticeBean t2) {
                               long time1 = t1.getRawTime();
                               long time2 = t2.getRawTime();
                               long diff = time1 - time2;
                               if (diff > 0) {
                                   return -1;
                               }
                               else if (diff < 0) {
                                   return 1;
                               }
                               return 0;
                           }
                       });
                       callback.success(list);
                   }

                   @Override
                   protected void onError(ApiException e) {
                        callback.fail();
                   }
               });

    }
}
