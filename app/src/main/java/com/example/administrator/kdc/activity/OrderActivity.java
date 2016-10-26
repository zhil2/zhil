package com.example.administrator.kdc.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Address_tbl;
import com.example.administrator.kdc.vo.InsertOrderBean;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.example.administrator.kdc.vo.Venuesshow_tbl;
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

public class OrderActivity extends AppCompatActivity {


    int user_id;

    Venues_tbl venues_tbl;


    int y, m, d, h, m2, flag = 0;
    String m4, h2, d2, m3;
    Timestamp orderTime;
    double price;
    String name;
    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.btn_basketball)
    ImageButton btnBasketball;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.tv_place)
    TextView tvPlace;
    @InjectView(R.id.et_place)
    EditText etPlace;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.et_price)
    EditText etPrice;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.et_number)
    EditText etNumber;
    @InjectView(R.id.tv_start)
    TextView tvStart;
    @InjectView(R.id.et_start)
    Button etStart;
    @InjectView(R.id.tv_end)
    TextView tvEnd;
    @InjectView(R.id.et_end)
    EditText etEnd;
    @InjectView(R.id.tv_totalprice)
    TextView tvTotalprice;
    @InjectView(R.id.et_totalprice)
    EditText etTotalprice;
    @InjectView(R.id.tv_note)
    TextView tvNote;
    @InjectView(R.id.et_note)
    EditText etNote;
    @InjectView(R.id.btn_send)
    Button btnSend;


    Venues_tbl venuesDetail;
    double eachPrice;//单价
    double Totalprice;//总价
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.inject(this);
        getData();


    }

    //获取数据源
    public void getData() {
        Intent intent = getIntent();
        venues_tbl =intent.getParcelableExtra("venues_tbl");
        etPlace.setText(venues_tbl.getVenues_name());
        etPrice.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price()+"");

//       venuesDetail=new Venues_tbl(1,"name",1,1,new Address_tbl("江苏省","苏州市",1,"吴中区","文荟","高博"),new Venuesshow_tbl(2,2,2,"a",2.0,"a"),1,1,1);
        eachPrice=venues_tbl.getVenuesshow_tbl().getVenuesshow_price();


    }

    @OnClick({R.id.btn_send, R.id.et_start,R.id.btn_back})
    public void onClick(View view) {
        switch (view.getId()) {
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
                insertOrderBean.setOrder_number(Integer.parseInt(etNumber.getText().toString()));
                insertOrderBean.setOrder_time(orderTime);
                insertOrderBean.setOrder_length(Integer.parseInt(etEnd.getText().toString()));
                insertOrderBean.setOrder_note(etNote.getText().toString());
                insertOrderBean.setVenuesDetail(venues_tbl);
                Totalprice=eachPrice*Integer.parseInt(etEnd.getText().toString());
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
                intent.putExtra("orderInfo",orderInfo+"");
                Log.i("GoPayActivity", "onClick: "+orderInfo);
                startActivity(intent);
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
            case R.id.btn_back:
                Intent intent2=new Intent(this,VenuesshowActivity.class);
                startActivity(intent2);
                break;
        }
    }
}

