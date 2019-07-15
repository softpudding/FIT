package com.example.FIT.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.FIT.R;
import com.example.FIT.activity.RecordDetailActivity;
import com.example.FIT.adapter.BaseAdapter;
import com.example.FIT.draw.CalorieCircle;
import com.example.FIT.item.RecordItem;

import java.util.ArrayList;
import java.util.List;


public class FragmentMainpage extends Fragment {

    // 样例数据，后续需要从数据库中获得
    private List<RecordItem> data;

    private void initData() {
        RecordItem item1 = new RecordItem();
        item1.setImage(R.drawable.item_friends);
        item1.setTitle("草莓");
        item1.setText("......");

        RecordItem item2 = new RecordItem();
        item2.setImage(R.drawable.item_home);
        item2.setTitle("饼干");
        item2.setText("......");

        data = new ArrayList<>();
        data.add(item1);
        data.add(item2);
        data.add(item1);
        data.add(item2);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mainpage, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rcyc_record);

        CalorieCircle calorieCircle = view.findViewById(R.id.calCircle);
        // 设置热量值，需要根据个人设置来设定
        calorieCircle.setCurProgress(1500);
        calorieCircle.setTargetProgress(2000);
        // 设置layout方式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        // 初始化data（item列表）
        initData();
        // 创建adapter实例
        BaseAdapter<RecordItem> adapter = new BaseAdapter<RecordItem>(data) {
            @Override
            public int getLayoutId(int viewType) {
                return R.layout.item_record;
            }

            @Override
            public void convert(BaseAdapter.MyHolder holder, RecordItem data, int position) {
                holder.setText(R.id.record_text, data.getText());
                holder.setText(R.id.record_title, data.getTitle());
                holder.setImage(R.id.record_img, data.getImage());
                holder.setHight(380);
            }
        };

        // 设置Adapter的事件监听
        adapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), RecordDetailActivity.class);
                // 传参
                RecordItem item = data.get(position);
                intent.putExtra("title", item.getTitle());
                intent.putExtra("text", item.getText());
                // 传项目中图片
                intent.putExtra("image", item.getImage());
                startActivity(intent);
            }
        });
        // 设置adapter
        recyclerView.setAdapter(adapter);


        return view;
    }
}
