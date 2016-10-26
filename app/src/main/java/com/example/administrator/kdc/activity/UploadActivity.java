package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.location.Location;
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
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class UploadActivity extends AppCompatActivity {

    @InjectView(R.id.et_upload)
    EditText etUpload;
    @InjectView(R.id.b_pisition)
    Button bPisition;
    @InjectView(R.id.et_name)
    EditText etName;
    @InjectView(R.id.et_man)
    EditText etMan;
//    @InjectView(R.id.et_type)
//    EditText etType;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.et_monery)
    EditText etMonery;
    @InjectView(R.id.et_number)
    EditText etNumber;
    @InjectView(R.id.et_live)
    EditText etLive;
    @InjectView(R.id.go)
    Button go;
    int user_id = 2;
    String a1, a2, a3, a4, a5, a6;
    Double w = 0.0, j = 0.0;
    private ArrayAdapter<String> arr_adapter;
    String muster_type;

    @InjectView(R.id.textView13)
    TextView textView13;
    @InjectView(R.id.relativeLayout5)
    RelativeLayout relativeLayout5;
    @InjectView(R.id.textView11)
    TextView textView11;
    @InjectView(R.id.relativeLayout2)
    RelativeLayout relativeLayout2;
    @InjectView(R.id.textView12)
    TextView textView12;
    @InjectView(R.id.relativeLayout3)
    RelativeLayout relativeLayout3;
    @InjectView(R.id.textView14)
    TextView textView14;
    @InjectView(R.id.et_totalprice)
    Spinner etTotalprice;
    @InjectView(R.id.relativeLayout4)
    RelativeLayout relativeLayout4;
    @InjectView(R.id.textView10)
    TextView textView10;
    @InjectView(R.id.textView16)
    TextView textView16;
    @InjectView(R.id.textView15)
    TextView textView15;
    @InjectView(R.id.relativeLayout6)
    RelativeLayout relativeLayout6;
    @InjectView(R.id.textView17)
    TextView textView17;
    @InjectView(R.id.relativeLayout7)
    RelativeLayout relativeLayout7;
    @InjectView(R.id.textView18)
    TextView textView18;
    @InjectView(R.id.relativeLayout8)
    RelativeLayout relativeLayout8;
    private List<String> data_list;
//    private LocationManager locationManager;
//    private String provider;
//    public static final int SHOW_LOCATTON = 0;
//    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id", 0);
        w = intent.getDoubleExtra("w", 0.0);
        j = intent.getDoubleExtra("j", 0.0);
        Log.d("ppp", w + "  " + j);
        showLocation2(j, w);

        data_list = new ArrayList<String>();
        data_list.add("支持预约的付费场地");
        data_list.add("不支持预约的付费场地");
        data_list.add("免费的野场地");

      //  etTotalprice

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

    //回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("qq", "????");
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    w = Double.valueOf(data.getStringExtra("w"));
                    j = Double.valueOf(data.getStringExtra("j"));
                    Log.d("qq", "1经度:" + j + "纬度:" + w);
                    //  etUpload.setText("经度:"+j+" 纬度:"+w);  a6
                    showLocation2(j, w);
                    //    etUpload.setText(a6);

                }
        }
    }

