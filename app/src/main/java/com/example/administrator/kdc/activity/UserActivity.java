package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;

import com.example.administrator.kdc.vo.User_tbl;


import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Allen on 2016/10/26.
 */
public class UserActivity extends AppCompatActivity {
    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.btn_update)
    Button btnUpdate;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.tv_zhanghao)
    TextView tvZhanghao;
    @InjectView(R.id.tv_real_zhanghao)
    TextView tvRealZhanghao;
    @InjectView(R.id.rl_edit_zhanghao)
    RelativeLayout rlEditZhanghao;
    @InjectView(R.id.view2)
    View view2;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_real_name)
    TextView tvRealName;
    @InjectView(R.id.rl_edit_name)
    RelativeLayout rlEditName;
    @InjectView(R.id.view3)
    View view3;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.tv_real_sex)
    TextView tvRealSex;
    @InjectView(R.id.rl_edit_sex)
    RelativeLayout rlEditSex;
    @InjectView(R.id.view4)
    View view4;
    @InjectView(R.id.tv_age)
    TextView tvAge;
    @InjectView(R.id.tv_real_age)
    TextView tvRealAge;
    @InjectView(R.id.rl_edit_age)
    RelativeLayout rlEditAge;
    @InjectView(R.id.view5)
    View view5;
    @InjectView(R.id.tv_date)
    TextView tvDate;
    @InjectView(R.id.tv_real_date)
    TextView tvRealDate;
    @InjectView(R.id.rl_edit_date)
    RelativeLayout rlEditDate;
    @InjectView(R.id.view6)
    View view6;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_real_address)
    TextView tvRealAddress;
    @InjectView(R.id.rl_edit_address)
    RelativeLayout rlEditAddress;
    @InjectView(R.id.view7)
    View view7;
    @InjectView(R.id.tv_call)
    TextView tvCall;
    @InjectView(R.id.tv_real_call)
    TextView tvRealCall;
    @InjectView(R.id.rl_edit_call)
    RelativeLayout rlEditCall;
    @InjectView(R.id.view8)
    View view8;
    @InjectView(R.id.tv_youxiang)
    TextView tvYouxiang;
    @InjectView(R.id.tv_real_youxiang)
    TextView tvRealYouxiang;
    @InjectView(R.id.rl_edit_youxiang)
    RelativeLayout rlEditYouxiang;
    User_tbl user_tbl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        getData();
    }
    public void getData(){

        Log.i("UserActivity", "getData: "+"进入");
                tvRealZhanghao.setText(((MyApplication) getApplication()).getUser().getUser_number()+"");
                tvRealName.setText(((MyApplication) getApplication()).getUsershow().getUsershow_name()+"");
                tvRealSex.setText(((MyApplication) getApplication()).getUsershow().getUsershow_sex()+"");
                tvRealAge.setText(((MyApplication) getApplication()).getUsershow().getUsershow_age()+"");
                tvRealDate.setText(((MyApplication) getApplication()).getUsershow().getUsershow_birthday()+"");
                tvRealAddress.setText(((MyApplication) getApplication()).getUsershow().getAddress_id()+"");

                tvRealCall.setText(((MyApplication) getApplication()).getUser().getUser_number()+"");
                tvRealYouxiang.setText(((MyApplication) getApplication()).getUser().getUser_emal()+"");


    }

    @OnClick({R.id.btn_back, R.id.btn_update})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent=new Intent(this,HomeActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_update:
               /* Intent intent2=new Intent(this,UserUpadateActivity.class);
                startActivity(intent2);*/
                break;
        }
    }
}
