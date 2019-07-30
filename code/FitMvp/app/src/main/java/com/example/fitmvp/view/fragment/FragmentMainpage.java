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
import com.example.fitmvp.contract.MainPageContract;
import com.example.fitmvp.presenter.MainPagePresenter;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.view.activity.PhotoType;
import com.example.fitmvp.view.activity.RecordDetailActivity;
import com.example.fitmvp.view.draw.CalorieCircle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FragmentMainpage extends BaseFragment<MainPagePresenter> implements MainPageContract.View {
    private LinearLayout progress;
    private LinearLayout progress_circle;
    private LinearLayout empty_record;
    private LinearLayout zero_cal;
    private RecyclerView recyclerView;
    private CalorieCircle calorieCircle;
    private FloatingActionButton takePhoto;
    private BaseAdapter<RecordBean> adapter;
    // 样例数据，后续需要从数据库中获得
    private List<RecordBean> records = new ArrayList<>();
    // 接收广播
    private LocalBroadcastManager broadcastManager;

    @Override
    protected void initListener() {
        takePhoto.setOnClickListener(this);
    }

    @Override
    protected Integer getLayoutId() {
        return R.layout.mainpage;
    }

    @Override
    protected MainPagePresenter loadPresenter() {
        return new MainPagePresenter();
    }

    @Override
    protected void initView() {
        progress = view.findViewById(R.id.progress);
        progress_circle = view.findViewById(R.id.progress_circle);
        empty_record = view.findViewById(R.id.empty_record);
        zero_cal = view.findViewById(R.id.zero_cal);
        recyclerView = view.findViewById(R.id.rcyc_record);
        calorieCircle = view.findViewById(R.id.calCircle);

        takePhoto = view.findViewById(R.id.takePhoto);

        //注册刷新Fragment数据的方法
        registerReceiver();

        // 初始时显示正在加载数据，环形统计图和数据列表不可见
        hideCircle();
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
        // 获取热量记录
        mPresenter.getCalValue();
        // 获取最新五条识别记录
        mPresenter.getList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.takePhoto:
                Intent intent = new Intent(getActivity(), PhotoType.class);
                startActivity(intent);
                break;
        }
    }

    public void setCircleValue(final double target, final double current){
        new Handler().post(new Runnable() {
            public void run() {
                calorieCircle.setCurProgress(current);
                calorieCircle.setTargetProgress(target);
                if(current==0){
                    showZeroCircle();
                }
                else{
                    showCircle();
                }
            }
        });
    }

    private void setList(List<RecordBean> list){
        // 重置记录
        records.clear();
        records.addAll(list);
    }

    public void updateList(List<RecordBean> list){
        setList(list);
        new Handler().post(new Runnable() {
            public void run() {
                // 有记录，显示记录
                if(records.size()>0){
                    showList();
                }
                // 无记录，显示记录为空的提示
                else{
                    showEmptyList();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void showCircle(){
        calorieCircle.setVisibility(View.VISIBLE);
        progress_circle.setVisibility(View.GONE);
        zero_cal.setVisibility(View.GONE);
    }

    private void hideCircle(){
        calorieCircle.setVisibility(View.GONE);
        progress_circle.setVisibility(View.VISIBLE);
        zero_cal.setVisibility(View.GONE);
    }

    private void showZeroCircle(){
        calorieCircle.setVisibility(View.GONE);
        progress_circle.setVisibility(View.GONE);
        zero_cal.setVisibility(View.VISIBLE);
    }

    private void showList(){
        recyclerView.setVisibility(View.VISIBLE);
        empty_record.setVisibility(View.GONE);
        progress.setVisibility(View.GONE);
    }

    private void hideList(){
        recyclerView.setVisibility(View.GONE);
        empty_record.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
    }

    private void showEmptyList(){
        recyclerView.setVisibility(View.GONE);
        empty_record.setVisibility(View.VISIBLE);
        progress.setVisibility(View.GONE);
    }

    //注册广播接收器
    private void registerReceiver() {
        broadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("updateRecords");
        intentFilter.addAction("updateCal");
        broadcastManager.registerReceiver(mRefreshReceiver, intentFilter);
    }

    private BroadcastReceiver mRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // 更新记录时同时更新当日能量记录环形图
            if("updateRecords".equals(action)){
                LogUtils.e("receive","update records broadcast");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        hideList();
                        hideCircle();
                    }
                });
                mPresenter.getList();
                mPresenter.getCalValue();
            }
            // 仅更新当日能量记录环形图，修改了身高、体重和性别信息时更新
            else if("updateCal".equals(action)){
                LogUtils.e("receive","update cal broadcast");
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        hideCircle();
                    }
                });
                mPresenter.getCalValue();
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
