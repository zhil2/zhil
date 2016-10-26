package com.example.administrator.kdc.Community.DaySign;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class DaySign extends AppCompatActivity {
    private TextView sign;//签到
    private TextView showdate;//展示当前时间
    private GridView myDate;//view视图
    //public TextView sign;//签到按钮
    Time nowTime = new Time();//获取当前时间
    private int dayMaxNum;//每月的天数
    private int year;//年份
    private int month;//月份
    private int day;//当前日期
    private int days=0;//签到天数
    private static List<String> list = new ArrayList<String>();//每月天数集合
    public static ArrayList<HashMap<String, Object>> sinlist=new ArrayList<HashMap<String, Object>>();
   // public  int user_id=3;
    public  int lstDay;
   // public static int count=0;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_sign);
        init();
        initData();
        initEvent();
    }
    private void init(){
        sign = (TextView) this.findViewById(R.id.sign);//签到
        showdate = (TextView)this.findViewById(R.id.show);//当前日期
        myDate = (GridView)this.findViewById(R.id.myDate);//日历表
        //取本地时间（时间应该从服务器获取）
        nowTime.setToNow();
        year = nowTime.year;
        month = nowTime.month+1;
        day = nowTime.monthDay;
        showdate.setText(year+"-"+month+"-"+day);
    }
    public void initData() {//初始化当前界面
        //getData("",((MyApplication)DaySign.this.getApplication()).getUser().getUser_id(), year+""+month);//获取当前月份的状态
        Log.i("lstDay222", "getView:lstDay txtDay  333555552" + lstDay + "---------" + "---------" +year+""+month);
        String url = URL.url + "Sign_tbl_getAll_Servlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("sign_date", "");
        requestParams.addQueryStringParameter("user_id", ((MyApplication)DaySign.this.getApplication()).getUser().getUser_id()+ "");
        requestParams.addQueryStringParameter("sign_yearmonth", year+""+month);
        x.http().get(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("Sign_tbl_getAll_Servlet", "onSuccess: " + result);
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {
                }.getType();
                ArrayList<HashMap<String, Object>> sinlist1 = gson.fromJson(result, type);
                sinlist.clear();
                sinlist.addAll(sinlist1);
                myDate.setSelector(new ColorDrawable(Color.TRANSPARENT));
                myDate.setAdapter(new getDayNumAdapter(getApplicationContext()));
                Log.i("lstDay222", "getView:lstDay txtDay  333555553" + lstDay + "---------" + sinlist1);
                Log.i("lstDay222", "getView:lstDay txtDay  333555550 " + lstDay + "---------" + sinlist);
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
        list.clear();
        dayMaxNum = getCurrentMonthDay();
        for (int i = 0; i < dayMaxNum; i++) {
            list.add(i, i + 1 + "");
        }
    }
   public void getData1(){

   }
    public void initEvent() {//控件事件
        myDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                    long id) {
                //判断是否已经签到 从服务器获取签到信息
                if (day == position + 1)//只能当天签到//主要根据当天天数查询
                {
                    //getData(year+"-"+month+"-"+(position+1),3,"0");
                    //        String url = URL.url + "Sign_tbl_getAll_Servlet";
                    String url = URL.url + "Sign_tbl_getAll_Servlet";
                    RequestParams requestParams = new RequestParams(url);
                    requestParams.addQueryStringParameter("sign_date", year + "-" + month + "-" + (position + 1));
                    requestParams.addQueryStringParameter("user_id", 3 + "");
                    requestParams.addQueryStringParameter("sign_yearmonth", "0");
                    x.http().get(requestParams, new Callback.CacheCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Log.i("Sign_tbl_getAll_Servlet", "onSuccess: " + result);
                            Gson gson = new Gson();
                            Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {
                            }.getType();
                            ArrayList<HashMap<String, Object>> sinlist1 = gson.fromJson(result, type);
                            sinlist.clear();
                            sinlist.addAll(sinlist1);
                            Log.i("lstDay222", "getView:lstDay txtDay  333555553" + lstDay + "---------" + sinlist1);
                            Log.i("lstDay222", "getView:lstDay txtDay  333555550 " + lstDay + "---------" + sinlist);
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

                        @Override
                        public boolean onCache(String result) {
                            return false;
                        }
                    });
                    if(sinlist.size() > 0) {//否则签到并插入数据到数据库中
                        Toast.makeText(getApplicationContext(), "已经签过到不能重复签到", Toast.LENGTH_SHORT).show();
                        Log.d("","已签到");
                    }
                    //sinlist.get(position)
                    //day==Integer.parseInt(list.get(position))
                    if (sinlist.get(position)==null) {
                        insertData(year + "-" + month + "-" + (position + 1), ((MyApplication) DaySign.this.getApplication()).getUser().getUser_id(), year + "" + month);//插入数据//月份
                        initData();
                    }
                }
            }
        });
    }

    class getDayNumAdapter extends BaseAdapter {//自定义日历适配器

        Context context;

        public getDayNumAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return list.size();//月天数
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {//自定义日历表适配器
            View v = LinearLayout.inflate(context, R.layout.activity_day_sign_griditem, null);//item
            TextView txtWeek = (TextView) v.findViewById(R.id.txtWeekDateMB);//签到的天数
            TextView txtDay = (TextView) v.findViewById(R.id.txtDayDateMB);//每周天数分布
            switch (position) {
                case 0:
                    txtWeek.setText("一");
                    break;
                case 1:
                    txtWeek.setText("二");
                    break;
                case 2:
                    txtWeek.setText("三");
                    break;
                case 3:
                    txtWeek.setText("四");
                    break;
                case 4:
                    txtWeek.setText("五");
                    break;
                case 5:
                    txtWeek.setText("六");
                    break;
                case 6:
                    txtWeek.setText("七");
                    break;
            }
            if (position < 7) {
                txtWeek.setVisibility(View.VISIBLE);//显示
            }
                lstDay = Integer.parseInt(list.get(position));//当前position为其签到日子
                //标记当前日期
                if (day == lstDay)//如果签到则当前的时间的字体为红色//当前日期未签到
                {
                    txtDay.setText(list.get(position).toString());//当前日期
                    Log.i("lstDay222", "getView:lstDay txtDay  list11111" + lstDay + "---------" + list);
                        txtDay.setTextColor(Color.RED);
                    //txtDay.setBackgroundColor(Color.GRAY) ;//背景灰色
                }else {
                    txtDay.setText(list.get(position).toString());
                    //标记已签到后的背景
                    for (int i = 0; i < sinlist.size(); i++)//获取当月已经签到的日期
                    {
                        String nowdate = sinlist.get(i).get("sign_date").toString();
                        String[] nowdatearr = nowdate.split("-");
                        Log.i("lstDay222", "ge221jjsjjhhbajsjkfnjsjbbfbjbjkabdkbbcbkbabhbhkbhbkahjbdbkhbhhckhahkhhkhakskhh  ajkjjkjajj1tView:lstDay txtDay  3335555570" + lstDay + "---------" + nowdatearr);
                        if (lstDay == Integer.parseInt(nowdatearr[2])) {
                            txtDay.setBackgroundColor(Color.GRAY);//已经签到的日子背景为蓝色
                            txtDay.setTextColor(Color.YELLOW);
                            ++days;
                        }
                        sign.setText("已经签到" + days + "天");
                    }
                }
            return v;
        }

    }

    //签到时间，用户id，月份，当前时间
    public void insertData(String sign_date, int user_id, String sign_yearmonth) {//插入数据到数据库
        String url2 = URL.url + "Sign_tbl_insert_Servlet";
        long currentTime = System.currentTimeMillis();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(currentTime);
        String sign_nowdate = formatter.format(date);
        RequestParams requestParams2 = new RequestParams(url2);
        requestParams2.addQueryStringParameter("sign_date", sign_date);
        requestParams2.addQueryStringParameter("user_id", user_id + "");
        requestParams2.addQueryStringParameter("sign_yearmonth", sign_yearmonth);
        requestParams2.addQueryStringParameter("sign_nowdate", sign_nowdate);
        Log.i("Sign_tbl_getAll_Servlet", "onSuccess: System.currentTimeMillis()" + System.currentTimeMillis());
        x.http().get(requestParams2, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("Sign_tbl_insert_Servlet", "onSuccess: " + result);
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

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    //签到时间，用户id，月份
 //   public void getData(final String sign_date, int user_id, String sign_yearmonth) {//查询数据库方法
//        String url = URL.url + "Sign_tbl_getAll_Servlet";
//        RequestParams requestParams = new RequestParams(url);
//        requestParams.addQueryStringParameter("sign_date", sign_date);
//        requestParams.addQueryStringParameter("user_id", user_id + "");
//        requestParams.addQueryStringParameter("sign_yearmonth", sign_yearmonth);
//        x.http().get(requestParams, new Callback.CacheCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                Log.i("Sign_tbl_getAll_Servlet", "onSuccess: " + result);
//                Gson gson = new Gson();
//                Type type = new TypeToken<ArrayList<HashMap<String, Object>>>() {
//                }.getType();
//                ArrayList<HashMap<String, Object>> sinlist1 = gson.fromJson(result, type);
//                sinlist.clear();
//                sinlist.addAll(sinlist1);
//                    getData1();
//                Log.i("lstDay222", "getView:lstDay txtDay  333555553" + lstDay + "---------" + sinlist1);
//                Log.i("lstDay222", "getView:lstDay txtDay  333555550 " + lstDay + "---------" + sinlist);
//            }
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//
//            @Override
//            public boolean onCache(String result) {
//                return false;
//            }
//        });
//    }

    public int getCurrentMonthDay() {//获取月天数
        Calendar a = Calendar.getInstance();
        a.set(Calendar.DATE, 1);
        a.roll(Calendar.DATE, -1);
        int maxDate = a.get(Calendar.DATE);
        return maxDate;
    }
}
