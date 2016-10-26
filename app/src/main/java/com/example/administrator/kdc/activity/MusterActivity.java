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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Venues_tbl;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muster);
        Intent intent = getIntent();
        user_id =  ((MyApplication) getApplication()).getUser().getUser_id();
        venues_tbl = (Venues_tbl) getIntent().getParcelableExtra("venues_tbl");
        ButterKnife.inject(this);

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

                Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料
                t.setToNow(); // 取得系统时间。
                y = t.year;m = t.month;d = t.monthDay;h = t.hour;m2 = t.minute;
                Log.d("time","time3"+y + "-" + m3 + "-" + d2 + " " + h+ ":" + m+":00");

                    TimePickerDialog time = new TimePickerDialog(MusterActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
                            Log.d("time","time2"+y + "-" + m3 + "-" + d2 + " " + h+ ":" + m+":00");
                            etStart.setText(y + "-" + m3 + "-" + d2 + " " + h2+ ":" + m4+":00");
                        }

                    }, h, m2, true);
                    time.show();


                DatePickerDialog datePicker = new DatePickerDialog(MusterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        y = year;
                        m = monthOfYear + 1;
                        d = dayOfMonth;

                        m3=m+"";
                        if(m<=9) {
                            m3 = "0" + m;
                        }
                         d2=d+"";
                        if(d<=9) {
                             d2 = "0" + d;
                        }
                    }
                }, y, m, d);
                datePicker.show();



                break;
        }
    }


}
