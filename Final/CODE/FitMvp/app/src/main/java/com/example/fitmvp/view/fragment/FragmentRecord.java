package com.example.fitmvp.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseAdapter;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.bean.RecordBean;
import com.example.fitmvp.contract.RecordContract;
import com.example.fitmvp.presenter.RecordPresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.RecordDetailActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FragmentRecord extends BaseFragment<RecordPresenter> implements RecordContract.View {
    private LinearLayout progress;
    private LinearLayout emptyList;
    private RecyclerView recyclerView;
    private BaseAdapter<RecordBean> adapter;

    private List<RecordBean> records = new ArrayList<>();
    //  接收广播
    private LocalBroadcastManager broadcastManager;

    @Override
    protected Integer getLayoutId() {
        return R.layout.record_all;
    }

    @Override
    protected RecordPresenter loadPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected void initView() {
        progress = view.findViewById(R.id.record_progress);
        emptyList = view.findViewById(R.id.record_empty_list);
        recyclerView = view.findViewById(R.id.record_all_list);
        // 注册广播接收
        registerReceiver();
        // 首先隐藏列表，显示正在加载记录
        hideList();

        // 设置layout方式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        // 创建adapter实例
        adapter = new BaseAdapter<RecordBean>(records) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.record_item;
            }

            @Override
            public void convert(BaseAdapter.MyHolder holder, RecordBean data, int position) {
                holder.setText(R.id.record_title, data.getFood());
                holder.setText(R.id.record_weight, String.format(Locale.getDefault(), "%.2f", data.getWeight()));
                holder.setText(R.id.record_timestamp, data.getTimeStamp());
                holder.setText(R.id.record_cal, String.format(Locale.getDefault(), "%.2f", data.getCal()));
            }
        };

        // 设置Adapter的事件监听
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), RecordDetailActivity.class);
                // 传参
                RecordBean item = records.get(position);
                intent.putExtra("food",item.getFood());
                intent.putExtra("timeStamp", item.getTimeStamp());
                intent.putExtra("weight", String.format(Locale.getDefault(), "%.2f", item.getWeight()));
                intent.putExtra("cal", String.format(Locale.getDefault(), "%.2f", item.getCal()));
                intent.putExtra("pro", String.format(Locale.getDefault(), "%.2f", item.getProtein()));
                intent.putExtra("fat", String.format(Locale.getDefault(), "%.2f", item.getFat()));
                intent.putExtra("carbohydrate", String.format(Locale.getDefault(), "%.2f", item.getCarbohydrate()));
                startActivity(intent);
            }
            @Override
            public void onItemLongClick(View view, int position){}
        });
        // 设置adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        mPresenter.getAllRecords();
    }

    @Override
    protected void initListener() {
    }

    @Override
    public void onClick(View view) {
    }

    private void setRecords(List<RecordBean> list){
        records.clear();
        records.addAll(list);
    }

    public void updateList(List<RecordBean> list){
        setRecords(list);
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                if(records.size()>0){
                    showList();
                }
                else {
                    showEmptyList();
                }
            }
        });
    }

    private void hideList(){
        progress.setVisibility(View.VISIBLE);
        emptyList.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showList(){
        progress.setVisibility(View.GONE);
        emptyList.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void showEmptyList(){
        progress.setVisibility(View.GONE);
        emptyList.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    //注册广播接收器
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateRecords");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 更新记录时同
            if("updateRecords".equals(action)){
                LogUtils.e("receive","update all records broadcast");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        hideList();
                    }
                });
                mPresenter.getAllRecords();
            }
        }
    };

    //注销广播
    @Override
    public void onDetach() {
        super.onDetach();
        broadcastManager.unregisterReceiver(mRefreshReceiver);
    }
}
