package com.example.administrator.kdc.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.db.Mydb;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.User_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, RongIM.UserInfoProvider{//登陆界面//界面修改过

    private EditText name, pwd;
    private Button login, login2, back, registred;
    private User_tbl user = null;
    private ImageView b;

    private Mydb dbHelpr;
    SQLiteDatabase db;

    String pwds;
    int names;
    private List<Usershow_tbl> userIdList;

    boolean jizhu;
    private static final String token1 = "PlSsY+fb7VP4TZ/71tJoFpFc7QgoHgMEC/sR4LQOGQFPP1bbTUsagqPd3rxGIWDufaF7huEtwZ0=";
    private static final String token2 = "48NzJ4oYh58rZnNzQZV3F/Cp7L7bJXH4SuYbDkfFpZ+jg4+djEN9sGgeLJHOo0WiqbprCs3A4RL5ZUUei/4rhQ==";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);

        //初始化用户信息
        initUserInfo();
        b = (ImageView) findViewById(R.id.iv_b);
        login2 = (Button) findViewById(R.id.b_login2);
        login = (Button) findViewById(R.id.b_login);
        back = (Button) findViewById(R.id.b_back);
        registred = (Button) findViewById(R.id.b_registred);
        name = (EditText) findViewById(R.id.et_name);
        pwd = (EditText) findViewById(R.id.et_pwd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//登录

                 names = Integer.parseInt(name.getText().toString());
                 pwds = pwd.getText().toString();
                //
                       connectRongServer(token1);
                Log.i("chat", "onClick: "+token1);


                login();

            }
        });
        login2.setOnClickListener(new View.OnClickListener() {//游客登录
            @Override
            public void onClick(View v) {//主页
                      Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                     startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {//找回密码
            @Override
            public void onClick(View v) {//忘记密码


                   Intent intent=new Intent(LoginActivity.this,BackActivity.class);
                   startActivity(intent);

            }
        });
        registred.setOnClickListener(new View.OnClickListener() {//注册
            @Override
            public void onClick(View v) {//注册用户
                Intent intent=new Intent(LoginActivity.this,RegistredActivity.class);
                startActivity(intent);
            }
        });
    }


    public void login(){

        RequestParams params = new RequestParams(NetUtil.url +"LoginServlet2");
        params.addBodyParameter("userName", names+"");//post方法的传值
        params.addBodyParameter("userPwd", pwds);
        Toast.makeText(LoginActivity.this, "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
                Log.e("BBBBB", "u3  get yes+" + result);
                Gson gson=new Gson();
                Usershow_tbl usershows=new Usershow_tbl();
                usershows=gson.fromJson(result,Usershow_tbl.class);
                User_tbl users=usershows.getUser_tbl();

                if (!usershows.equals(null)) {
                    //将数据存储到数据库里
                    Log.d("BBBBB","登录成功");

                    dbHelpr=new Mydb(LoginActivity.this,"kdc.db",null,1);
                    dbHelpr.getWritableDatabase();

                    db=dbHelpr.getWritableDatabase();

                    //清空表
                    db.delete("user_tbl",null,null);
                    db.delete("usershow_tbl",null,null);

                    //插入表
                    ContentValues values=new ContentValues();
                    values.put("id",users.getUser_id());
                    values.put("user_number",users.getUser_number());
                    values.put("user_pwd",users.getUser_pwd());
                    values.put("user_emal",users.getUser_emal());
                    db.insert("user_tbl",null,values);
                    values.clear();

                    values.put("id",usershows.getUsershow_id());
                    values.put("user_id",usershows.getUser_tbl().getUser_id());
                    values.put("usershow_name",usershows.getUsershow_name());
                    values.put("usershow_sex",usershows.getUsershow_sex());
                    values.put("usershow_age",usershows.getUsershow_age());
                    values.put("usershow_birthday", String.valueOf(usershows.getUsershow_birthday()));
                  //  values.put("usershow_birthday", "1994-12-16 12:12");
                    values.put("usershow_head",usershows.getUsershow_head());
                    values.put("usershow_credit",usershows.getUsershow_credit());
                    values.put("usershow_money",usershows.getUsershow_money());
                    values.put("address_id",usershows.getAddress_id());
                    db.insert("usershow_tbl",null,values);
                    values.clear();

            //        Log.d("BBBBB","到达不了????"+result);

                    //登录成功后将

//                    ((MyApplication) getApplication()).setUsershow(usershows);
//                    ((MyApplication) getApplication()).setUser(users);

                    Toast.makeText(LoginActivity.this, "登录成功，欢迎您", Toast.LENGTH_SHORT).show();
                    //将user_id传过去
                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                    intent.putExtra("user_id",users.getUser_id());
                    startActivity(intent);
                    finish();

                } else {
                //    Log.d("BBBBB","登录失败"+result);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("BBBBB", "u3  get no");
                pwd.setText("");
                Toast.makeText(LoginActivity.this, "您的账号/密码错误，请区分大小写或者重新输入", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
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
                Log.i("kdc", "onSuccess: "+errorCode.getValue());
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
    //用来提供用户信息
    @Override
    public UserInfo getUserInfo(String s) {
        for (Usershow_tbl i : userIdList) {
            if (i.getUser_id()==(Integer.parseInt(s))) {
                Log.i("chat","image:" +i.getUsershow_head());
                return new UserInfo(i.getUser_id()+"", i.getUsershow_name(), Uri.parse(i.getUsershow_head()));
            }
        }
        Log.i("chat", "UserId is : " + s);
        return null;
    }

    private void initUserInfo() {
        Log.i("chat", "initUserInfo: "+"x信息");
        userIdList = new ArrayList<Usershow_tbl>();

        userIdList.add(new Usershow_tbl(1, "路飞", "http://p.qq181.com/cms/1210/2012100413195471481.jpg"));
        userIdList.add(new Usershow_tbl(2, "露西", "http://p0.so.qhmsg.com/bdr/200_200_/t01011efe90e544739a.jpg"));
        RongIM.setUserInfoProvider(this, true);  //设置用户的信息的提供者 共RongIm使用  TRUE可以缓存到本地
    }


}
