package com.example.fitmvp.model;

import com.example.fitmvp.base.BaseModel;
import com.example.fitmvp.bean.ConversationEntity;
import com.example.fitmvp.bean.NoticeBean;
import com.example.fitmvp.contract.NoticeListContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NoticeListModel extends BaseModel implements NoticeListContract.Model {
    @Override
    public void getNotice(Callback callback) {
        List<NoticeBean> list = new ArrayList<>();
        NoticeBean notice = new NoticeBean();
        notice.setTittle("000");
        notice.setTime_stamp("2019-07-31 11:03:05");
        notice.setNews("news");
        list.add(notice);
        NoticeBean noticeBean = new NoticeBean();
        noticeBean.setTittle("啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
        noticeBean.setTime_stamp("2019-07-31 12:03:05");
        noticeBean.setNews("news news................................................................................");
        list.add(noticeBean);
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
}
