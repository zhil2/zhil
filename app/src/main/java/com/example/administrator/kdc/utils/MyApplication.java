package com.example.administrator.kdc.utils;

import android.app.Application;

import com.example.administrator.kdc.vo.User_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;

import org.xutils.x;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyApplication extends Application{

    public User_tbl getUser() {
        return user;
    }
    public void setUser(User_tbl user) {
        this.user = user;
    }

    public Usershow_tbl getUsershow() {
        return usershow;
    }
    public void setUsershow(Usershow_tbl usershow) {
        this.usershow = usershow;
    }


    private User_tbl user=new User_tbl(1,0,"0","0");
    private Usershow_tbl usershow=new Usershow_tbl();





    @Override public void onCreate()
    { super.onCreate();
        x.Ext.init(this);

        //x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能. ...
    }





}
