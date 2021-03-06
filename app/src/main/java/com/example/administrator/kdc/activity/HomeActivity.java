package com.example.administrator.kdc.activity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Community.CommunityMainLayout.CommunityMain;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.db.Mydb;
import com.example.administrator.kdc.framet.Fragement1;
import com.example.administrator.kdc.framet.Fragement2;
import com.example.administrator.kdc.framet.ListiFragment;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Sign_tbl;
import com.example.administrator.kdc.vo.User_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.igexin.sdk.PushManager;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ContactNotificationMessage;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    //ViewPager
    ViewPager vp;
    RadioGroup radioGroup;
    @InjectView(R.id.button5)
    RadioButton button5;
    @InjectView(R.id.button6)
    RadioButton button6;
    @InjectView(R.id.button7)
    RadioButton button7;
    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.btn_basketball)
    ImageButton btnBasketball;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.linearLayout3)
    RadioGroup linearLayout3;
    @InjectView(R.id.viewpager)
    ViewPager viewpager;
    int user_id;
    int currentIndex;
    View headView;
    ImageView ivHeader;
    TextView tvHeader;
    String userHang=null;
    private Mydb dbHelpr;
    ImageLoader myImageLoader;
    int tc=0;
    String token;
    private List<Usershow_tbl> userIdList;
    Fragement2 fragement2;
    android.os.Handler handler=new android.os.Handler();

    @Override
    protected void onActivityResult(int rqquestCode,int resultCode,Intent data){
        Log.d("dfshgfkhk","回调了");
        if(resultCode==RESULT_OK){
            vp.setCurrentItem(1);
        }
        if (resultCode==2){
            fragement2.getOrderData();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //推送启动
        PushManager.getInstance().initialize(this.getApplicationContext());

        //建立数据库
        dbHelpr=new Mydb(this,"kdc.db",null,1);
        dbHelpr.getWritableDatabase();
        //从库里取出用户表，并赋值给MyApplication

        SQLiteDatabase db=dbHelpr.getWritableDatabase();
        Cursor cursor=db.query("user_tbl",null,null,null,null,null,null);
        User_tbl users=null;
        if(cursor.moveToNext()){
            do{
                users=new User_tbl(cursor.getInt(cursor.getColumnIndex("id")),cursor.getInt(cursor.getColumnIndex("user_number")),cursor.getString(cursor.getColumnIndex("user_pwd")),cursor.getString(cursor.getColumnIndex("user_emal")));
                ((MyApplication) getApplication()).setUser(users);
            }while (cursor.moveToNext());
        }

        cursor=db.query("usershow_tbl",null,null,null,null,null,null);

        if(cursor.moveToNext()){
            do{
                int usershow_id=cursor.getInt(cursor.getColumnIndex("id"));
                String usershow_name=cursor.getString(cursor.getColumnIndex("usershow_name"));

                String usershow_sex=cursor.getString(cursor.getColumnIndex("usershow_sex"));
                int usershow_age=cursor.getInt(cursor.getColumnIndex("usershow_age"));

                String usershow_birthday=cursor.getString(cursor.getColumnIndex("usershow_birthday"));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                Timestamp a = null;
                long millionSeconds;
                try {
                     millionSeconds = sdf.parse(usershow_birthday).getTime();
                     a=new Timestamp(millionSeconds);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String usershow_head=cursor.getString(cursor.getColumnIndex("usershow_head"));
                int usershow_credit=cursor.getInt(cursor.getColumnIndex("usershow_credit"));
                Double usershow_money=cursor.getDouble(cursor.getColumnIndex("usershow_money"));
                int address_id=cursor.getInt(cursor.getColumnIndex("address_id"));

                Usershow_tbl usershows=new Usershow_tbl(address_id,users,usershow_name,usershow_sex,usershow_age,a,usershow_head,usershow_credit,usershow_money,usershow_id);
                ((MyApplication) getApplication()).setUsershow(usershows);

            }while (cursor.moveToNext());
        }

        cursor=db.query("user_key_tbl",null,null,null,null,null,null);
        if(cursor.moveToNext()){
            do{
                String key=cursor.getString(cursor.getColumnIndex("user_key"));
                ((MyApplication) getApplication()).setToken(key);
            }while (cursor.moveToNext());
        }
        //用户信息提供者
        initUserInfo();
        //消息接收监听
        initData();
        //连接融云服务器
        token=((MyApplication) getApplication()).getToken();
        connectRongServer(token);
        user_id= ((MyApplication) getApplication()).getUser().getUser_id();

        //设置toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //设置抽屉DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        // 添加抽屉按钮，通过点击按钮实现打开和关闭功能; 如果不想要抽屉按钮，只允许在侧边边界拉出侧边栏，可以不写此行代码
        toggle.syncState();
        // 设置按钮的动画效果; 如果不想要打开关闭抽屉时的箭头动画效果，可以不写此行代码

        drawer.setDrawerListener(toggle);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);//设置菜单图标恢复本来的颜色

        //获取navigationView的头部布局（headerLayout）
        headView = navigationView.getHeaderView(0);
        //获取headerLayout布局中的控件
        //头像赋值
        ivHeader = (ImageView) headView.findViewById(R.id.im_user);
        if (user_id!=0){
            String url2=((MyApplication) getApplication()).getUsershow().getUsershow_head();
            myImageLoader = new ImageLoader(this);
            myImageLoader.showImageByUrl(url2, ivHeader);
        }else{
            ivHeader.setImageResource(R.drawable.tx);
        }


        //文字控件
        tvHeader = (TextView) headView.findViewById(R.id.tv_user);

        if (user_id!=0){

        tvHeader.setText("欢迎您："+((MyApplication) getApplication()).getUsershow().getUsershow_name());
        }
        else{
            tvHeader.setText("您还未登录,请点击登录");
        }


        //设置TextView的点击事件
        tvHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_id!=0){
                    Log.i("MainActivity", "onClick  跳转到登录界面:"+user_id);
                }else {
                    //跳转到登录界面
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        user_id= ((MyApplication) getApplication()).getUser().getUser_id();

        //个推绑定别名

        if (user_id!=0) {

            Boolean a = PushManager.getInstance().bindAlias(this, "kdc" + user_id + "");

            if (!a) {
                handler.postDelayed(new Runnable(){
                    public void run() {
                        Boolean a = PushManager.getInstance().bindAlias(HomeActivity.this, "kdc" + user_id + "");
                      //  Log.d("gt", "6秒后绑定" + a);
                    }
                }, 6000);
            }
        }


        ButterKnife.inject(this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        radioGroup = (RadioGroup) findViewById(R.id.linearLayout3);

        Fragement1 fragement1 = new Fragement1();
        fragement2 = new Fragement2();
        ListiFragment lf=new ListiFragment();


        Bundle bundle = new Bundle();
        bundle.putInt("str",user_id);
        fragement1.setArguments(bundle);

        fragmentList.add(fragement1);
        fragmentList.add(fragement2);
        fragmentList.add(lf);


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

                 currentIndex = -1;//viewpager显式项的位置
                switch (checkedId) {
                    case R.id.button5:
                        currentIndex = 0;
                        break;
                    case R.id.button6:
                        currentIndex = 1;
                        break;
                    case R.id.button7:
                        currentIndex = 2;
                        break;
                }
                vp.setCurrentItem(currentIndex);
            }
        });
    }

    @OnClick({R.id.btn_back, R.id.btn_basketball})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:

                break;
            case R.id.btn_basketball:
                Intent intent=new Intent(HomeActivity.this,CommunityMain.class);
                intent.putExtra("user_id",user_id);
                startActivity(intent);
                break;
        }
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

    /**
     *  drawlayout
     */

    @Override
    public void onBackPressed() {

        handler.postDelayed(new Runnable(){
            public void run() {
                tc=0;
            }
        }, 3000);

        tc++;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            tc=0;
            drawer.closeDrawer(GravityCompat.START);
        } else {

            if(tc==2) {
                finish();
            }else{
                Toast.makeText(HomeActivity.this, "再按一次退出酷动场", Toast.LENGTH_SHORT).show();
            }
        }
    }


    //菜单设计
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawlayout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_person) {
            // Handle the camera action
            Intent intent1=new Intent(this,UserActivity.class);
            startActivity(intent1);
        } else if (id == R.id.nav_yuyue) {
            Intent intent=new Intent(this,AllOrderActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_zhaoji) {
            Intent intent = new Intent(HomeActivity.this, MusterlistActivity.class);
            intent.putExtra("venues_id",0+"");
            startActivity(intent);

        } else if (id == R.id.nav_qiandao) {
            Intent intent = new Intent(HomeActivity.this, Sign_tbl.class);

            startActivity(intent);

        } else if (id == R.id.nav_shezhi) {

        } else if (id == R.id.nav_tuichu) {

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void bd(){

      //  int cid;

        RequestParams params = new RequestParams(NetUtil.url +"LoginServlet2");
        params.addBodyParameter("user_id", user_id+"");//post方法的传值
    //    params.addBodyParameter("userPwd", pwds);
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });


    }




     /*
    * 连接融云
    * */

    private void connectRongServer(String token) {
        /*
        * 请求 融云*/
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {

            }

            @Override
            public void onSuccess(String s) {
                Log.i("chat", "onSuccess: "+s);
               /* if (Integer.parseInt(s)==1) {
                    Log.i("chat", "onSuccess: 2"+Integer.parseInt(s)+"");
                    Intent intent1 =new Intent(LoginActivity.this, HomeActivity.class);
                    intent1.putExtra("userId",Integer.parseInt(s));
                    startActivity(intent1);
                } else {
                    Intent intent2=new Intent(LoginActivity.this, HomeActivity.class);
                    intent2.putExtra("userId",Integer.parseInt(s));
                    startActivity(intent2);
                    Log.i("chat", "onSuccess:2 "+s);
                }*/
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
              //  Log.i("kdc", "onSuccess: "+errorCode.getValue());
            }
        });
    }
    //信息接收监听
    public void initData(){
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {

            @Override
            public boolean onReceived(Message message, int i) {
                MessageContent messageContent = message.getContent();
                Log.i("chenyu", "onReceived: "+(messageContent instanceof ContactNotificationMessage));
                if(messageContent instanceof ContactNotificationMessage){
                    ContactNotificationMessage contactNotificationMessage= (ContactNotificationMessage) messageContent;
                    Log.i("chat2", "onReceived: "+contactNotificationMessage);
                    Toast.makeText(HomeActivity.this,contactNotificationMessage.getMessage(),Toast.LENGTH_SHORT).show();
                    MyApplication myapplication= (MyApplication) getApplication();
                    List<ContactNotificationMessage> contactNotificationMessages=  myapplication.getContactNotificationMessages();
                    Log.i("chat2", "onReceived: "+"11111111111111");
                    List flags=myapplication.getFlags();
                    contactNotificationMessages.add(contactNotificationMessage);
                    flags.add(true);
                    myapplication.setContactNotificationMessages(contactNotificationMessages);
                    myapplication.setFlags(flags);
                    return true;

                }
                return false;
            }
        });
    }

    //用来提供用户信息
    public void initUserInfo(){
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            Usershow_tbl usershow_tbl;
            @Override
            public UserInfo getUserInfo(final String s) {
                RequestParams requestParams=new RequestParams(NetUtil.url+"QueryFriendInfo");
                requestParams.addQueryStringParameter("userId",s);
                x.http().get(requestParams,new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson=new Gson();
                        usershow_tbl=gson.fromJson(result,new TypeToken<Usershow_tbl>(){}.getType());
                        //刷新数据
                        RongIM.getInstance().refreshUserInfoCache(new UserInfo(s,usershow_tbl.getUsershow_name(),Uri.parse(usershow_tbl.getUsershow_head())));
                        Log.i("chat1", "onSuccess: "+s);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("chat1", "onError: ");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        Log.i("chat1", "onFinished: ");
                    }
                });
                return null;
            }
        },true);
    }


}