package com.example.administrator.kdc.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.VC_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import indoorview.BaseStripAdapter;
import indoorview.StripListView;

public class one extends Activity {

    // 定位相关
    LocationClient mLocClient;

    public MyLocationListenner myListener = new MyLocationListenner();


    Boolean flag1 = true, flag2 = true, flag3 = true, flag11 = flag1, flag22 = flag2, flag33 = flag3;
    int flag = 0;


    //加载的mark集合
    private List<VC_tbl> infos;
    private List<VC_tbl> infos1;
    private List<VC_tbl> infos2;
    private List<VC_tbl> infos3;
    int user_id = 0;

    private final int SDK_PERMISSION_REQUEST = 127;
    List<String> providerList;

    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    MapView mMapView;
    BaiduMap mBaiduMap;

    StripListView stripListView;
    BaseStripAdapter mFloorListAdapter;
    MapBaseIndoorMapInfo mMapBaseIndoorMapInfo = null;
    // UI相关
    double j, w;

    Button bUp;
    Button bShow;
    Button bXx;
    Button button;
    Button requestLocButton;

    boolean isFirstLoc = true; // 是否首次定位

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        RelativeLayout layout = new RelativeLayout(this);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mainview = inflater.inflate(R.layout.activity_location, null);
        layout.addView(mainview);

        bUp = (Button) mainview.findViewById(R.id.b_up);
        bShow = (Button) mainview.findViewById(R.id.b_show);
        bXx = (Button) mainview.findViewById(R.id.b_xx);
        button = (Button) mainview.findViewById(R.id.button);
        requestLocButton = (Button) mainview.findViewById(R.id.button1);

