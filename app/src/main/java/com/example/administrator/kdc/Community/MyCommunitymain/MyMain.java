package com.example.administrator.kdc.Community.MyCommunitymain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.administrator.kdc.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MyMain extends AppCompatActivity {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    ViewPager vp;
    RadioGroup radioGroup;
    static MyAllPost_Fragment myallPost_fragment = new MyAllPost_Fragment();
    static MyAllCommunity_Fragment myAllCommunity_fragment = new MyAllCommunity_Fragment();
    //public int user_id= ((MyApplication) getApplication()).getUser().getUser_id();
    public static int user_id;
    @InjectView(R.id.return_community_mainlayout)
    ImageView returnCommunityMainlayout;
    @InjectView(R.id.comunitytop)
    LinearLayout comunitytop;
    @InjectView(R.id.rel1)
    LinearLayout rel1;

    //标识前一次点击的按钮的index
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymain);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        user_id=intent.getIntExtra("user_id",0);
        vp = (ViewPager) findViewById(R.id.ViewPager_community);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        fragmentList.add(myallPost_fragment);
        fragmentList.add(myAllCommunity_fragment);
        vp.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager()));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //改变radiobutton的选中状态
                RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                radioButton.setChecked(true);//设置radiobutton选中

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int currentIndex = -1;//viewpager显式项的位置
                switch (checkedId) {
                    case R.id.btn_allpost:
                        currentIndex = 0;
                        break;
                    case R.id.btn_activitypost:
                        currentIndex = 1;
                        break;
                }
                vp.setCurrentItem(currentIndex);
            }
        });
    }

    @OnClick(R.id.return_community_mainlayout)//返回社区主界面
    public void onClick() {
        finish();
    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        public MyFragmentPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);

        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

}

