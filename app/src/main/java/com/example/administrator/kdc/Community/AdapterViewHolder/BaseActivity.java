package com.example.administrator.kdc.Community.AdapterViewHolder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by kskjf on 2016/10/5.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//      initData();
        getData();
        initEvent();
//      loadData();
//      setListAdapter();
    }
    public abstract  void initView();//找控件
    public abstract  void initEvent();//设置控件的事件
    public abstract  void getData();//设置界面初始值
//    public abstract void loadData();
//    public abstract  void setListAdapter();
}
