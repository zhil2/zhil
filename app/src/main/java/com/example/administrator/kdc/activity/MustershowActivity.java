package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Muster_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MustershowActivity extends AppCompatActivity {
    int user_id;
    Muster_tbl muster_tbl;
    String url2;
    ImageLoader myImageLoader;

    @InjectView(R.id.tv_venues)
    TextView tvVenues;
    @InjectView(R.id.et_venues)
    TextView etVenues;
    @InjectView(R.id.et_venues4)
    TextView etVenues4;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.et_time)
    TextView etTime;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.et_number)
    TextView etNumber;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.et_price)
    TextView etPrice;
    @InjectView(R.id.tv_type2)
    TextView tvType2;
    @InjectView(R.id.et_type2)
    TextView etType2;
    @InjectView(R.id.tv_note)
    TextView tvNote;
    @InjectView(R.id.et_note)
    TextView etNote;
    @InjectView(R.id.tv_user)
    TextView tvUser;
    @InjectView(R.id.et_user)
    ImageButton etUser;
    @InjectView(R.id.tv_user2)
    TextView tvUser2;
    @InjectView(R.id.et_user3)
    ImageButton etUser3;
    @InjectView(R.id.et_user4)
    ImageButton etUser4;
    @InjectView(R.id.et_user2)
    ImageButton etUser2;
    @InjectView(R.id.b_go)
    Button bGo;
    @InjectView(R.id.tv_name1)
    TextView tvName1;
    @InjectView(R.id.tv_name4)
    TextView tvName4;
    @InjectView(R.id.tv_name3)
    TextView tvName3;
    @InjectView(R.id.tv_name2)
    TextView tvName2;
    @InjectView(R.id.tv_name5)
    TextView tvName5;
    @InjectView(R.id.et_user5)
    ImageButton etUser5;
    @InjectView(R.id.et_to1)
    ImageButton etTo1;
    @InjectView(R.id.et_to2)
    ImageButton etTo2;


    List<Usershow_tbl> list = new ArrayList<Usershow_tbl>();
    List<Usershow_tbl> list2 = new ArrayList<Usershow_tbl>();

    int flag = 0, flag2 = 0;
    int size = 0;
    int ys = 0;
    @InjectView(R.id.b_go2)
    Button bGo2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mustershow);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        user_id = ((MyApplication) getApplication()).getUser().getUser_id();

      //  muster_tbl = getIntent().getParcelableExtra("Muster_tbl");

         String gsonmuster_tbl=intent.getStringExtra("gsonmuster_tbl");

         Gson gson=new Gson();
         muster_tbl=gson.fromJson(gsonmuster_tbl,Muster_tbl.class);

     //   Log.d("agtzdscb","show  etTime"+muster_tbl.getMuster_time()+"");

        Timestamp timestamp=muster_tbl.getMuster_time();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

        String a=sdf.format(timestamp);
        long millionSeconds = timestamp.getTime();
        long b=millionSeconds+muster_tbl.getMuster_length()*1000*60*60;


        String str = sdf.format(b);


        etVenues.setText(muster_tbl.getVenues_tbl().getVenues_name());

        etTime.setText("时间段:"+a+"~"+str);

        etNumber.setText(muster_tbl.getMuster_number() + "");
        etPrice.setText(muster_tbl.getVenues_tbl().getVenuesshow_tbl().getVenuesshow_price() + "￥人/小时");
        etType2.setText(muster_tbl.getMuster_type());
        etNote.setText(muster_tbl.getMuster_note());
        etVenues4.setText(muster_tbl.getMuster_state());

        url2 = muster_tbl.getUsershow_tbl().getUsershow_head();
        myImageLoader = new ImageLoader(MustershowActivity.this);
        myImageLoader.showImageByUrl(url2, etUser);
        tvName1.setText(muster_tbl.getUsershow_tbl().getUsershow_name());

        //加载参与者信息
        Mustergo();


    //    Log.d("agtzdscb", "  user_id:" + user_id + " muster_tbl.getUser_id():" + muster_tbl.getUser_id());
    }


    public void Mustergo() {

        String url = NetUtil.url + "MustergoServlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("muster_id", muster_tbl.getMuster_id() + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("result", "result" + result);
                Gson gson = new Gson();
                list = gson.fromJson(result,
                        new TypeToken<List<Usershow_tbl>>() {
                        }.getType());
                //初始化
                ys = list.size() / 4;
                size = list.size() % 4;
         //       Log.d("agtzdscb", "初始化  ys:" + ys + " size:" + size + " flag:" + flag + "  list.size()" + list.size());
                Imageto();

                //按钮初始
             //   Log.d("agtzdscb", "  user_id:" + user_id + " muster_tbl.getUser_id():" + muster_tbl.getUsershow_tbl().getUser_id()+"  muster_tbl.getMuster_type()"+muster_tbl.getMuster_state());

                Log.d("dgsjhfj","muster_tbl.getUsershow_tbl().getUser_tbl().getUser_id()"+muster_tbl.getUsershow_tbl().getUser_tbl().getUser_id()+"    user_id"+user_id);

                if(muster_tbl.getMuster_state().equals("正在召集中")) {
                    Log.d("agtzdscb","已经变为正在召集");
                    if (muster_tbl.getUsershow_tbl().getUser_tbl().getUser_id() == user_id) {
                        Log.d("agtzdscb","已经变为取消召集");
                        bGo.setText("取消召集");
                    } else {
                        Log.d("agtzdscb","已经变为申请加入了");

                        bGo.setText("申请加入");//还没有关系时
                    }

                    for (Usershow_tbl usershow_tbl : list) {
                        if (usershow_tbl.getUser_tbl().getUser_id() == user_id) {

                            Log.d("dgsjhfj","usershow_tbl.getUser_tbl().getUser_id() "+usershow_tbl.getUser_tbl().getUser_id() +"    user_id"+user_id);

                            bGo.setText("退出报名");
                        }
                    }

                }else if(muster_tbl.getMuster_state().equals("正在进行中")){
                    if (muster_tbl.getUsershow_tbl().getUser_tbl().getUser_id()  == user_id) {
                        bGo.setText("取消召集");

                    }
                    for (Usershow_tbl usershow_tbl : list) {
                        if (usershow_tbl.getUser_tbl().getUser_id() == user_id) {
                            bGo.setText("中途退出");
                        }
                    }
                }else{//已经结束的
                    if (muster_tbl.getUsershow_tbl().getUser_tbl().getUser_id() == user_id) {
                        bGo.setText("删除召集");
                        tvName5.setVisibility(View.VISIBLE);
                        bGo2.setText("再次发布");
                    }
                    for (Usershow_tbl usershow_tbl : list) {
                        if (usershow_tbl.getUser_tbl().getUser_id() == user_id) {
                            bGo.setText("消除痕迹");
                        }
                    }
                }

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


    public void Imageto() {

      //  Log.d("agtzdscb", "判断  flag:" + flag + " size:" + size);

        if (ys == flag) {

            Log.d("agtzdscb", "没有下一页了2  flag:" + flag + " size:" + size);
            etUser2.setVisibility(View.INVISIBLE);
            tvName2.setVisibility(View.INVISIBLE);
            etUser3.setVisibility(View.INVISIBLE);
            tvName3.setVisibility(View.INVISIBLE);
            etUser4.setVisibility(View.INVISIBLE);
            tvName4.setVisibility(View.INVISIBLE);
            etUser5.setVisibility(View.INVISIBLE);
            tvName5.setVisibility(View.INVISIBLE);

            switch (-size + 4) {
                case 0:
                    Log.d("agtzdscb", "size+1111111");
                    url2 = list.get(3 + flag * 4).getUsershow_head();
                    myImageLoader.showImageByUrl(url2, etUser5);
                    tvName5.setText(list.get(3 + flag * 4).getUsershow_name());
                    etUser5.setVisibility(View.VISIBLE);
                    tvName5.setVisibility(View.VISIBLE);

                case 1:
                    Log.d("agtzdscb", "size+22222222");
                    url2 = list.get(2 + flag * 4).getUsershow_head();
                    myImageLoader.showImageByUrl(url2, etUser4);
                    tvName4.setText(list.get(2 + flag * 4).getUsershow_name());
                    etUser4.setVisibility(View.VISIBLE);
                    tvName4.setVisibility(View.VISIBLE);
                case 2:
                    Log.d("agtzdscb", "size+3333");
                    url2 = list.get(1 + flag * 4).getUsershow_head();
                    myImageLoader.showImageByUrl(url2, etUser3);
                    tvName3.setText(list.get(1 + flag * 4).getUsershow_name());
                    etUser3.setVisibility(View.VISIBLE);
                    tvName3.setVisibility(View.VISIBLE);
                case 3:
                    Log.d("agtzdscb", "size+44444");
                    url2 = list.get(0 + flag * 4).getUsershow_head();
                    myImageLoader.showImageByUrl(url2, etUser2);
                    tvName2.setText(list.get(0 + flag * 4).getUsershow_name());
                    etUser2.setVisibility(View.VISIBLE);
                    tvName2.setVisibility(View.VISIBLE);
                    break;

            }
            flag--;
            flag2 = 1;

        } else if (ys > flag) {
            url2 = list.get(3 + flag * 4).getUsershow_head();
            myImageLoader.showImageByUrl(url2, etUser5);
            tvName5.setText(list.get(3 + flag * 4).getUsershow_name());
            etUser5.setVisibility(View.VISIBLE);
            tvName5.setVisibility(View.VISIBLE);
            url2 = list.get(2 + flag * 4).getUsershow_head();
            myImageLoader.showImageByUrl(url2, etUser4);
            tvName4.setText(list.get(2 + flag * 4).getUsershow_name());
            etUser4.setVisibility(View.VISIBLE);
            tvName4.setVisibility(View.VISIBLE);
            url2 = list.get(1 + flag * 4).getUsershow_head();
            myImageLoader.showImageByUrl(url2, etUser3);
            tvName3.setText(list.get(1 + flag * 4).getUsershow_name());
            etUser3.setVisibility(View.VISIBLE);
            tvName3.setVisibility(View.VISIBLE);
            url2 = list.get(0 + flag * 4).getUsershow_head();
            myImageLoader.showImageByUrl(url2, etUser2);
            tvName2.setText(list.get(0 + flag * 4).getUsershow_name());
            etUser2.setVisibility(View.VISIBLE);
            tvName2.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.et_to1, R.id.et_to2, R.id.b_go,R.id.b_go2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.et_to1:
                if (flag == -1) {
                    flag++;
                }

//                if (flag2 == 1) {
//                    //    Log.d("agtzdscb", "处在最后一页，已经减过"+flag);
//                    flag2 = 0;
//                }

                if (flag != 0) {
                    Log.d("agtzdscb", "可以减"+flag);
                    flag--;
                    Imageto();
                }

                break;
            case R.id.et_to2:
                //    Log.d("agtzdscb", "加"+flag);
                flag++;
                Imageto();
                break;
            case R.id.b_go:

                String url = NetUtil.url + "MustergoServlet2";//访问网络的url
                RequestParams requestParams = new RequestParams(url);
                requestParams.addQueryStringParameter("user_id", user_id + "");
                requestParams.addQueryStringParameter("muster_id", muster_tbl.getMuster_id() + "");
                requestParams.addQueryStringParameter("type", bGo.getText().toString()+"");

                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {

                        Mustergo();
                        finish();
                        Toast.makeText(MustershowActivity.this, result, Toast.LENGTH_SHORT).show();
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

                break;
            case R.id.b_go2:
                if (bGo2.getText().equals("再次发布")) {
                }
                String url2 = NetUtil.url + "VenuesServlet2";//访问网络的url
                RequestParams requestParams2 = new RequestParams(url2);
                requestParams2.addQueryStringParameter("venues_id", muster_tbl.getMuster_id() +"");
                x.http().get(requestParams2, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Gson gson = new Gson();
                        Venues_tbl venues_tbl = gson.fromJson(result,Venues_tbl.class);
                        Intent intent2 = new Intent(MustershowActivity.this, MusterActivity.class);
                        intent2.putExtra("venues_tbl",venues_tbl);//发送数据
                        startActivity(intent2);
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

                break;
        }

    }
}
