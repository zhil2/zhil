package com.example.administrator.kdc.utils;

import android.app.Application;
import android.app.Service;
import android.os.Vibrator;

import com.baidu.location.service.LocationService;
import com.baidu.mapapi.SDKInitializer;
import com.example.administrator.kdc.vo.User_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;

import org.xutils.x;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyApplication extends Application{
    public Double getLatitude() {
        return latitude;
    }
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
    public Double getLongitude() {
        return longitude;
    }
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
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
    public String getCid() {
        return cid;
    }
    public void setCid(String cid) {
        this.cid = cid;
    }

    private User_tbl user=new User_tbl(0,0,"0","0");
    private Usershow_tbl usershow=new Usershow_tbl();
    private String cid="初始cid";
    private String token;
    private Double latitude=33.0;
    private Double longitude=120.0;

    public LocationService locationService;
    public Vibrator mVibrator;


    @Override public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        //初始化
        RongIM.init(this);

        locationService = new LocationService(getApplicationContext());
        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
        SDKInitializer.initialize(getApplicationContext());


    }


















}
