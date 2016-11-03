package com.example.administrator.kdc.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.InsertOrderBean;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.example.administrator.kdc.wheelview.DateUtils;
import com.example.administrator.kdc.wheelview.JudgeDate;
import com.example.administrator.kdc.wheelview.ScreenInfo;
import com.example.administrator.kdc.wheelview.WheelWeekMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    //时间选择器的控件

    private TextView tv_center;
    private WheelWeekMain wheelWeekMainDate;
    private String beginTime;
    String a="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.inject(this);

        tv_center = (TextView) findViewById(R.id.tv_center);


        getData();

        if (i!=1){
            a="预约人数";
        }else{
            a="预约时长";
            textView25.setText("元/每小时");
            tv4.setText("预约时长:");
            textView26.setText("小时");
        }
        //随着输入的值而改变
        tvLong.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
              //  Log.i("aaa", "beforeTextChanged: "+"1");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
             //   Log.i("aaa", "beforeTextChanged: "+"2");
            }

            @Override
            public void afterTextChanged(Editable s) {
                int x=0;
                if (!s.toString().equals("")){
                    x=Integer.parseInt(s.toString());
                }

                Totalprice = eachPrice * x;
                btnSend.setText("总价:" + Totalprice + "元，发送预约单");
               // Log.i("aaa", "beforeTextChanged: "+"3");
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
                showWeekBottoPopupWindow();
                  break;

            case R.id.btn_send:

             //   Log.d("dvsdtw","etStart.getText()"+etStart.getText()+"   tvLong.getText()"+tvLong.getText());

                if(etStart.getText().equals("请 选 择 开 始 时 间")){
                    Toast.makeText(OrderActivity.this, "您还未确定开始时间", Toast.LENGTH_SHORT).show();
                }else if(tvLong.getText().toString().equals("")||tvLong.getText().toString()==null){
                    Toast.makeText(OrderActivity.this, "您还未填写"+a, Toast.LENGTH_SHORT).show();
                }else {

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
                    RequestParams requestParams = new RequestParams(NetUtil.url + "OrderInsertServlet");
                    //添加到网络
                    InsertOrderBean insertOrderBean = new InsertOrderBean();
                    insertOrderBean.setUser_id(((MyApplication) getApplication()).getUser().getUser_id());
//                insertOrderBean.setOrder_number(Integer.parseInt(etNumber.getText().toString()));
                    insertOrderBean.setOrder_time(orderTime);
                    insertOrderBean.setOrder_length(Integer.parseInt(tvLong.getText().toString()));
                    insertOrderBean.setOrder_note(etBeizhu.getText().toString());
                    insertOrderBean.setVenuesDetail(venues_tbl);

                    insertOrderBean.setTotal_price(Totalprice);
                    Log.i("time2", "onClick: " + orderTime);
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
                    intent.putExtra("orderInfo", orderInfo);
                    Log.i("GoPayActivity", "onClick: " + Totalprice);
                    startActivity(intent);
                }

                break;
        }
    }



    private java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void showWeekBottoPopupWindow() {
        WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_week_popup_window,null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, (int)(width*0.8),
                ActionBar.LayoutParams.WRAP_CONTENT);
        ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelWeekMainDate = new WheelWeekMain(menuView, true);
        wheelWeekMainDate.screenheight = screenInfoDate.getHeight();
        String time = DateUtils.currentMonth().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-DD")) {
            try {
                calendar.setTime(new Date(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        wheelWeekMainDate.initDateTimePicker(year, month, day, hours,minute);
        final String currentTime = wheelWeekMainDate.getTime().toString();
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.showAtLocation(tv_center, Gravity.CENTER, 0, 0);
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        backgroundAlpha(0.6f);
        TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
        TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
        TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
        tv_pop_title.setText("选择起始时间");
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        tv_ensure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                beginTime = wheelWeekMainDate.getTime().toString();
                String aa=DateUtils.formateStringH(beginTime,DateUtils.yyyyMMddHHmm);
                String[]  strs=aa.split("[\\(（][\\s\\S]*[\\)）]");
                etStart.setText(strs[0]+strs[1]);
                mPopupWindow.dismiss();
                backgroundAlpha(1f);

            }
        });
    }

    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }

    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


}
