package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.framet.BaseFragment;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Muster_tbl;
import com.example.administrator.kdc.vo.Reply_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.example.administrator.kdc.vo.VC_tbl;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.example.administrator.kdc.vo.Venuesshow_tbl;
import com.example.administrator.kdc.vo.Venuestime_tbl;
import com.example.administrator.kdc.widget.NoScrollListview;
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

public class VenuesshowActivity extends AppCompatActivity {
    List<Venuesshow_tbl> venuesshowList = new ArrayList<Venuesshow_tbl>();

    String url2;
    ImageLoader myImageLoader;

    int user_id;

    Venues_tbl venues_tbl;
    VC_tbl vc_tbl;
    int good, no, yn, sc;
    int flag = 0, flag2 = 0;


    Usershow_tbl usershow_tbl = null;
    Venuestime_tbl venuestime_tbl = null;
    //评价list
    CommonAdapter<Reply_tbl> orderAdapter;
    List<Reply_tbl> venueslist = new ArrayList<Reply_tbl>();

    //召集list
    List<Muster_tbl> muster = new ArrayList<Muster_tbl>();
    CommonAdapter<Muster_tbl> orderAdapter2;

    List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    @InjectView(R.id.iv_portrait)
    ImageView ivPortrait;
    @InjectView(R.id.textView9)
    TextView textView9;
    @InjectView(R.id.textView20)
    TextView textView20;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_user)
    TextView tvUser;
    @InjectView(R.id.relativeLayout9)
    RelativeLayout relativeLayout9;
    @InjectView(R.id.tv_order)
    TextView tvOrder;
    @InjectView(R.id.tv_muster)
    TextView tvMuster;
    @InjectView(R.id.im_yes)
    ImageButton imYes;
    @InjectView(R.id.tv_yes1)
    TextView tvYes1;
    @InjectView(R.id.textView19)
    TextView textView19;
    @InjectView(R.id.im_no)
    ImageButton imNo;
    @InjectView(R.id.ib_collection)
    ImageButton ibCollection;
    @InjectView(R.id.tv_sports)
    TextView tvSports;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.tv_current)
    TextView tvCurrent;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.textView8)
    TextView textView8;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.xxx)
    TextView xxx;
    @InjectView(R.id.ev_pj)
    EditText evPj;
    @InjectView(R.id.b_pj)
    Button bPj;
    @InjectView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @InjectView(R.id.vp_pj)
    NoScrollListview vpPj;
    @InjectView(R.id.tv_evaluation)
    TextView tvEvaluation;
    @InjectView(R.id.xxx2)
    TextView xxx2;
    @InjectView(R.id.vp_zj)
    NoScrollListview vpZj;
    @InjectView(R.id.tv_lookmuster)
    TextView tvLookmuster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venuesshow);
        ButterKnife.inject(this);

        user_id = ((MyApplication) getApplication()).getUsershow().getUser_tbl().getUser_id();
        init();
        getOrderData();
        getOrderData2();

    }

    public void init() {
        Intent intent = getIntent();

        vc_tbl = getIntent().getParcelableExtra("vc_tbl");
        sc = intent.getIntExtra("sc", -1);
        good = intent.getIntExtra("yes", -1);
        no = intent.getIntExtra("no", -1);
        yn = intent.getIntExtra("yn", -1);

        venues_tbl = vc_tbl.getVenues_tbl();


        RequestParams params = new RequestParams(NetUtil.url + "UsershowServlet");
        params.addBodyParameter("user_id", venues_tbl.getVenuesshow_tbl().getUser_id() + "");

        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                usershow_tbl = gson.fromJson(result, Usershow_tbl.class);


                tvUser.setText("发布者:" + usershow_tbl.getUsershow_name() + "");


                String b = "";


                switch (venues_tbl.getVenues_type()) {
                    case 1:
                        b = "支持预约的付费场地";
                        tvCurrent.setText(" 容量：" + venues_tbl.getVenues_current() + "/" + venues_tbl.getVenues_ceiling());
                        tvPrice.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price() + "￥每人/每小时");

                        break;
                    case 2:
                        b = "不支持预约的付费场地";
                        tvCurrent.setText(" 容量：" + venues_tbl.getVenues_ceiling());
                        tvPrice.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price() + "￥每人/每小时");
                        tvOrder.setText("无法预约");
                        break;
                    case 3:
                        b = "免费的野场地";
                        tvCurrent.setText(" 容量：" + venues_tbl.getVenues_ceiling());
                        tvPrice.setText("免费");
                        tvOrder.setText("无法预约");
                        break;
                }

                if (user_id == usershow_tbl.getUser_tbl().getUser_id()) {//自己发布的场馆
                    tvOrder.setText("场馆管理");
                }

                //        Log.d("vgdhrtyyrj","     usershow_tbl.getUsershow_name()"+usershow_tbl.getUsershow_name());

                textView9.setText("" + venues_tbl.getVenues_name());

                tvType.setText("" + b);


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


        RequestParams params2 = new RequestParams(NetUtil.url + "VenuestimeServlet");
        params2.addBodyParameter("venues_id", venues_tbl.getVenues_id() + "");

        x.http().post(params2, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                Log.d("dfghdfnxcv", "result" + result);

                Gson gson = new Gson();
                venuestime_tbl = gson.fromJson(result, Venuestime_tbl.class);

                textView20.setText("开馆时间：" + venuestime_tbl.getVenuestime_kg() + "~" + venuestime_tbl.getVenuestime_bg());

                Log.d("dfghdfnxcv", "venuestime_tbl.getVenuestime_kg()" + venuestime_tbl.getVenuestime_kg());

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


        goodorno();

        url2 = venues_tbl.getVenuesshow_tbl().getVenuesshow_portrait();
        myImageLoader = new ImageLoader(VenuesshowActivity.this);
        myImageLoader.showImageByUrl(url2, ivPortrait);
        tvNumber.setText(" 联系电话：" + venues_tbl.getVenuesshow_tbl().getVenuesshow_number());

        myImageLoader.showImageByUrl(url2, imageView4);
        tvAddress.setText(" 地址:" + venues_tbl.getAddress_tbl().getAddress_city() + "" + venues_tbl.getAddress_tbl().getAddress_county() + "" + venues_tbl.getAddress_tbl().getAddress_town() + "" + venues_tbl.getAddress_tbl().getAddress_show());

        Log.d("dfdsaf", "sc    " + sc);
        if (sc != -1) {
            ibCollection.setBackgroundResource(R.drawable.sc);
        } else {
            ibCollection.setBackgroundResource(R.drawable.sc2);
        }

        if (yn == 1) {
            flag = 1;
            imNo.setBackgroundResource(R.drawable.no);
            imYes.setBackgroundResource(R.drawable.good2);
        } else if (yn == 2) {
            flag = 2;
            imNo.setBackgroundResource(R.drawable.no2);
            imYes.setBackgroundResource(R.drawable.good);
        } else {
            flag = 0;
            imNo.setBackgroundResource(R.drawable.no);
            imYes.setBackgroundResource(R.drawable.good);
        }


    }


    @OnClick({R.id.tv_order, R.id.tv_muster, R.id.im_yes, R.id.im_no, R.id.ib_collection, R.id.tv_evaluation, R.id.tv_lookmuster})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order:

                if (tvOrder.getText().equals("我要预约")) {
                    Intent intent = new Intent(VenuesshowActivity.this, OrderActivity.class);
                    intent.putExtra("venues_tbl", venues_tbl);
                    startActivity(intent);
                } else if (tvOrder.getText().equals("场馆管理")) {

                    Intent intent = new Intent(VenuesshowActivity.this, ManagementActivity.class);
                    intent.putExtra("venues_tbl", venues_tbl);
                    intent.putExtra("venuestime_tbl", venuestime_tbl);
                    startActivityForResult(intent, 1);

                }

                break;
            case R.id.tv_muster:

                Intent intent2 = new Intent(VenuesshowActivity.this, MusterActivity.class);
                intent2.putExtra("venues_tbl", venues_tbl);//发送数据
                startActivity(intent2);

                break;

            case R.id.im_yes:
                if (flag == 1) {
                    flag = 0;
                    good--;
                } else if (flag == 0) {
                    flag = 1;
                    good++;
                } else {
                    flag = 1;
                    good++;
                    no--;
                }
                goodorno();
                evaluation(flag);
                break;
            case R.id.im_no:
                if (flag == 2) {
                    flag = 0;
                    no--;
                } else if (flag == 0) {
                    flag = 2;
                    no++;
                } else {
                    flag = 2;
                    no++;
                    good--;
                }
                goodorno();
                evaluation(flag);
                break;

            case R.id.ib_collection:

                sc(user_id, venues_tbl.getVenues_id(), 0, view);

                break;
            case R.id.tv_evaluation:

                Intent intent4 = new Intent(VenuesshowActivity.this, EvaluationActivity.class);
                intent4.putExtra("venues_id", venues_tbl.getVenues_id());
                startActivity(intent4);
                break;

            case R.id.tv_lookmuster:
                Intent intent3 = new Intent(VenuesshowActivity.this, MusterlistActivity.class);
                intent3.putExtra("venues_id", venues_tbl.getVenues_id() + "");
                startActivity(intent3);
                break;
        }

    }

    public void evaluation(int flag) {
        RequestParams params = new RequestParams(NetUtil.url + "EvaluationServlet");
        params.addBodyParameter("flag", flag + "");//post方法的传值
        params.addBodyParameter("user_id", user_id + "");
        params.addBodyParameter("type", 2 + "");
        params.addBodyParameter("Venues_id", venues_tbl.getVenues_id() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
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

    public void goodorno() {
        // Log.d("asdfad","good"+good+"  no"+no);
        if (flag == 0) {
            imYes.setBackgroundResource(R.drawable.good);
            imNo.setBackgroundResource(R.drawable.no);
        } else if (flag == 1) {
            imYes.setBackgroundResource(R.drawable.good2);
            imNo.setBackgroundResource(R.drawable.no);
        } else {
            imYes.setBackgroundResource(R.drawable.good);
            imNo.setBackgroundResource(R.drawable.no2);
        }
        tvYes1.setText("" + good);
        textView19.setText("" + no);
    }


    public void sc(int user_id, int collection_object, int flag, final View v) {

        RequestParams params = new RequestParams(NetUtil.url + "CollectionServlet");
        params.addBodyParameter("user_id", user_id + "");//post方法的传值
        params.addBodyParameter("collection_type", 1 + "");
        params.addBodyParameter("collection_object", collection_object + "");
        params.addBodyParameter("flag", flag + "");

        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                if (result.equals("收藏成功")) {
                    v.setBackgroundResource(R.drawable.sc);
                } else {
                    v.setBackgroundResource(R.drawable.sc2);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("BBBBB", "u3  get no");

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent date) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {

                    String venues_name = date.getStringExtra("venues_name");
                    vc_tbl.getVenues_tbl().setVenues_name(venues_name);
                    String venues_ceiling = date.getStringExtra("venues_ceiling");
                    vc_tbl.getVenues_tbl().setVenues_ceiling(Integer.parseInt(venues_ceiling));
                    String venues_current = date.getStringExtra("venues_current");
                    vc_tbl.getVenues_tbl().setVenues_current(Integer.parseInt(venues_current));
                    String venues_kg = date.getStringExtra("venues_kg");
                    venuestime_tbl.setVenuestime_kg(venues_kg);
                    String venues_bg = date.getStringExtra("venues_bg");
                    venuestime_tbl.setVenuestime_bg(venues_bg);
                    String venues_type = date.getStringExtra("venues_type");
                    String venuesshow_price = date.getStringExtra("venuesshow_price");
                    vc_tbl.getVenues_tbl().getVenuesshow_tbl().setVenuesshow_price(Double.parseDouble(venuesshow_price));
                    String venuesshow_number = date.getStringExtra("venuesshow_number");
                    vc_tbl.getVenues_tbl().getVenuesshow_tbl().setVenuesshow_number(Integer.parseInt(venuesshow_number));

                    textView20.setText("开放时间：" + venues_kg + "~" + venues_bg);

                    Log.d("dfghdfnxcv", "ve    venues_name" + venues_name);

                    if (venues_type.equals("支持预约的付费场地")) {
                        vc_tbl.getVenues_tbl().setVenues_type(1);
                        tvType.setText("支持预约的付费场地");
                        tvCurrent.setText(" 容量：" + venues_current + "/" + venues_ceiling);
                        tvPrice.setText(venuesshow_price + "￥每人/每小时");
                    } else if (venues_type.equals("不支持预约的付费场地")) {
                        vc_tbl.getVenues_tbl().setVenues_type(2);
                        tvType.setText("不支持预约的付费场地");
                        tvCurrent.setText(" 容量：" + venues_ceiling);
                        tvPrice.setText(venuesshow_price + "￥每人/每小时");

                    } else {
                        vc_tbl.getVenues_tbl().setVenues_type(3);
                        tvType.setText("免费的野场地");
                        tvCurrent.setText(" 容量：" + venues_ceiling);
                        tvPrice.setText("免费");

                    }
                    textView9.setText("" + venues_name);
                    tvNumber.setText("联系电话" + venuesshow_number);
                }
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(2, intent);
        finish();
    }

    public void getOrderData() {
        RequestParams params = new RequestParams(NetUtil.url + "ReplyServlet");
        params.addBodyParameter("venues_id", venues_tbl.getVenues_id() + "");
        params.addBodyParameter("ys", 1 + "");//场馆的回复最多加载8条

        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
                Log.e("BBBBB", "u3  get yes+" + result);
                Gson gson = new Gson();
                List<Reply_tbl> newOrders = gson.fromJson(result, new TypeToken<List<Reply_tbl>>() {
                }.getType());
                venueslist.clear();
                venueslist.addAll(newOrders);//添加新的集合
                Log.i("elav", "onSuccess: venueslist.size" + venueslist.size());

                if (orderAdapter == null) {
                    // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
                    orderAdapter = new CommonAdapter<Reply_tbl>(VenuesshowActivity.this, venueslist, R.layout.list_a) {
                        @Override
                        public void convert(final ViewHolder viewHolder, final Reply_tbl item2, final int position) {

                            TextView venues_name = viewHolder.getViewById(R.id.tv_name);
                            TextView tv_time = viewHolder.getViewById(R.id.tv_time);
                            TextView tv_nr = viewHolder.getViewById(R.id.tv_nr);
                            ImageView iv_tx = viewHolder.getViewById(R.id.iv_tx);

                            venues_name.setText(" " + item2.getUsershow_tbl().getUsershow_name());
                            tv_time.setText(" " + item2.getReply_date());
                            tv_nr.setText(" " + item2.getReply_text());

                            url2 = item2.getUsershow_tbl().getUsershow_head();
                            myImageLoader = new ImageLoader(VenuesshowActivity.this);
                            myImageLoader.showImageByUrl(url2, iv_tx);

                        }
                    };

                    vpPj.setAdapter(orderAdapter);
                } else {
                    orderAdapter.notifyDataSetChanged();
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


    @OnClick(R.id.b_pj)
    public void onClick() {

        if (evPj.getText().length() == 0) {
            Toast.makeText(VenuesshowActivity.this, "评论不能为空", Toast.LENGTH_SHORT).show();
        } else if (evPj.getText().length() > 100) {
            Toast.makeText(VenuesshowActivity.this, "评论过长，请输入100个以内的字符", Toast.LENGTH_SHORT).show();
        } else {
            RequestParams params = new RequestParams(NetUtil.url + "ReplyServlet2");
            params.addBodyParameter("user_id", user_id + "");//post方法的传值
            params.addBodyParameter("venues_id", venues_tbl.getVenues_id() + "");
            params.addBodyParameter("reply_text", evPj.getText() + "");

            x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                @Override
                public void onSuccess(String result) {

                    getOrderData();
                    Toast.makeText(VenuesshowActivity.this, result, Toast.LENGTH_SHORT).show();
                    evPj.setText("");

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
    }

    public void getOrderData2() {
        orderAdapter2 = null;
        RequestParams requestParams = new RequestParams(NetUtil.url + "MusterSelectServlet");
        requestParams.addQueryStringParameter("userId", user_id + "");
        requestParams.addQueryStringParameter("muster_state", "本馆召集");//状态为0表示查询全部的订单信息
        requestParams.addQueryStringParameter("venues_id", venues_tbl.getVenues_id() + "");
        requestParams.addQueryStringParameter("address_id", ((MyApplication) getApplication()).getUsershow().getAddress_id() + "");

        //获取网络数据，获取到之后，设置数据源
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                List<Muster_tbl> newOrders = gson.fromJson(result, new TypeToken<List<Muster_tbl>>() {
                }.getType());

                for (Muster_tbl newOrder : newOrders) {
                    if (newOrder.getMuster_state().equals("不在显示")) {
                        newOrders.remove(newOrder);
                    }
                }
                ;
                muster.clear();
                muster.addAll(newOrders);//添加新的集合
                //  fragAllordersListview.setEmptyView(fragAllordersRl);//设置没有数据时，显示
                if (orderAdapter2 == null) {
                    // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
                    orderAdapter2 = new CommonAdapter<Muster_tbl>(VenuesshowActivity.this, muster, R.layout.list_muster) {
                        @Override
                        public void convert(ViewHolder viewHolder, final Muster_tbl item, final int position) {

                            Log.d("111222", "item.getMuster_number()" + item.getMuster_number());

                            TextView tvCurrent = viewHolder.getViewById(R.id.tv_current);
                            tvCurrent.setText("召集人数 1/" + item.getMuster_number());//人数

                            TextView tvNameMuster = viewHolder.getViewById(R.id.tv_name_muster);//地址
                            tvNameMuster.setText(" 场馆名:" + item.getVenues_tbl().getVenues_name());

                            TextView tvsernameMuster = viewHolder.getViewById(R.id.tv_username_muster);
                            tvsernameMuster.setText("发布者：" + item.getUsershow_tbl().getUsershow_name());//发布者

                            TextView tvType2 = viewHolder.getViewById(R.id.tv_type2);
                            tvType2.setText("" + item.getMuster_state());//类型

                            TextView tvType = viewHolder.getViewById(R.id.tv_type);
                            tvType.setText("费用方式" + item.getMuster_type());//类型

                            TextView tvTime = viewHolder.getViewById(R.id.tv_time);
                            tvTime.setText("时间" + item.getMuster_time());//类型


                            ImageLoader myImageLoader;
                            ImageView imageView = viewHolder.getViewById(R.id.imageView);
                            String url2 = item.getUsershow_tbl().getUsershow_head();
                            myImageLoader = new ImageLoader(VenuesshowActivity.this);
                            myImageLoader.showImageByUrl(url2, imageView);

                            //具体按钮显示（文本），及点击事件
                            Button btnRight = viewHolder.getViewById(R.id.b_1);
                            btnRight.setFocusable(false);
                            Button btnLeft = viewHolder.getViewById(R.id.b_2);
                            btnLeft.setFocusable(false);
                            //根据订单状态，判断当前显示的文本
                            //         user_id= ((MyApplication) getActivity().getApplication()).getUser().getUser_id();
                            btnLeft.setVisibility(View.VISIBLE);

                            switch (item.getMuster_state()) {
                                case "正在召集中":
                                    btnLeft.setText("申请加入");
                                    break;
                                case "正在进行中":

                                    btnLeft.setText("中途退出");
                                    break;
                                case "已经结束的":
                                    btnLeft.setText("消除痕迹");
                                    break;
                            }
                            //设置按钮点击事件
                            btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (item.getMuster_state()) {
                                        case "正在召集中0":
                                            go(item, "申请加入");
                                            break;
                                        case "正在进行中":
                                            go(item, "中途退出");
                                            break;
                                        case "已经结束的":
                                            go(item, "消除痕迹");
                                            break;
                                    }
                                }
                            });

                            btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (item.getMuster_state()) {
                                        case "已经结束的":
                                            //    go(item,"再次发布");


                                            break;
                                    }
                                }
                            });

                        }
                    };

                    //listview中显示的是所有的数据信息
                    vpZj.setAdapter(orderAdapter2);

                    vpZj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override//点击此view进行界面的跳转
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Muster_tbl muster_tbl = muster.get(position);

                            Intent intent = new Intent(VenuesshowActivity.this, MustershowActivity.class);
                            Gson gson = new Gson();
                            String gsonmuster_tbl = gson.toJson(muster_tbl);
                            intent.putExtra("gsonmuster_tbl", gsonmuster_tbl);//发送数据
                            Log.d("agtzdscb", "frage  etTime" + muster_tbl.getMuster_time() + "");
                            startActivity(intent);

                        }
                    });

                } else {
                    orderAdapter2.notifyDataSetChanged();
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

    public void go(Muster_tbl muster_tbl, String type) {

        String url = NetUtil.url + "MustergoServlet2";//访问网络的url
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("user_id", user_id + "");
        requestParams.addQueryStringParameter("muster_id", muster_tbl.getMuster_id() + "");
        requestParams.addQueryStringParameter("type", type + "");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                getOrderData2();
                Toast.makeText(VenuesshowActivity.this, result, Toast.LENGTH_SHORT).show();
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


}
