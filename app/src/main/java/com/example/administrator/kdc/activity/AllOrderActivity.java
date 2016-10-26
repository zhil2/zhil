package com.example.administrator.kdc.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.framet.AllorderFragment;
import com.example.administrator.kdc.framet.BaseFragment;
import com.example.administrator.kdc.framet.CompleteFragment;
import com.example.administrator.kdc.framet.UnpayFragment;
import com.example.administrator.kdc.framet.UnremarkFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AllOrderActivity extends AppCompatActivity {

    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.order_fragment_tab1)
    Button orderFragmentTab1;
    @InjectView(R.id.order_fragment_tab2)
    Button orderFragmentTab2;
    @InjectView(R.id.order_fragment_tab3)
    Button orderFragmentTab3;
    @InjectView(R.id.id_linearly)
    LinearLayout idLinearly;
    @InjectView(R.id.order_fragment_viewpager)
    ViewPager orderFragmentViewpager;

    //fragment的集合
    List<BaseFragment> lists = new ArrayList<BaseFragment>();
    int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        ButterKnife.inject(this);

        initData();
    }

    public void initData() {

        lists.add(new AllorderFragment());
        lists.add(new UnpayFragment());
        lists.add(new UnremarkFragment());
        lists.add(new CompleteFragment());


        //设置viewpager显示内容
        orderFragmentViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return lists.get(position);
            }

            @Override
            public int getCount() {
                return lists.size();
            }
        });

    }
    @OnClick({R.id.order_fragment_tab1, R.id.order_fragment_tab2, R.id.order_fragment_tab3,R.id.order_fragment_tab4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.order_fragment_tab1:
                currentIndex=0;
                Log.i("aa", "onClick: "+"进入全部");
                break;
            case R.id.order_fragment_tab2:
                currentIndex=1;
                Log.i("aa", "onClick: "+"进入未付款");
                break;
            case R.id.order_fragment_tab3:
                currentIndex=2;
                Log.i("aa", "onClick: "+"进入待评价");
                break;
            case R.id.order_fragment_tab4:
                currentIndex=3;
                Log.i("aa", "onClick: "+"进入待评价");
                break;
        }
        orderFragmentViewpager.setCurrentItem(currentIndex);
    }
}
