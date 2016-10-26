package com.example.administrator.kdc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.administrator.kdc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {



    @InjectView(R.id.bmapView)
    MapView bmapView;
    private MapView mapView = null;
    private BaiduMap map = null;
    private LatLng center = null;
    double w=0,j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        w=intent.getDoubleExtra("w",0.0);
        j=intent.getDoubleExtra("j",0.0);
        Log.d("ppp1111",w+"  "+j);
        mapView = (MapView) findViewById(R.id.bmapView);
        /*
         * 用经纬度定义地图默认显示中心
         */
        center = new LatLng(w,j);
        MapStatus status = new MapStatus.Builder().target(center).build();
        MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newMapStatus(status);

       // statusUpdate = MapStatusUpdateFactory.zoomTo(19f);
        /*
         * 获取MapView中的BaiduMap引用，并改变地图状态
         */

        map = mapView.getMap();
        map.setMapStatus(statusUpdate);
        MarkerOptions marker = new MarkerOptions();
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.dy1);
        marker.icon(bitmap).position(center).draggable(true);
        map.addOverlay(marker);

        //覆盖物点击事件
        map.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                new  AlertDialog.Builder(MainActivity.this)
                        .setTitle("确认定位当前坐标吗？" )
                        .setMessage("当前坐标:"+" 经度:"+j+" 纬度:"+w )
                        .setPositiveButton("是" , new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent intent=new Intent();
                                intent.putExtra("w",w+"");
                                intent.putExtra("j",j+"");
                                Log.d("qq","2经度:"+j+"纬度:"+w);
                                setResult(RESULT_OK,intent);
                                finish();
                            }
                        } )
                        .setNegativeButton("否" , null)
                        .show();
                return false;
            }
        });
        //覆盖物拖拽事件
        map.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker arg0) {
            }
            @Override
            public void onMarkerDragEnd(Marker arg0) {

                j=arg0.getPosition().longitude;
                w=arg0.getPosition().latitude;

                Toast.makeText(
                        MainActivity.this,
                        "拖拽结束，新位置：纬度：" + w + ", 经度：" + j, Toast.LENGTH_LONG).show();
            }
            @Override
            public void onMarkerDrag(Marker arg0) {
            }
        });

    }
}
