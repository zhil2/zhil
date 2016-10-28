package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class RegistredOverActivity extends AppCompatActivity {//新增

    @InjectView(R.id.et_confirm)
    EditText pwd;
    @InjectView(R.id.et_confirm2)
    EditText pwd2;
    @InjectView(R.id.cb_yes)
    CheckBox cbYes;
    @InjectView(R.id.b_sunmit)
    Button bSunmit;
    int yes1 = 1;
    String telnumber;
    @InjectView(R.id.et_email)
    EditText email;
    @InjectView(R.id.b_returnsunmit)
    Button bReturnsunmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred_over);
        ButterKnife.inject(this);
        Intent intent = getIntent();
        telnumber = intent.getStringExtra("telnumber");
    }

    @OnClick({R.id.cb_yes, R.id.b_sunmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cb_yes:
                yes1++;
                break;
            case R.id.b_sunmit:
                String pwds = pwd.getText().toString();//首次密码
                String pwds2 = pwd2.getText().toString();//确认密码
                String emails = email.getText().toString();//邮箱
                if (pwds.equals("")) {
                    Toast.makeText(RegistredOverActivity.this, "您有重要信息未填写，请确认输入", Toast.LENGTH_SHORT).show();
                } else if (!pwds.equals(pwds2)) {
                    Toast.makeText(RegistredOverActivity.this, "您输入的两次密码并不相同，请重新输入", Toast.LENGTH_SHORT).show();
                    pwd.setText("");
                    pwd2.setText("");
                } else if (yes1 % 2 == 1) {
                    Toast.makeText(RegistredOverActivity.this, "您还未同意用户协议，请确认同意条款", Toast.LENGTH_SHORT).show();
                } else {

                    RequestParams params = new RequestParams(NetUtil.url+"RegistredServlet");
                    params.addBodyParameter("numbers", telnumber);//post方法的传值
                    //params.addBodyParameter("conf", conf);
                    params.addBodyParameter("pwds", pwds);
                    params.addBodyParameter("emails", emails);

                    Toast.makeText(RegistredOverActivity.this, "正在注册中，请稍等。。。", Toast.LENGTH_SHORT).show();
                    x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                        @Override
                        public void onSuccess(String result) {
                            Log.e("TAG", "u3  get yes" + result);
                            if (result.equals("注册成功，跳转至登录页面")) {
                                Toast.makeText(RegistredOverActivity.this, result, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegistredOverActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(RegistredOverActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            Log.e("TAG", "u3  get no");
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
        }
    }

    @OnClick(R.id.b_returnsunmit)
    public void onClick() {
        Intent intent=new Intent(RegistredOverActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
        Log.i("11", "onClick: "+111);
        Log.i("11", "onClick: "+111);
    }
}
