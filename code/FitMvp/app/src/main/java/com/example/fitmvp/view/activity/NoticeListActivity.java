package com.example.fitmvp.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.ActionBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.bean.NoticeBean;
import com.example.fitmvp.contract.NoticeListContract;
import com.example.fitmvp.presenter.NoticeListPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NoticeListActivity extends BaseActivity<NoticeListPresenter> implements NoticeListContract.View {
    @Bind(R.id.notice_list)
    RecyclerView recyclerView;
    @Bind(R.id.notice_progress)
    LinearLayout progress;

    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    private BaseAdapter<NoticeBean> adapter;
    private List<NoticeBean> notice = new ArrayList<>();

    @Override
    protected void setBar() {
        // 返回键
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("公告");
    }

    @Override
    protected NoticeListPresenter loadPresenter() {
        return new NoticeListPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    public void onStart(){
        super.onStart();
        mPresenter.getNotice();
    }

    @Override
    protected void initListener() {
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NoticeBean noticeBean = notice.get(position);
                Intent intent = new Intent();
                intent.setClass(NoticeListActivity.this, NoticeDetailActivity.class);
                intent.putExtra("title",noticeBean.getTittle());
                intent.putExtra("time",noticeBean.getTime_stamp());
                intent.putExtra("msg",noticeBean.getNews());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        hideList();
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new BaseAdapter<NoticeBean>(notice) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.notice_item;
            }

            @Override
            public void convert(MyHolder holder, NoticeBean data, int position) {
                if(data.getTittle().length()<=10){
                    holder.setText(R.id.notice_title, data.getTittle());
                }
                else {
                    String title = data.getTittle().substring(0,9)+"...";
                    holder.setText(R.id.notice_title,title);
                }
                holder.setText(R.id.notice_time, data.getTime_stamp());
            }
        };
    }

    @Override
    protected int getLayoutId() {
        return R.layout.notice_list;
    }

    @Override
    protected void otherViewClick(View view) {

    }

    public void setNotice(List<NoticeBean> list){
        notice.clear();
        notice.addAll(list);
    }

    private void hideList(){
        progress.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    public void showList(){
        adapter.notifyDataSetChanged();
        progress.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
