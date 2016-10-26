package com.example.administrator.kdc.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.administrator.kdc.Adapter.VenuesAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Venues_tbl;

import java.util.ArrayList;
import java.util.List;

public class TooblearActivity extends AppCompatActivity {
    Toolbar toolbar;
    List<Venues_tbl> venuesList = new ArrayList<Venues_tbl>();
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    VenuesAdapter adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tooblear);

        toolbar= (Toolbar) findViewById(R.id.view);
         toolbar.setTitle("首页");

        //    toolbar.setLogo(R.mipmap.ic_launcher);//logo
      //  toolbar.setSubtitle("subtitle");//副标题
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.tx);//导航图标的图标
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        initVenues();
        adapter = new VenuesAdapter(venuesList, this);
        mDrawerList.setAdapter(adapter);//调用适配器

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override//点击此view进行界面的跳转
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TooblearActivity.this, VenuesshowActivity.class);
                startActivity(intent);
            }
        });


        //设置toolbar的导航图标点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TooblearActivity.this, "打开", Toast.LENGTH_SHORT).show();
             //   Log.i("MyToolBarActivity", "NavigationOnClickListener: onClick");
            }
        });

        //设置toolbar的menu item点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(TooblearActivity.this, "打开2", Toast.LENGTH_SHORT).show();
             //   Log.i("MyToolBarActivity", "MyOnMenuItemClickListener: onMenuItemClick");
                return false;
            }
        });
    }

    //设置toolbar显示内容
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //某个item被选中的事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(TooblearActivity.this, "打开3", Toast.LENGTH_SHORT).show();
        Log.i("MyToolBarActivity", "MyToolBarActivity: onOptionsItemSelected:menuId:" + item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void initVenues() {//插入测试数据
//        venuesList.add(new Venues_tbl(33,120,1, "东大篮球馆", 21, 100, 1, 1, 2, 20, 2));
//        venuesList.add(new Venues_tbl(33,120,2, "东大篮球馆", 23, 100, 1, 1, 1, 21, 2));
//        venuesList.add(new Venues_tbl(33,120,3, "东大篮球馆", 21, 100, 1, 1, 3, 22, 2));
//        venuesList.add(new Venues_tbl(33,120,4, "东大篮球馆", 31, 100, 1, 1, 1, 20, 2));
//        venuesList.add(new Venues_tbl(33,120,5, "东大篮球馆", 21, 100, 1, 1, 1, 24, 2));
//        venuesList.add(new Venues_tbl(33,120,6, "东大篮球馆", 11, 100, 1, 1, 1, 25, 2));
//        venuesList.add(new Venues_tbl(33,120,7, "东大篮球馆", 41, 100, 1, 1, 1, 23, 2));
//        venuesList.add(new Venues_tbl(33,120,8, "东大篮球馆", 21, 100, 1, 1, 1, 20, 2));
    }
}
