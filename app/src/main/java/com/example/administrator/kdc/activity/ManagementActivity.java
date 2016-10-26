package com.example.administrator.kdc.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.example.administrator.kdc.vo.Venuestime_tbl;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        user_id = ((MyApplication) getApplication()).getUser().getUser_id();

        venues_tbl = (Venues_tbl) intent.getParcelableExtra("venues_tbl");
        venuestime_tbl = (Venuestime_tbl) intent.getParcelableExtra("venuestime_tbl");

        Log.d("dfghdfnxcv", "Ma     venuestime_tbl.getVenuestime_bg()" + venuestime_tbl.getVenuestime_bg());

        bPisition.setText(venuestime_tbl.getVenuestime_kg() + "");
        button4.setText(venuestime_tbl.getVenuestime_bg() + "");


//        data_list.clear();
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





    //    etType.setText("" + b);

        etNumber.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_number() + "");

        etLive.setText("篮球");


    }

    @OnClick({R.id.b_pisition, R.id.button4, R.id.imageView7, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_pisition:

                TimePickerDialog time = new TimePickerDialog(ManagementActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // TODO Auto-generated method stub
                        h = hourOfDay;
                        h22 = h + "";
                        if (h <= 9) {
                            h22 = "0" + h;
                        }
                        m2 = minute;
                        m24 = m2 + "";
                        if (m2 <= 9) {
                            m24 = "0" + m2;
                        }

                        bPisition.setText(h22 + ":" + m24);
                    }

                }, 12, 30, true);
                time.show();


                break;
            case R.id.button4:

                TimePickerDialog time2 = new TimePickerDialog(ManagementActivity.this, new TimePickerDialog.OnTimeSetListener() {
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

                        int ih2 = Integer.parseInt(h2), ih22 = Integer.parseInt(h22);
                        int im4 = Integer.parseInt(m4), im24 = Integer.parseInt(m24);

                        if (ih2 == ih22 && im4 == im24) {

                            //        Toast.makeText(ManagementActivity.this, "您的闭馆时间", Toast.LENGTH_SHORT).show();
                            bPisition.setText("全天开放");
                            button4.setText("");

                        } else {

                            button4.setText(h2 + ":" + m4);
                        }
                    }
                }, 12, 30, true);
                time2.show();

                break;
            case R.id.imageView7:

                break;
            case R.id.go:

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

                        Log.d("dfghdfnxcv", "ma    etName.getText() " + etName.getText());

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

                break;
        }
    }
}