//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (locationManager != null) {
//            //退出时关闭监听器
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                return;
//            }
//            locationManager.removeUpdates(locationListener);
//        }
//    }
//
//    LocationListener locationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            //更新位置
//            if (location != null) {
//                showLocation(location);
//            }
//        }
//
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//
//        @Override
//        public void onProviderEnabled(String provider) {
//        }
//
//        @Override
//        public void onProviderDisabled(String provider) {
//        }
//    };

    private void showLocation(Location location) {

        String currentPosition = "latitude is" + location.getLatitude() + "\n" + "longitude is" + location.getLongitude();
        //  etUpload.setText(currentPosition);

    }

    private void showLocation2(Double j, Double w) {
        //+location.getLatitude()+",%20"+location.getLongitude()  注：经度需要前面加20！！！！！！！！！
        String url = "http://api.map.baidu.com/geocoder?output=json&location=" + w + ",%20" + j + "&key=hN4VoCixFHSih157j8PaX8mpDSkRvolb";
        RequestParams params = new RequestParams(url);
        // RequestParams params = new RequestParams("http://api.map.baidu.com/geocoder?output=json&location=32.983424"+",%20116.322987"+"&key=hN4VoCixFHSih157j8PaX8mpDSkRvolb");
        x.http().get(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
                String a = result;
                Pattern p = Pattern.compile("\t|\r|\n");
                Matcher m = p.matcher(a);
                a = m.replaceAll("");
                a = a.replaceAll(" +", "");
                //省
                a1 = a.substring(a.indexOf("province") + 11, a.indexOf("street") - 3);
                //市
                a2 = a.substring(a.indexOf("city") + 7, a.indexOf("direct") - 3);
                //县
                a3 = a.substring(a.indexOf("district") + 11, a.indexOf("province") - 3);
                //街道
                a4 = a.substring(a.indexOf("street") + 9, a.indexOf("street_number") - 3);
                //门牌
                a5 = a.substring(a.indexOf("street_number") + 16, a.indexOf("cityCode") - 4);
                //全部地址信息
                String a6 = a.substring(a.indexOf("formatted_address") + 20, a.indexOf("business") - 3);

                etUpload.setText(a6);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("BBBBB", "u3  get no");
                Toast.makeText(UploadActivity.this, "链接失败", Toast.LENGTH_SHORT).show();
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

    //    @OnClick(R.id.b_pisition)
//    public void onClick() {
//        showLocation2(location);
//    }
//按钮点击事件
    @OnClick({R.id.b_pisition, R.id.imageView7, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            //上传场地头像
            case R.id.imageView7:


                break;
            case R.id.go:

                if (etName.getText().toString().equals("")) {
                    Toast.makeText(
                            UploadActivity.this, "您还未填写场馆名，请填写完整", Toast.LENGTH_LONG).show();
                } else if (etMan.getText().toString().equals("")) {
                    Toast.makeText(
                            UploadActivity.this, "您还未填写场馆容量，请填写完整", Toast.LENGTH_LONG).show();
                } else if (w == 0.0) {
                    Toast.makeText(
                            UploadActivity.this, "您还未对场地进行定位，先进行定位", Toast.LENGTH_LONG).show();
                }  else if (!muster_type.equals("免费的野场地") && etMonery.getText().toString().equals("")) {
                    Toast.makeText(
                            UploadActivity.this, "您上传的场地类型为付费场地，需要填写价格信息，请填写完整", Toast.LENGTH_LONG).show();
                } else if (!muster_type.equals("免费的野场地") && etNumber.getText().toString().equals("")) {
                    Toast.makeText(
                            UploadActivity.this, "您上传的场地类型为付费场地，需要填写联系电话，请填写完整", Toast.LENGTH_LONG).show();
                } else if (etLive.getText().toString().equals("")) {
                    Toast.makeText(
                            UploadActivity.this, "您还未填写场馆支持的运动，请填写完整", Toast.LENGTH_LONG).show();
                } else {
                    RequestParams params = new RequestParams(NetUtil.url +"UploadServlet");
                    params.addBodyParameter("name", etName.getText().toString());//post方法的传值
                    params.addBodyParameter("man", etMan.getText().toString());
                    params.addBodyParameter("monery", etMonery.getText().toString());
                    params.addBodyParameter("w", w + "");
                    params.addBodyParameter("j", j + "");
                    params.addBodyParameter("user_id", user_id + "");
            //        Log.d("xxx", "etName.getText().toString()" + etName.getText().toString());
                    int xx=1;
                    if(muster_type.equals("免费的野场地")){
                        xx=3;
                    }else if(muster_type.equals("不支持预约的付费场地")){
                        xx=2;
                    }


                    params.addBodyParameter("type", xx+"");
                    params.addBodyParameter("live", etLive.getText().toString());
                    params.addBodyParameter("number", etNumber.getText().toString());
                    params.addBodyParameter("a1", a1);
                    params.addBodyParameter("a2", a2);
                    params.addBodyParameter("a3", a3);
                    params.addBodyParameter("a4", a4);
                    params.addBodyParameter("a5", a5);
                    //  Toast.makeText(LoginActivity.this, "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
                    x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                        @Override
                        public void onSuccess(String result) {
                            Log.e("BBBBB", "u3  get yes+" + result);
                            Toast.makeText(
                                    UploadActivity.this, "场地上传成功，谢谢您的分享", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.e("BBBBB", "u3  get no");
                            Toast.makeText(
                                    UploadActivity.this, "场地上传失败，请重新尝试", Toast.LENGTH_LONG).show();
                            //  Toast.makeText(LoginActivity.this, "您的账号/密码错误，请区分大小写或者重新输入", Toast.LENGTH_SHORT).show();
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

                break;
            case R.id.b_pisition:
                Intent intent = new Intent(UploadActivity.this, MainActivity.class);
                intent.putExtra("w", w);
                intent.putExtra("j", j);
                startActivityForResult(intent, 1);
                break;
        }
    }


}