        bShow.setOnClickListener(new View.OnClickListener() {//游客登录
            @Override
            public void onClick(View v) {//主页
                Intent intent = new Intent(one.this, HomeActivity.class);
                intent.putExtra("flag", "2");
                //   startActivity(intent);
                setResult(RESULT_OK, intent);
                finish();

            }
        });
        bUp.setOnClickListener(new View.OnClickListener() {//游客登录
            @Override
            public void onClick(View v) {//主页

                Intent intent2 = new Intent(one.this, UploadActivity.class);
                intent2.putExtra("user_id", user_id);
                intent2.putExtra("w", w);
                intent2.putExtra("j", j);
                startActivity(intent2);

            }
        });
        bXx.setOnClickListener(new View.OnClickListener() {//游客登录
            @Override
            public void onClick(View v) {//主页
                Intent intent = new Intent(one.this, HomeActivity.class);
                intent.putExtra("flag", "2");
                //   startActivity(intent);
                setResult(RESULT_OK, intent);
                finish();

            }
        });



        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        requestLocButton.setText("普通");
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                                mCurrentMarker));
                        break;
                    case COMPASS:
                        requestLocButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                                mCurrentMarker));
                        break;
                    case FOLLOWING:
                        requestLocButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true,
                                mCurrentMarker));
                        break;
                    default:
                        break;
                }
            }
        };
        requestLocButton.setOnClickListener(btnClickListener);

        // 地图初始化
        mMapView = (MapView) mainview.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 开启室内图
        mBaiduMap.setIndoorEnable(true);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();

        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(3000);

        mLocClient.setLocOption(option);

        mLocClient.start();

        stripListView = new StripListView(this);
        layout.addView(stripListView);
        setContentView(layout);

        mFloorListAdapter = new BaseStripAdapter(one.this);

        mBaiduMap.setOnBaseIndoorMapListener(new BaiduMap.OnBaseIndoorMapListener() {
            @Override
            public void onBaseIndoorMapMode(boolean b, MapBaseIndoorMapInfo mapBaseIndoorMapInfo) {
                if (b == false || mapBaseIndoorMapInfo == null) {
                    stripListView.setVisibility(View.INVISIBLE);
                    return;
                }

                mFloorListAdapter.setmFloorList(mapBaseIndoorMapInfo.getFloors());
                stripListView.setVisibility(View.VISIBLE);
                stripListView.setStripAdapter(mFloorListAdapter);
                mMapBaseIndoorMapInfo = mapBaseIndoorMapInfo;
            }
        });

        user_id = ((MyApplication) getApplication()).getUser().getUser_id();

        setMarkerInfo();//覆盖物的获取

    }


    private void setMarkerInfo() {

        infos = new ArrayList<VC_tbl>();
        infos1 = new ArrayList<VC_tbl>();
        infos2 = new ArrayList<VC_tbl>();
        infos3 = new ArrayList<VC_tbl>();

        //从网络获取数据
        RequestParams params = new RequestParams(NetUtil.url + "MaplistServlet2");
        params.addBodyParameter("userId", 1 + "");//post方法的传值

        //  Toast.makeText(MaplistActivity.this, "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                //   Log.e("aaaa", "场地信息返回成功" + result);
                List<VC_tbl> newOrders = gson.fromJson(result, new TypeToken<List<VC_tbl>>() {
                }.getType());
                for (VC_tbl lis : newOrders) {
                    infos.add(lis);
                }
                //将场馆按类型分类
                for (VC_tbl info : infos) {
                    if (info.getVenues_tbl().getVenues_type() == 1) {
                        infos1.add(info);
                    } else if (info.getVenues_tbl().getVenues_type() == 2) {
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
    private void addOverlay(List<VC_tbl> infos) {
        //清空地图
        mBaiduMap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap;
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for (VC_tbl info : infos) {
            //按类型不同显示不同的覆盖物
            if (info.getVenues_tbl().getVenues_type() == 1) {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dy1);
            } else if (info.getVenues_tbl().getVenues_type() == 2) {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dy3);
            } else {
                bitmap = BitmapDescriptorFactory.fromResource(R.drawable.dy2);
            }
            //获取经纬度
            latLng = new LatLng(info.getVenues_tbl().getLatitude(), info.getVenues_tbl().getLongitude());
            //设置marker
            options = new MarkerOptions()
                    .position(latLng)//设置位置
                    .icon(bitmap)//设置图标样式
                    .zIndex(9) // 设置marker所在层级
            ; // 设置手势拖拽;
            //添加marker
            marker = (Marker) mBaiduMap.addOverlay(options);
            //添加文本
            TextOptions text = new TextOptions();
            text.bgColor(Color.GRAY).fontColor(Color.GREEN).text(info.getVenues_tbl().getVenues_name())
                    .position(new LatLng(info.getVenues_tbl().getLatitude(), info.getVenues_tbl().getLongitude()))
                    .fontSize(25);
            mBaiduMap.addOverlay(text);
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
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                VC_tbl infoUtil = (VC_tbl) bundle.getParcelable("info");
                //跳转到详情表
                Intent intent = new Intent(one.this, VenuesshowActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("vc_tbl", infoUtil);//发送数据
                intent.putExtra("sc", infoUtil.getFlag());//发送数据
                intent.putExtra("yn", infoUtil.getFlag2());//发送数据
                intent.putExtra("yes", infoUtil.getVenues_tbl().getVenues_yes());//发送数据
                intent.putExtra("no", infoUtil.getVenues_tbl().getVenues_no());//发送数据
                startActivity(intent);
                return true;
            }
        });
    }



    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        private String lastFloor = null;

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置

            j=location.getLongitude();
            w=location.getLatitude();

            Log.i("indoor", "j = " + j + "   w = " + w);

            if (location == null || mMapView == null) {
                return;
            }

            String bid = location.getBuildingID();
            if (bid != null && mMapBaseIndoorMapInfo != null) {
                Log.i("indoor", "bid = " + bid + " mid = " + mMapBaseIndoorMapInfo.getID());
                if (bid.equals(mMapBaseIndoorMapInfo.getID())) {// 校验是否满足室内定位模式开启条件
                    // Log.i("indoor","bid = mMapBaseIndoorMapInfo.getID()");



                    ((MyApplication) getApplication()).setLatitude(w);
                    ((MyApplication) getApplication()).setLatitude(j);

                    String floor = location.getFloor().toUpperCase();// 楼层
                    Log.i("indoor", "floor = " + floor + " position = " + mFloorListAdapter.getPosition(floor));
                    Log.i("indoor", "radius = " + location.getRadius() + " type = " + location.getNetworkLocationType());

                    boolean needUpdateFloor = true;
                    if (lastFloor == null) {
                        lastFloor = floor;
                    } else {
                        if (lastFloor.equals(floor)) {
                            needUpdateFloor = false;
                        } else {
                            lastFloor = floor;
                        }
                    }
                    if (needUpdateFloor) {// 切换楼层
                        mBaiduMap.switchBaseIndoorMapFloor(floor, mMapBaseIndoorMapInfo.getID());
                        mFloorListAdapter.setSelectedPostion(mFloorListAdapter.getPosition(floor));
                        mFloorListAdapter.notifyDataSetInvalidated();
                    }

                    if (!location.isIndoorLocMode()) {
                        mLocClient.startIndoorMode();// 开启室内定位模式，只有支持室内定位功能的定位SDK版本才能调用该接口
                        Log.i("indoor", "start indoormod");
                    }
                }
            }

            MyLocationData locData = new MyLocationData.Builder().accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude()).longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }





}
