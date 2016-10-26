package com.example.administrator.kdc.framet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.AllOrderActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/18.
 */
public class PersonFragment extends BaseFragment {


    @InjectView(R.id.imb_user)
    ImageButton imbUser;
    @InjectView(R.id.tv_user)
    TextView tvUser;
    @InjectView(R.id.textView21)
    TextView textView21;
    @InjectView(R.id.textView22)
    TextView textView22;
    @InjectView(R.id.person_center_ll_header)
    RelativeLayout personCenterLlHeader;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.tv_personinfo)
    TextView tvPersoninfo;
    @InjectView(R.id.rl1)
    RelativeLayout rl1;
    @InjectView(R.id.imageView6)
    ImageView imageView6;
    @InjectView(R.id.tv_yuyue)
    TextView tvYuyue;
    @InjectView(R.id.rl2)
    RelativeLayout rl2;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.tv_zhaoji)
    TextView tvZhaoji;
    @InjectView(R.id.rl3)
    RelativeLayout rl3;
    @InjectView(R.id.imageView8)
    ImageView imageView8;
    @InjectView(R.id.tv_qiandao)
    TextView tvQiandao;
    @InjectView(R.id.rl4)
    RelativeLayout rl4;
    @InjectView(R.id.imageView9)
    ImageView imageView9;
    @InjectView(R.id.tv_shezhi)
    TextView tvShezhi;
    @InjectView(R.id.rl5)
    RelativeLayout rl5;
    @InjectView(R.id.imageView10)
    ImageView imageView10;
    @InjectView(R.id.tv_tuichu)
    TextView tvTuichu;
    @InjectView(R.id.rl6)
    RelativeLayout rl6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person_center, null);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.imb_user, R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4, R.id.rl5, R.id.rl6})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imb_user:
                break;
            case R.id.rl1:

                break;
            case R.id.rl2:
                Intent intent=new Intent(getActivity(), AllOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.rl3:
                break;
            case R.id.rl4:
                break;
            case R.id.rl5:
                break;
            case R.id.rl6:
                break;
        }
    }
}