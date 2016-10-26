package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.framet.BaseFragment;
import com.example.administrator.kdc.framet.MusterFragement1;
import com.example.administrator.kdc.framet.MusterFragement2;
import com.example.administrator.kdc.framet.MusterFragement3;
import com.example.administrator.kdc.framet.MusterFragement4;
import com.example.administrator.kdc.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MusterlistActivity extends AppCompatActivity {

    @InjectView(R.id.all_order_goback)
    ImageView allOrderGoback;
    @InjectView(R.id.order_fragment_tab1)
    Button orderFragmentTab1;
    @InjectView(R.id.order_fragment_tab2)
    Button orderFragmentTab2;
    @InjectView(R.id.order_fragment_tab3)
    Button orderFragmentTab3;
    @InjectView(R.id.order_fragment_tab4)
    Button orderFragmentTab4;

    @InjectView(R.id.id_linearly)
    LinearLayout idLinearly;
    @InjectView(R.id.order_line_tab)
    ImageView orderLineTab;

    @InjectView(R.id.order_fragment_viewpager)
    ViewPager orderFragmentViewpager;

    int flag = 0;
    int user_id;
    int venues_id;

    List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musterlist);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        String a=intent.getStringExtra("venues_id");
        venues_id= Integer.parseInt( a );
        user_id =((MyApplication) getApplication()).getUser().getUser_id();

        if(venues_id!=0){

            color(1);
            orderFragmentViewpager.setOffscreenPageLimit(2);
            orderFragmentViewpager.setCurrentItem(1);

        }
        Log.d("agfhjyir","list  venues_id"+venues_id+"    a"+a);
        //将场馆id发给碎片2


        initView();

        Bundle bundle = new Bundle();
        bundle.putString("venues_id",a);

        fragmentList.get(1).setArguments(bundle);

    }

    public void initView() {
        fragmentList.add(new MusterFragement1());//全部
        fragmentList.add(new MusterFragement2());//待支付
        fragmentList.add(new MusterFragement3());//待接收
        fragmentList.add(new MusterFragement4());//待验证

        orderFragmentViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Log.d("jj","position"+position);
                return fragmentList.get(position);
            }
            @Override
            public int getCount() {
                orderFragmentViewpager.getCurrentItem();
                color(orderFragmentViewpager.getCurrentItem());
                return fragmentList.size();
            }
        });
    }

    @OnClick({R.id.order_fragment_tab1, R.id.order_fragment_tab2, R.id.order_fragment_tab3, R.id.order_fragment_tab4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_fragment_tab1:
                flag = 0;
                break;
            case R.id.order_fragment_tab2:
                flag = 1;

                break;
            case R.id.order_fragment_tab3:
                flag = 2;

                break;
            case R.id.order_fragment_tab4:
                flag = 3;

                break;
        }
        color(flag);
        orderFragmentViewpager.setOffscreenPageLimit(1);
        orderFragmentViewpager.setCurrentItem(flag);
    }


    public void color( int flag) {
        orderFragmentTab1.setBackgroundColor(0xffffffff);
        orderFragmentTab2.setBackgroundColor(0xffffffff);
        orderFragmentTab3.setBackgroundColor(0xffffffff);
        orderFragmentTab4.setBackgroundColor(0xffffffff);

        switch (flag) {
            case 0:
                orderFragmentTab1.setBackgroundColor(0xffBDBDBD);
                break;
            case 1:
                orderFragmentTab2.setBackgroundColor(0xffBDBDBD);
                break;
            case 2:
                orderFragmentTab3.setBackgroundColor(0xffBDBDBD);
                break;
            case 3:
                orderFragmentTab4.setBackgroundColor(0xffBDBDBD);
                break;
        }

    }


}
