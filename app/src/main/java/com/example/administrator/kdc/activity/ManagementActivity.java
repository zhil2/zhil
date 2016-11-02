package com.example.administrator.kdc.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.example.administrator.kdc.vo.Venuestime_tbl;
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

public class ManagementActivity extends AppCompatActivity {

    String url2;
    ImageLoader myImageLoader;

    @InjectView(R.id.b_pisition)
    Button bPisition;
    @InjectView(R.id.textView13)
    TextView textView13;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.relativeLayout5)
    RelativeLayout relativeLayout5;
    @InjectView(R.id.textView11)
    TextView textView11;
    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @InjectView(R.id.textView12)
    TextView textView12;
    @InjectView(R.id.et_man)
    EditText etMan;
    @InjectView(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    @InjectView(R.id.textView14)
    TextView textView14;
//    @InjectView(R.id.et_type)
//    EditText etType;
    @InjectView(R.id.relativeLayout4)
    RelativeLayout relativeLayout4;
    @InjectView(R.id.textView10)
    TextView textView10;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.textView16)
    TextView textView16;
    @InjectView(R.id.et_monery)
    EditText etMonery;
    @InjectView(R.id.textView15)
    TextView textView15;
    @InjectView(R.id.relativeLayout6)
    RelativeLayout relativeLayout6;
    @InjectView(R.id.textView17)
    TextView textView17;
    @InjectView(R.id.et_number)
    EditText etNumber;
    @InjectView(R.id.relativeLayout7)
    RelativeLayout relativeLayout7;
    @InjectView(R.id.textView18)
    TextView textView18;
    @InjectView(R.id.et_live)
    EditText etLive;
    @InjectView(R.id.relativeLayout8)
    RelativeLayout relativeLayout8;
    @InjectView(R.id.go)
    Button go;

    int y, m, d, h, m2, flag = 0;
    String m4, h2, m24, h22;

    String hhh,fff;
    int user_id;
    Venues_tbl venues_tbl;
    Venuestime_tbl venuestime_tbl;
    @InjectView(R.id.editText)
    EditText editText;
    @InjectView(R.id.textView25)
    TextView textView25;
    @InjectView(R.id.textView26)
    TextView textView26;
    @InjectView(R.id.et_totalprice)
    Spinner etTotalprice;

    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    String muster_type;

    //时间选择器的控件

    private TextView tv_center;
    private WheelMain wheelMainDate;
    private String beginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        user_id = ((MyApplication) getApplication()).getUser().getUser_id();

        venues_tbl = (Venues_tbl) intent.getParcelableExtra("venues_tbl");
        venuestime_tbl = (Venuestime_tbl) intent.getParcelableExtra("venuestime_tbl");

        tv_center = (TextView) findViewById(R.id.tv_center);


        if(!venuestime_tbl.getVenuestime_kg().equals("全天开放")) {
            String[]  strs=venuestime_tbl.getVenuestime_kg().split(":");
            String[]  strs2=venuestime_tbl.getVenuestime_bg().split(":");
            h22 = strs[0];
            m24 = strs[1];
            h2 = strs2[0];
            m4 = strs2[1];
        }else{
            h22="12";
            m24="00";
            h2="12";
            m4="00";
        }

        bPisition.setText(venuestime_tbl.getVenuestime_kg() + "");
        button4.setText(venuestime_tbl.getVenuestime_bg() + "");

        data_list = new ArrayList<String>();

        String b = "";
        switch (venues_tbl.getVenues_type()) {
            case 1:
                data_list.add("支持预约的付费场地");
                data_list.add("不支持预约的付费场地");
                data_list.add("免费的野场地");
                etMonery.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price() + "");
                break;
            case 2:
                data_list.add("不支持预约的付费场地");
                data_list.add("支持预约的付费场地");
                data_list.add("免费的野场地");
                etMonery.setText("0");
                break;
            case 3:
                data_list.add("免费的野场地");
                data_list.add("不支持预约的付费场地");
                data_list.add("支持预约的付费场地");
                etMonery.setText("0");
                break;
        }

        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etTotalprice.setAdapter(arr_adapter);


        url2 = venues_tbl.getVenuesshow_tbl().getVenuesshow_portrait();
        myImageLoader = new ImageLoader(ManagementActivity.this);
        myImageLoader.showImageByUrl(url2, imageView7);

        etName.setText(venues_tbl.getVenues_name());
        etMan.setText(venues_tbl.getVenues_ceiling() + "");
        editText.setText(venues_tbl.getVenues_current() + "");


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

        etNumber.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_number() + "");

        etLive.setText("篮球");

    }

    @OnClick({R.id.b_pisition, R.id.button4, R.id.imageView7, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_pisition:

                hhh= (String) bPisition.getText();
                fff= (String) button4.getText();

           //     Log.d("qwrfad","hhh   "+hhh+"   fff"+fff);
                if(!bPisition.getText().equals("全天开放")&&!button4.getText().equals("")) {
                    String[]  strs=hhh.split(":");
                    String[]  strs2=fff.split(":");
                    h22 = strs[0];
                    m24 = strs[1];
                    h2 = strs2[0];
                    m4 = strs2[1];
                }else{
                    h22="12";
                    m24="00";
                    h2="12";
                    m4="00";
                }

                showBottoPopupWindow(2016,12,12,Integer.parseInt(h22),Integer.parseInt(m24),bPisition);


                break;
            case R.id.button4:

                hhh= (String) bPisition.getText();
                fff= (String) button4.getText();
                if(!bPisition.getText().equals("全天开放")&&!button4.getText().equals("")) {
                    String[]  strs=hhh.split(":");
                    String[]  strs2=fff.split(":");
                    h22 = strs[0];
                    m24 = strs[1];
                    h2 = strs2[0];
                    m4 = strs2[1];
                }else{
                    h22="12";
                    m24="00";
                    h2="12";
                    m4="00";
                }

                showBottoPopupWindow(2016,12,12,Integer.parseInt(h2),Integer.parseInt(m4),button4);

                break;
            case R.id.imageView7:

                break;
            case R.id.go:
                if(etName.getText().equals("")){
                    Toast.makeText(ManagementActivity.this, "场地名不能为空", Toast.LENGTH_SHORT).show();
                }else
                if(etName.getText().length()>12){
                    Toast.makeText(ManagementActivity.this, "场地名不能超过12个字符", Toast.LENGTH_SHORT).show();
                }
                else
                if(etMan.getText().equals("")){
                    Toast.makeText(ManagementActivity.this, "场地容量不能为空", Toast.LENGTH_SHORT).show();
                }
                else
                if(etNumber.getText().equals("")){
                    Toast.makeText(ManagementActivity.this, "支持运动不能为空", Toast.LENGTH_SHORT).show();
                }
                else
                if(bPisition.getText().equals("全天开放")&&!button4.getText().equals("")){
                    Toast.makeText(ManagementActivity.this, "全天开放状态下，不应该有闭馆时间", Toast.LENGTH_SHORT).show();
                    button4.setText("");
                }else if(bPisition.getText().equals("")){
                Toast.makeText(ManagementActivity.this, "您还未填写开馆时间，将开馆时间与闭馆时间等同时，将转为全天开放模式", Toast.LENGTH_SHORT).show();
                }else if(!bPisition.getText().equals("全天开放")&&button4.getText().equals("")) {
                    Toast.makeText(ManagementActivity.this, "非全天开放模式下，闭馆时间不能为空，将开馆时间与闭馆时间等同时，将转为全天开放模式", Toast.LENGTH_SHORT).show();
                }else {
                    RequestParams params = new RequestParams(NetUtil.url + "VenuesServlet3");
                    params.addBodyParameter("venues_id", venues_tbl.getVenues_id() + "");
                    params.addBodyParameter("venues_name", etName.getText() + "");
                    params.addBodyParameter("venues_ceiling", etMan.getText() + "");
                    params.addBodyParameter("venues_current", editText.getText() + "");
                    params.addBodyParameter("venues_kg", bPisition.getText() + "");
                    params.addBodyParameter("venues_bg", button4.getText() + "");
                    params.addBodyParameter("venues_type", muster_type + "");
                    params.addBodyParameter("venuesshow_price", etMonery.getText() + "");
                    params.addBodyParameter("venuesshow_number", etNumber.getText() + "");
                    x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                        @Override
                        public void onSuccess(String result) {

                            Toast.makeText(ManagementActivity.this, result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("venues_name", etName.getText() + "");
                            intent.putExtra("venues_ceiling", etMan.getText() + "");
                            intent.putExtra("venues_current", editText.getText() + "");
                            intent.putExtra("venues_kg", bPisition.getText() + "");
                            intent.putExtra("venues_bg", button4.getText() + "");
                            intent.putExtra("venues_type", muster_type + "");
                            intent.putExtra("venuesshow_price", etMonery.getText() + "");
                            intent.putExtra("venuesshow_number", etNumber.getText() + "");

                            //   Log.d("dfghdfnxcv", "ma    etName.getText() " + etName.getText());

                            setResult(RESULT_OK, intent);
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
                }

                break;
        }
    }


    private java.text.DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public void showBottoPopupWindow(int y,int m,int d,int s,int f, final Button button) {
        WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_popup_window2,null);
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
        int year = y;//calendar.get(Calendar.YEAR);
        int month =  m;//calendar.get(Calendar.MONTH);
        int day =  d;//calendar.get(Calendar.DAY_OF_MONTH);
        int hours =  s;//calendar.get(Calendar.HOUR_OF_DAY);
        int minute =  f;//calendar.get(Calendar.MINUTE);

        wheelMainDate.initDateTimePicker2( hours,minute);

        final String currentTime = wheelMainDate.getTime2().toString();
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
                beginTime = wheelMainDate.getTime2().toString();
                button.setText(beginTime.toString());

              //  Log.d("q34123asd","222button4.getText()   "+button4.getText()+"      222bPisition.getText()"+bPisition.getText());
                if(button4.getText().equals(bPisition.getText())){
                    bPisition.setText("全天开放");
                    button4.setText("");
                }

                try {
                    Date begin = dateFormat.parse(currentTime);
                    Date end = dateFormat.parse(beginTime);
                    //将选择的时间显示
                    button.setText(DateUtils.formateStringH(beginTime,DateUtils.yyyyMMddHHmm));

                    //tv_house_time.setText(DateUtils.currentTimeDeatil(begin));
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
