package com.example.administrator.kdc.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.kdc.R;
//import com.example.administrator.kdc.utils.MyLocationListener;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MapActivity extends AppCompatActivity {
    int user_id = 2;
    @InjectView(R.id.b_show)
    Button bShow;
    @InjectView(R.id.b_up)
    Button bUp;
    @InjectView(R.id.b_xx)
    Button bXx;
    @InjectView(R.id.relativeLayout)
    RelativeLayout relativeLayout;
    @InjectView(R.id.bmapview)
    MapView bmapview;

    double j, w;
    @InjectView(R.id.button)
    Button button;
    // 百度地图控件
    private MapView mMapView = null;
    // 百度地图对象
    private BaiduMap bdMap;
    //定位的实例
    private LocationManager locationManager;
    //3种位置提供器的哪一种
    private String provider;
    //立一个标签，防止重复定位当前地址
    private boolean isFirstLocate = true;

    Boolean flag1 = true, flag2 = true, flag3 = true, flag11 = flag1, flag22 = flag2, flag33 = flag3;
    int flag=0;


    //加载的mark集合
    private List<Venues_tbl> infos;
    private List<Venues_tbl> infos1;
    private List<Venues_tbl> infos2;
    private List<Venues_tbl> infos3;

    private final int SDK_PERMISSION_REQUEST = 127;

   // public MyLocationListener myListener = new MyLocationListener();

    List<String> providerList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   requestWindowFeature(Window.FEATURE_NO_TITLE);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);
        //获取id信息
        Intent intent = getIntent();
        user_id = ((MyApplication) getApplication()).getUser().getUser_id();

        ButterKnife.inject(this);
        init();
        //获取地图对象
        bdMap = mMapView.getMap();
        bdMap.setMyLocationEnabled(true);
        //获取LocationManager的实例，getSystemService（）方法用来确定获取系统的那个服务
        //注似乎位置提供器服务必须由用户自己开启，用app没有方法可以直接调用权限的的样子
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        //获取所有定位服务
         providerList = locationManager.getProviders(true);
        //判断是否开启了gps
        Log.d("aaa", "" + providerList + "GPS_PROVIDER" + providerList.contains(LocationManager.GPS_PROVIDER));

        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(MapActivity.this, "正在使用gps服务定位", Toast.LENGTH_SHORT).show();
            provider = LocationManager.GPS_PROVIDER;
            //判断是否开启了网络定位
        } else
        if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(MapActivity.this, "您的gps功能未开启，或者卫星链接过少，自动帮您启用网络服务定位，可能导致定位偏差较大", Toast.LENGTH_LONG).show();
            provider = LocationManager.NETWORK_PROVIDER;
        } else

        {
            Toast.makeText(MapActivity.this, "您还未开启定位服务，请您先在设置中开启定位服务功能", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            //调用方法，将获取的数据取出并装到LatLng中
            navigateTo(location);
        }
        //监听器，时间每5秒监听一次，距离没1m监听一次
        locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);



     //   mLocationClient.start();
      //  navigateTo(location);//定中心
        setMarkerInfo();//覆盖物的获取
      //  getPersimmions();

    }

    private void navigateTo(Location location) {
        if (isFirstLocate) {
            //将将获取的数据取出并装到LatLng中(确定要定定位的位置)
            // LatLng a = new LatLng(23, 113);
            j = location.getLongitude();
            w = location.getLatitude();
            //  j=113.0;
            //  w=24.0;
            LatLng a = new LatLng(w, j);

            Log.d("ppp1111","map"+ w + "  " + j);

            //将LatLng对象转为MapStatusUpdate对象
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(a);
            //将位置信息最后转化为BaiduMap的属性
            bdMap.animateMapStatus(update);
            //设置缩放等级3~19
            update = MapStatusUpdateFactory.zoomTo(19f);
            bdMap.animateMapStatus(update);
            //将标签修改
            isFirstLocate = false;
        }
        MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
        locationBuilder.latitude(location.getLatitude());
        locationBuilder.longitude(location.getLongitude());
        MyLocationData locationData = locationBuilder.build();
        bdMap.setMyLocationData(locationData);
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //更新位置
            if (location != null) {
                navigateTo(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    private void init() {
        mMapView = (MapView) findViewById(R.id.bmapview);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bdMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //退出时关闭监听器
            locationManager.removeUpdates(locationListener);
        }
    }

    //初始化覆盖物
    private void newVenuess(double a, double b, String name) {

        MarkerOptions marker = new MarkerOptions();
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.zh);
        marker.icon(bitmap).position(new LatLng(a, b)).draggable(false);
        bdMap.addOverlay(marker);

         /*
         * 创建文字覆盖物
         */
        TextOptions text = new TextOptions();
        text.bgColor(Color.GRAY).fontColor(Color.GREEN).text(name)
                .position(new LatLng(a, b))
                .fontSize(25);
        bdMap.addOverlay(text);
        //响应事件
        bdMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
          //      Toast.makeText(MapActivity.this, "登录成功，欢迎您", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void setMarkerInfo() {

        infos = new ArrayList<Venues_tbl>();
        infos1 = new ArrayList<Venues_tbl>();
        infos2 = new ArrayList<Venues_tbl>();
        infos3 = new ArrayList<Venues_tbl>();

        //从网络获取数据
        RequestParams params = new RequestParams(NetUtil.url +"MaplistServlet2");
        params.addBodyParameter("userId", user_id + "");//post方法的传值


        //  Toast.makeText(MaplistActivity.this, "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Log.e("aaaa", "场地信息返回成功" + result);
                List<Venues_tbl> list = gson.fromJson(result,
                        new TypeToken<List<Venues_tbl>>() {
                        }.getType());

                for (Venues_tbl lis : list) {
                    infos.add(lis);
                    Log.e("aaaaa", "venuesList：" + infos);
                }
                //将场馆按类型分类
                for (Venues_tbl info : infos) {
                    if (info.getVenues_type() == 1) {
                        infos1.add(info);
                    } else if (info.getVenues_type() == 2) {
                        infos2.add(info);
                    } else {
                        infos3.add(info);
                    }
                }
                //显示覆盖物
                addOverlay(infos);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("aaaaa", "链接失败");
                //    Toast.makeText(LoginActivity.this, "您的账号/密码错误，请区分大小写或者重新输入", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
            }
        });

        Log.e("aaaaa", "venuesList2222：" + infos);
    }

    //显示marker
    private void addOverlay(List<Venues_tbl> infos) {
        //清空地图
        bdMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap;
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for (Venues_tbl info : infos) {
            //按类型不同显示不同的覆盖物
            if (info.getVenues_type() == 1) {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dy1);
            } else if (info.getVenues_type() == 2) {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dy3);
            } else {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dy2);
            }
            //获取经纬度
            latLng = new LatLng(info.getLatitude(), info.getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9) // 设置marker所在层级
            ; // 设置手势拖拽;
            //添加marker
            marker = (Marker) bdMap.addOverlay(options);
            //添加文本
            TextOptions text = new TextOptions();
            text.bgColor(Color.GRAY).fontColor(Color.GREEN).text(info.getVenues_name())
                    .position(new LatLng(info.getLatitude(), info.getLongitude()))
                    .fontSize(25);
            bdMap.addOverlay(text);
            //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
            Bundle bundle = new Bundle();
            //info必须实现序列化接口
            bundle.putParcelable("info", info);
           // bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
        //将地图显示在最后一个marker的位置
  //      MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
   //     bdMap.setMapStatus(msu);
        //添加marker点击事件的监听
        bdMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                Venues_tbl infoUtil = (Venues_tbl) bundle.getParcelable("info");
                //跳转到详情表
                Intent intent = new Intent(MapActivity.this, VenuesshowActivity.class);
                intent.putExtra("user_id", 2);

                intent.putExtra("venues_tbl", infoUtil);//发送数据

                startActivity(intent);
                return true;
            }
        });
    }
    //菜单按钮
    @OnClick({R.id.b_show, R.id.b_up, R.id.b_xx,R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_show:
                Intent intent = new Intent(MapActivity.this, HomeActivity.class);
                intent.putExtra("flag", "2");
                //   startActivity(intent);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.b_up:
                Intent intent2 = new Intent(MapActivity.this, UploadActivity.class);
                intent2.putExtra("user_id", user_id);
                intent2.putExtra("w", w);
                intent2.putExtra("j", j);
                startActivity(intent2);
                break;
            case R.id.button:
                new  AlertDialog.Builder(MapActivity.this)
                        .setTitle("请选择定位方式" )
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setSingleChoiceItems(new  String[] {"gps定位", "网络定位" },flag,
                                new  DialogInterface.OnClickListener() {
                                    public   void  onClick(DialogInterface dialog,  int  which) {
                                        if(which==0){
                                           flag=0;
                                        }else{
                                           flag=1;
                                        }
                                    }
                                }
                        )
                        .setNegativeButton("确定" , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (flag==0){
                                    provider = LocationManager.GPS_PROVIDER;
                                }else{
                                    provider = LocationManager.NETWORK_PROVIDER;
                                }
                            }
                        })
                        .show();
                break;
            case R.id.b_xx:
                final String[] hobbies = {"显示支持预约的付费场地", "显示不支持预约的付费场地", "显示免费的野场地"};
                new AlertDialog.Builder(MapActivity.this)
                        .setTitle("选择显示的场地类型（多选）")
                        .setMultiChoiceItems(hobbies, new boolean[]{flag1, flag2, flag3}, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (which == 0) {
                                    flag1 = !flag1;
                                } else if (which == 1) {
                                    flag2 = !flag2;
                                } else if (which == 2) {
                                    flag3 = !flag3;
                                }
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                infos.clear();
                                if (flag1) {
                                    infos.addAll(infos1);
                                }
                                if (flag2) {
                                    infos.addAll(infos2);
                                }
                                if (flag3) {
                                    infos.addAll(infos3);
                                }
                                addOverlay(infos);
                                flag11 = flag1;
                                flag22 = flag2;
                                flag33 = flag3;
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                flag1 = flag11;
                                flag2 = flag22;
                                flag3 = flag33;
                            }
                        })
                        .show();
                break;
        }
    }

}
