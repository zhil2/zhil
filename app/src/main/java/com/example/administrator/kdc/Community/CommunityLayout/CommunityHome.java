package com.example.administrator.kdc.Community.CommunityLayout;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Community.CommunityGetPicture.FileUtils;
import com.example.administrator.kdc.Community.CommunityMainLayout.CommunityMain;
import com.example.administrator.kdc.Community.CommunitySendPost.C_send_Post_Activity;
import com.example.administrator.kdc.Community.MyCommunitymain.MyMain;
import com.example.administrator.kdc.Community.Search.Community_Post_SearchActivity;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.vo.Community_tbl;
import com.example.administrator.kdc.vo.Focus_tbl;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CommunityHome extends AppCompatActivity {

    @InjectView(R.id.return_community_mainlayout)
    ImageView returnCommunityMainlayout;
    @InjectView(R.id.tv_comunityname)
    TextView tvComunityname;
    @InjectView(R.id.btn_fucksc)
    public TextView btnFucksc;
    @InjectView(R.id.sendpost_tocommunity)
    Button sendpostTocommunity;
    List<Fragment> fragmentList = new ArrayList<Fragment>();
    ViewPager vp;
    RadioGroup radioGroup;
    @InjectView(R.id.comunitytop)
    LinearLayout comunitytop;
    @InjectView(R.id.radioGroup1)
    LinearLayout radioGroup1;
    @InjectView(R.id.rel1)
    LinearLayout rel1;
    @InjectView(R.id.btn_allpost)
    RadioButton btnAllpost;
    @InjectView(R.id.btn_activitypost)
    RadioButton btnActivitypost;
    @InjectView(R.id.btn_sharepost)
    RadioButton btnSharepost;
    @InjectView(R.id.btn_teachpost)
    RadioButton btnTeachpost;
    @InjectView(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @InjectView(R.id.ViewPager_community)
    ViewPager ViewPagerCommunity;
    @InjectView(R.id.main_bottom1)
    RelativeLayout mainBottom1;
    static AllPost_Fragment allPost_fragment = new AllPost_Fragment();
    static ActivityPost_Fragment activityPost_fragment = new ActivityPost_Fragment();
    static SharePost_Fragment sharePost_fragment = new SharePost_Fragment();
    static Teach_Fragment teach_fragment = new Teach_Fragment();
    public static int community_id;
    public List<String> popup = new ArrayList<>();
    @InjectView(R.id.iv_mycommunity)
    ImageView ivMycommunity;
    public String communityname;
    public static String focus=null;
    public Focus_tbl focus_tbl;
    public static int focusstype;//关注状态
    public static String focusname;//关注状态名
    int currentIndex;
    //标识前一次点击的按钮的index
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_home);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        //传值详情
        Community_tbl community_tbl =intent.getParcelableExtra("community_tbl");
        Log.i("community_tb21l","onCreate11111111112222222: "+community_tbl);
        tvComunityname.setText(community_tbl.getCommunity_name());
        communityname=community_tbl.getCommunity_name();
        Log.i("community_tb21l", "onCreate1:tvComunityname "+tvComunityname);
        community_id=community_tbl.getCommunity_id();
        Log.i("community_tb21l", "onCreate2:community_id "+community_tbl.getCommunity_id());
        vp = (ViewPager) findViewById(R.id.ViewPager_community);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup2);
        //fragment对象
        fragmentList.add(allPost_fragment);
        fragmentList.add(activityPost_fragment);
        fragmentList.add(sharePost_fragment);
        fragmentList.add(teach_fragment);
        initData();
        getOne();
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
            public void onCheckedChanged(RadioGroup group, int checkedId) {//点击事件

                currentIndex = -1;//viewpager显式项的位置
                switch (checkedId) {
                    case R.id.btn_allpost:
                        currentIndex = 0;
                        break;
                    case R.id.btn_activitypost:
                        currentIndex = 1;
                        break;
                    case R.id.btn_sharepost:
                        currentIndex = 2;
                        break;
                    case R.id.btn_teachpost:
                        currentIndex = 3;
                        break;
                }
                vp.setCurrentItem(currentIndex);
            }
        });
        btnFucksc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (focus==null){
                    addData();
                    focusname="关注";
                }
                else if (focusstype==0) {
                    //btnFucksc.setText("已关注");//点击内容改变  关注->已关注
                    update(1);
                    focusname="关注";

                }else if (focusstype==1){
                    //btnFucksc.setText("关注");//点击内容改变  已关注->关注
                    update(0);
                    focusname="取消关注";
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    int posttype=data.getIntExtra("posttype",0);
                    currentIndex=posttype;
                    Log.i("onActivityResult", "onActivityResult:0 "+currentIndex);
                }
                break;
            default:
        }
    }

    public void initData() {
        popup.add("我的帖子");
        popup.add("我的社区");
    }

    public void initPopupWindow(View v) {//右上角按钮
        View view = LayoutInflater.from(this).inflate(R.layout.mymain_listview, null);
        final PopupWindow popupWindow = new PopupWindow(view, 300, 150);//设置popup窗口//宽、高
        ListView listview = (ListView) view.findViewById(R.id.mymain_listview);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.mymain_listviewitem, popup);
        listview.setAdapter(arrayAdapter);
        //点击空白处消失
        popupWindow.setOutsideTouchable(true);
        Resources resources = getBaseContext().getResources();
        Drawable d = resources.getDrawable(android.R.color.white);
        popupWindow.setBackgroundDrawable(d);
        //popupWindow.setD
        //在view之下
        popupWindow.showAsDropDown(v);//综合排序窗口
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                popupWindow.dismiss();
                if (position == 0) {//跳转到我的社区界面：我的帖子、关注的社区
                    Intent intent = new Intent(CommunityHome.this, MyMain.class);
                    intent.putExtra("user_id", ((MyApplication) CommunityHome.this.getApplication()).getUser().getUser_id());
                    startActivity(intent);
                }
                else if (position == 1) {//跳转到我的社区界面：我的帖子、关注的社区
                    Intent intent = new Intent(CommunityHome.this, MyMain.class);
                    intent.putExtra("user_id", ((MyApplication) CommunityHome.this.getApplication()).getUser().getUser_id());
                    startActivity(intent);
                }
            }
        });
    }
    public void addData(){
        //public void addFocus(int foucs_type,int exceptional_object,int user_id,int focus_stype);
        RequestParams requestParams = new RequestParams(URL.url + "Focus_tbl_add_Servlet");
        Log.i(" post_picture111111", "onClick:1111111111111 "+ 1111);
        requestParams.addQueryStringParameter("focus_type", 2+"");//类型
        requestParams.addQueryStringParameter("exceptional_object", community_id+"");//对象id
        requestParams.addQueryStringParameter("user_id", ((MyApplication) CommunityHome.this.getApplication()).getUser().getUser_id()+"");
        requestParams.addQueryStringParameter("focus_stype", 1+"");//状态
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("Post_tbl_Insert_Servlet", "onSuccess: get" + result);
                getOne();

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
    public void update(int focus_stype){
        ////public void update(int exceptional_object,int foucs_type,int user_id,int focus_stype);
        RequestParams requestParams = new RequestParams(URL.url + "Focus_tbl_update_Servlet");
        Log.i(" post_picture111111", "onClick:1111111111111 "+ 222222);
        requestParams.addQueryStringParameter("focus_type", 2+"");//类型
        requestParams.addQueryStringParameter("exceptional_object", community_id+"");//对象id
        requestParams.addQueryStringParameter("user_id", ((MyApplication) CommunityHome.this.getApplication()).getUser().getUser_id()+"");
        requestParams.addQueryStringParameter("focus_stype", focus_stype+"");//状态
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("Post_tbl_Insert_Servlet", "onSuccess: get" + result);
                getOne();

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }
    public void getOne(){
        //public Focus_tbl getFocusdata(int exceptional_object,int foucs_type,int user_id);
        RequestParams requestParams = new RequestParams(URL.url + "Focus_tbl_getOne_Servlet");
        Log.i(" post_picture111111", "onClick:1111111111111 "+ 33333);
        requestParams.addQueryStringParameter("focus_type", 2+"");//类型
        requestParams.addQueryStringParameter("exceptional_object", community_id+"");//对象id
        requestParams.addQueryStringParameter("user_id", ((MyApplication) CommunityHome.this.getApplication()).getUser().getUser_id()+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("Post_tbl_Insert_Servlet", "onSuccess: get" + result);
                Gson gson=new Gson();
                focus_tbl=gson.fromJson(result,Focus_tbl.class);
                if (focus_tbl!=null) {
                    focus = "关注";
                    focusstype = focus_tbl.getFocus_stype();
                    if (focusstype == 0) {
                        btnFucksc.setText("关注");
                    } else if (focusstype == 1) {
                        btnFucksc.setText("取消关注");
                    }
                    Toast.makeText(getApplicationContext(), "你"+focusname+"了"+communityname, Toast.LENGTH_SHORT).show();
                }


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
            }
            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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

    @OnClick({R.id.return_community_mainlayout, R.id.btn_searchpost1, R.id.sendpost_tocommunity, R.id.iv_mycommunity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_community_mainlayout://返回上一级
                Intent intent2 = new Intent(this, CommunityMain.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.btn_searchpost1://搜索帖子
                Intent intent3 = new Intent(this, Community_Post_SearchActivity.class);
                startActivity(intent3);
                break;
            case R.id.sendpost_tocommunity://发帖
                Intent intent1 = new Intent(this, C_send_Post_Activity.class);
                intent1.putExtra("community_id",community_id);
                startActivityForResult(intent1,1);
                break;
            case R.id.iv_mycommunity:
                initPopupWindow(view);
                break;
        }
    }
}

