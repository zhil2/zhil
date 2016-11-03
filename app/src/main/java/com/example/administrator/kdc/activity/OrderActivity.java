package com.example.administrator.kdc.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;


import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.InsertOrderBean;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Allen on 2016/11/1.
 */
public class OrderActivity extends AppCompatActivity {
    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv1)
    TextView tv1;
    @InjectView(R.id.tv_place)
    TextView tvPlace;
    @InjectView(R.id.rl)
    RelativeLayout rl;
    @InjectView(R.id.tv2)
    TextView tv2;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.textView25)
    TextView textView25;
    @InjectView(R.id.rl2)
    RelativeLayout rl2;
    @InjectView(R.id.tv3)
    TextView tv3;
    @InjectView(R.id.et_start)
    Button etStart;
    @InjectView(R.id.tv4)
    TextView tv4;
    @InjectView(R.id.tv_long)
    EditText tvLong;
    @InjectView(R.id.textView26)
    TextView textView26;
    @InjectView(R.id.rl4)
    RelativeLayout rl4;
    @InjectView(R.id.tv5)
    TextView tv5;
    @InjectView(R.id.et_beizhu)
    EditText etBeizhu;
    @InjectView(R.id.rl5)
    RelativeLayout rl5;
    @InjectView(R.id.btn_send)
    Button btnSend;

    Venues_tbl venuesDetail;
    int user_id;

    Venues_tbl venues_tbl;


    int y, m, d, h, m2, flag = 0;
    String m4, h2, d2, m3;
    Timestamp orderTime;
    double eachPrice;//单价
    double Totalprice;//总价
    int i;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.inject(this);
        getData();
        if (i!=1){

        }else{
            textView25.setText("元/每小时");
            tv4.setText("预约时长:");
            textView26.setText("小时");
        }
        //随着输入的值而改变
        tvLong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("aaa", "beforeTextChanged: "+"1");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("aaa", "beforeTextChanged: "+"2");
            }

            @Override
            public void afterTextChanged(Editable s) {
                Totalprice = eachPrice * Integer.parseInt(s.toString());
                btnSend.setText("总价:" + Totalprice + "元，发送预约单");
                Log.i("aaa", "beforeTextChanged: "+"3");
            }
        });

    }

    //获取数据源
    public void getData() {
        Intent intent = getIntent();
        venues_tbl =intent.getParcelableExtra("venues_tbl");
        tvPlace.setText(venues_tbl.getVenues_name());
        tvPrice.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price()+"");
        i=1;
//       venuesDetail=new Venues_tbl(1,"name",1,1,new Address_tbl("江苏省","苏州市",1,"吴中区","文荟","高博"),new Venuesshow_tbl(2,2,2,"a",2.0,"a"),1,1,1);
        eachPrice=venues_tbl.getVenuesshow_tbl().getVenuesshow_price();


    }

    @OnClick({R.id.btn_back, R.id.et_start, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent2=new Intent(this,VenuesshowActivity.class);
                startActivity(intent2);
                break;
            case R.id.et_start:

                Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
                t.setToNow(); // 取得系统时间。
                y = t.year;
                m = t.month;
                d = t.monthDay;
                h = t.hour;
                m2 = t.minute;
                Log.d("time", "time3" + y + "-" + m3 + "-" + d2 + " " + h + ":" + m + ":00"+"111111111");

                TimePickerDialog time = new TimePickerDialog(OrderActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        h = hourOfDay;
                        h2 = h + "";
                        if (h <= 9) {
                            h2 = "0" + h;
                        }
                        m2 = minute;
                        m4 = m2 + "";
                        if (m2 <= 9) {
                            m4 = "0" + m2;
                        }
                        Log.d("time", "time2" + y + "-" + m3 + "-" + d2 + " " + h + ":" + m + ":00"+"22222");
                        etStart.setText(y + "-" + m3 + "-" + d2 + " " + h2 + ":" + m4 + ":00");
                    }

                }, h, m2, true);
                time.show();


                DatePickerDialog datePicker = new DatePickerDialog(OrderActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        y = year;
                        m = monthOfYear +1;
                        d = dayOfMonth;
                        m3 = m + "";
                        if (m <= 9) {
                            m3 = "0" + m;
                        }
                        d2 = d + "";
                        if (d <= 9) {
                            d2 = "0" + d;
                        }
                    }
                }, y, m, d);
                datePicker.show();
                break;

            case R.id.btn_send:
                String str = etStart.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");

                long millionSeconds = 0;
                try {
                    millionSeconds = sdf.parse(str).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //  System.out.println("millionSeconds   "+millionSeconds);//millionSeconds是字符串
                orderTime = new Timestamp(millionSeconds);
                //   System.out.println("a   "+a);

                //访问网络
                RequestParams requestParams = new RequestParams(NetUtil.url+ "OrderInsertServlet");

                //添加到网络
                InsertOrderBean insertOrderBean = new InsertOrderBean();
                insertOrderBean.setUser_id(((MyApplication) getApplication()).getUser().getUser_id());
//                insertOrderBean.setOrder_number(Integer.parseInt(etNumber.getText().toString()));
                insertOrderBean.setOrder_time(orderTime);
                insertOrderBean.setOrder_length(Integer.parseInt(tvLong.getText().toString()));
                insertOrderBean.setOrder_note(etBeizhu.getText().toString());
                insertOrderBean.setVenuesDetail(venues_tbl);

                insertOrderBean.setTotal_price(Totalprice);
                Log.i("time2", "onClick: "+orderTime);
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();//传有带时间的参数  得注意
                String orderInfo = gson.toJson(insertOrderBean);

                Log.d("wetsgrwe", "orderInfo" + orderInfo);
                requestParams.addQueryStringParameter("orderInfo", orderInfo);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("ProdOrderActivity", "onSuccess: " + result);
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
                //跳转到支付界面
                Intent intent = new Intent(this, GoPayActivity.class);
                intent.putExtra("money",Totalprice);
                Log.i("GoPayActivity", "onClick: "+Totalprice);
                startActivity(intent);
                break;
        }
    }
}
