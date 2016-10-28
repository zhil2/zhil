package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.kdc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;


public class RegistredActivity extends AppCompatActivity {//注册界面//界面、代码修改过
    @InjectView(R.id.b_returnsunmit)
    ImageView bReturnsunmit;
    private EditText number, conf; //email,pwd, pwd2,;
    private Button sunmit, confirm;
    String APPID = "b196bd5a61fd7201f2bd4684f880c15f";
    public static String telnumber;//手机号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred);
        BmobSMS.initialize(this, APPID);//初始化
        ButterKnife.inject(this);
        sunmit = (Button) findViewById(R.id.b_sunmit);//注册按钮
        confirm = (Button) findViewById(R.id.b_confirm);//获取验证码
        number = (EditText) findViewById(R.id.et_number);//手机号输入框
        conf = (EditText) findViewById(R.id.et_confirm);//短信验证
        confirm.setOnClickListener(new View.OnClickListener() {//发送验证码
            @Override
            public void onClick(View view) {
                telnumber = number.getText().toString();//手机号
                Log.i("bmob", "短信id：" + telnumber);//用于查询本次短信发送详情
                BmobSMS.requestSMSCode(RegistredActivity.this, telnumber, "模板名称", new RequestSMSCodeListener() {

                    @Override
                    public void done(Integer smsId, BmobException ex) {
                        // TODO Auto-generated method stub
                        if (ex == null) {//验证码发送成功
                            Log.i("bmob", "短信id：" + smsId);//用于查询本次短信发送详情
                        } else {
                            Log.i("bmob", "errorCode = " + ex.getErrorCode() + ",errorMsg = " + ex.getLocalizedMessage());
                        }
                    }
                });


            }
        });
        sunmit.setOnClickListener(new View.OnClickListener() {//前往注册
            @Override
            public void onClick(View v) {
               String verifySmsCode = conf.getText().toString();//验证码
                if (!verifySmsCode.equals("")) {
                    BmobSMS.verifySmsCode(RegistredActivity.this, telnumber, verifySmsCode, new VerifySMSCodeListener() {//验证验证码
                        @Override
                        public void done(BmobException ex) {
                            // TODO Auto-generated method stub
                            if (ex == null) {//短信验证码已验证成功
                                Log.i("bmob", "验证通过，正在前往注册");
                                Intent intent=new Intent(RegistredActivity.this,RegistredOverActivity.class);//跳转到最后注册界面
                                intent.putExtra("telnumber",telnumber);
                                Toast.makeText(RegistredActivity.this, "验证通过", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            } else {
                                Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                Toast.makeText(RegistredActivity.this, "验证码无效，请输入正确验证码", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @OnClick({R.id.b_returnsunmit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_returnsunmit://返回登陆界面
                finish();
                break;
            //
        }
    }
}
