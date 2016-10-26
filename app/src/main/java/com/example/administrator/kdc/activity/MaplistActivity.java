package com.example.administrator.kdc.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.administrator.kdc.Adapter.VenuesAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MaplistActivity extends Activity {//显示list的主页

    List<Venues_tbl> venuesList = new ArrayList<Venues_tbl>();

    VenuesAdapter venuesAdapter;
    VenuesAdapter adapter;

    Context context=this;

    int flag1=1,flag2=1;

    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.linearLayout)
    RelativeLayout linearLayout;
    @InjectView(R.id.listView)
    ListView listView;
     int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maplist);

        Intent intent=getIntent();
        user_id=intent.getIntExtra("user_id",0);
        Log.e("BBBBB", "maplist" + user_id);

        ButterKnife.inject(this);
        initVenues();

        adapter = new VenuesAdapter(venuesList,this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);//调用适配器

    }


    private void initVenues() {//插入测试数据

        RequestParams params = new RequestParams(NetUtil.url +"MaplistServlet");
        params.addBodyParameter("userId", user_id+"");//post方法的传值

      //  Toast.makeText(MaplistActivity.this, "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                Gson gson=new Gson();
                List<Venues_tbl> list = gson.fromJson(result,
                        new TypeToken<List<Venues_tbl>>() {
                        }.getType());
                for (Venues_tbl lis : list) {
                    venuesList.add(lis);
                }

                adapter = new VenuesAdapter(venuesList,context);
                listView = (ListView) findViewById(R.id.listView);
                listView.setAdapter(adapter);//调用适配器

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override//点击此view进行界面的跳转
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Venues_tbl  upload=venuesList.get(position);
                        //   Log.d("BBBBB","url2 ven____________________"+upload.getVenuesshow_tbl().getVenuesshow_portrait());
                        Intent intent = new Intent(MaplistActivity.this, VenuesshowActivity.class);
                        intent.putExtra("user_id",user_id);
                        intent.putExtra("venues_tbl", upload);//发送数据
                        startActivity(intent);

                    }
                });


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("BBBBB", "链接失败");

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

    }

    @OnClick({R.id.button2, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                flag1++;
                if (flag1%2==0){
                sorting2(venuesList) ;
                    button2.setText("按好评排列↓");
                }else{sorting22(venuesList);
                    button2.setText("按好评排列↑");}
                adapter.notifyDataSetChanged();
                break;
            case R.id.button3:
                flag2++;
                if (flag2%2==0){
                    sorting1(venuesList) ;
                    button3.setText("按人气排列↓");
                }else{sorting11(venuesList);
                    button3.setText("按人气排列↑");}
                adapter.notifyDataSetChanged();
                break;
        }
    }

    public void sorting2(List venuesList) {
        Collections.sort(venuesList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Venues_tbl venues1 = (Venues_tbl) o1;
                Venues_tbl venues2 = (Venues_tbl) o2;
                if (venues1.getVenues_yes() > venues2.getVenues_yes()) {
                    return -1;
                } else if (venues1.getVenues_yes() == venues2.getVenues_yes()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }
    public void sorting22(List venuesList) {
        Collections.sort(venuesList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Venues_tbl venues1 = (Venues_tbl) o1;
                Venues_tbl venues2 = (Venues_tbl) o2;
                if (venues1.getVenues_yes() > venues2.getVenues_yes()) {
                    return 1;
                } else if (venues1.getVenues_yes() == venues2.getVenues_yes()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }

    public void sorting1(List venuesList) {
        Collections.sort(venuesList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Venues_tbl venues1 = (Venues_tbl) o1;
                Venues_tbl venues2 = (Venues_tbl) o2;
                if (venues1.getVenues_ceiling() > venues2.getVenues_ceiling()) {
                    return -1;
                } else if (venues1.getVenues_ceiling() == venues2.getVenues_ceiling()) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
    }
    public void sorting11(List venuesList) {
        Collections.sort(venuesList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Venues_tbl venues1 = (Venues_tbl) o1;
                Venues_tbl venues2 = (Venues_tbl) o2;
                if (venues1.getVenues_ceiling() > venues2.getVenues_ceiling()) {
                    return 1;
                } else if (venues1.getVenues_ceiling() == venues2.getVenues_ceiling()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }
}