package com.example.administrator.kdc.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.example.administrator.kdc.wheelview.DateUtils;
import com.example.administrator.kdc.wheelview.JudgeDate;
import com.example.administrator.kdc.wheelview.ScreenInfo;
import com.example.administrator.kdc.wheelview.WheelMain;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MusterActivity extends AppCompatActivity {


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
    TextView etPlace;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.et_price)
    TextView etPrice;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.et_number)
    EditText etNumber;
    @InjectView(R.id.tv_start)
    TextView tvStart;
    @InjectView(R.id.et_start)
    TextView etStart;
    @InjectView(R.id.tv_end)
    TextView tvEnd;
    @InjectView(R.id.et_end)
    EditText etEnd;
    @InjectView(R.id.tv_totalprice)
    TextView tvTotalprice;
    @InjectView(R.id.et_totalprice)
    Spinner etTotalprice;
    @InjectView(R.id.tv_note)
    TextView tvNote;
    @InjectView(R.id.et_note)
    EditText etNote;
    @InjectView(R.id.btn_send)
    Button btnSend;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;

    Venues_tbl venues_tbl;
    int user_id;
    private static final int DATE_ID = 1;
    private TimePicker timePick1;

    int y, m, d, h, m2,flag=0;
    String m4,h2,d2,m3;
    String muster_type;

    //时间选择器的控件

    private TextView tv_center;
    private WheelMain wheelMainDate;
    private String beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muster);
        Intent intent = getIntent();
        user_id =  ((MyApplication) getApplication()).getUser().getUser_id();
        venues_tbl = (Venues_tbl) getIntent().getParcelableExtra("venues_tbl");
        ButterKnife.inject(this);

        tv_center = (TextView) findViewById(R.id.tv_center);

        data_list = new ArrayList<String>();
        data_list.add("AA制");
        data_list.add("我请客");
        data_list.add("求土豪");

        etPlace.setText(venues_tbl.getVenues_name());

        if (venues_tbl.getVenues_type() == 3) {
            etPrice.setText("免费");
        } else {
            if(venues_tbl.getVenues_id()!=0){
                etPrice.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price() +"");
            }else{
                etPrice.setText("");
            }


        }

        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etTotalprice.setAdapter(arr_adapter);


        etTotalprice.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                muster_type = data_list.get(arg2);
                //设置显示当前选择的项
                arg0.setVisibility(View.VISIBLE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

    }


    @OnClick({R.id.et_start, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_send:
                RequestParams params = new RequestParams(NetUtil.url + "MusterServlet");
                params.addBodyParameter("user_id", user_id + "");
                params.addBodyParameter("venues_id",""+venues_tbl.getVenues_id());
                params.addBodyParameter("muster_number", etNumber.getText()+ "");
                params.addBodyParameter("muster_time", etStart.getText()+"");
                params.addBodyParameter("muster_length", etEnd.getText()+ "");
                params.addBodyParameter("muster_note", etNote.getText()+ "");
                params.addBodyParameter("muster_type", muster_type+"");
                params.addBodyParameter("muster_state", "正在召集中");

                x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                    @Override
                    public void onSuccess(String result) {
                        Toast.makeText(MusterActivity.this, result, Toast.LENGTH_SHORT).show();

                        Intent intent3 = new Intent(MusterActivity.this, MusterlistActivity.class);
                        intent3.putExtra("venues_id", venues_tbl.getVenues_id() + "");
                        Log.d("agfhjyir", "show  venues_id" + venues_tbl.getVenues_id());
                        startActivity(intent3);
                        finish();

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

            case R.id.et_start:

                showBottoPopupWindow();
                break;
        }
    }


    private java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void showBottoPopupWindow() {
        WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_popup_window,null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, (int)(width*0.8),
                ActionBar.LayoutParams.WRAP_CONTENT);
        ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelMainDate = new WheelMain(menuView, true);
        wheelMainDate.screenheight = screenInfoDate.getHeight();
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

        wheelMainDate.setSTART_YEAR(year);

        wheelMainDate.initDateTimePicker(year,month,day, hours,minute);

        final String currentTime = wheelMainDate.getTime().toString();
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
        tv_pop_title.setText("请选择时间");
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
                beginTime = wheelMainDate.getTime().toString();

                etStart.setText(beginTime.toString());

                try {
                    Date begin = dateFormat.parse(currentTime);
                    Date end = dateFormat.parse(beginTime);
                    //将选择的时间显示
                  //  etStart.setText(DateUtils.currentTimeDeatil(begin));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
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
