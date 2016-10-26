package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.administrator.kdc.Adapter.VenuesfriendAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Usershow_tbl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VenuessfriendActivity extends AppCompatActivity {

    List<Usershow_tbl> VenuesfriendList = new ArrayList<Usershow_tbl>();

    VenuesfriendAdapter venuesfriendAdapter;
    VenuesfriendAdapter adapter;

    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.listView)
    ListView listView;
    int flag1=1,flag2=1;
    int user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venuessfriend);

        Intent intent=getIntent();
        user_id=intent.getIntExtra("user_id",0);

        Log.e("BBBBB", "Venfriend" + user_id);

        ButterKnife.inject(this);
        initVenues();
        adapter = new VenuesfriendAdapter(VenuesfriendList, this);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);//调用适配器

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override//点击此view进行界面的跳转
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(VenuessfriendActivity.this, VenuesshowActivity.class);
                startActivity(intent);
            }
        });


    }


    private void initVenues() {//插入测试数据


      //  VenuesfriendList.add(new Usershow_tbl(1,1,"qqq","男",21,"2010-9-10","tx",211,1,110));


    }


    @OnClick({R.id.button2, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                flag1++;
                if (flag1%2==0){
                    sorting2(VenuesfriendList) ;
                    button2.setText("按信誉排列↓");
                }else{sorting22(VenuesfriendList);
                    button2.setText("按信誉排列↑");}
                adapter.notifyDataSetChanged();
                break;
            case R.id.button3:
                flag2++;
                if (flag2%2==0){
                    sorting1(VenuesfriendList) ;
                    button3.setText("按人气排列↓");
                }else{sorting11(VenuesfriendList);
                    button3.setText("按人气排列↑");}
                adapter.notifyDataSetChanged();
                break;
        }
    }
    public void sorting2(List venuesList) {
        Collections.sort(venuesList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Usershow_tbl venues1 = (Usershow_tbl) o1;
                Usershow_tbl venues2 = (Usershow_tbl) o2;
                if (venues1.getUsershow_credit() > venues2.getUsershow_credit()) {
                    return -1;
                } else if (venues1.getUsershow_credit() == venues2.getUsershow_credit()) {
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
                Usershow_tbl venues1 = (Usershow_tbl) o1;
                Usershow_tbl venues2 = (Usershow_tbl) o2;
                if (venues1.getUsershow_credit() > venues2.getUsershow_credit()) {
                    return 1;
                } else if (venues1.getUsershow_credit() == venues2.getUsershow_credit()) {
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
                Usershow_tbl venues1 = (Usershow_tbl) o1;
                Usershow_tbl venues2 = (Usershow_tbl) o2;
                if (venues1.getUsershow_credit() > venues2.getUsershow_credit()) {
                    return -1;
                } else if (venues1.getUsershow_credit() == venues2.getUsershow_credit()) {
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
                Usershow_tbl venues1 = (Usershow_tbl) o1;
                Usershow_tbl venues2 = (Usershow_tbl) o2;
                if (venues1.getUsershow_credit() > venues2.getUsershow_credit()) {
                    return 1;
                } else if (venues1.getUsershow_credit() == venues2.getUsershow_credit()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }
}
