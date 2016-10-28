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
import com.example.administrator.kdc.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;


public class BackActivity extends AppCompatActivity {//修改过
    @InjectView(R.id.b_returnsunmit)
    ImageView bReturnsunmit;
    private EditText newpwd, newpwd2, number, conf;
    private Button sunmit, confirm;
    String APPID = "b196bd5a61fd7201f2bd4684f880c15f";
    public static String telnumber;//手机号
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        BmobSMS.initialize(this, APPID);//初始化
        ButterKnife.inject(this);
        sunmit = (Button) findViewById(R.id.b_sunmit);//提交
        confirm = (Button) findViewById(R.id.b_confirm);//获取验证码
        newpwd = (EditText) findViewById(R.id.et_newpwd);//旧密码
        newpwd2 = (EditText) findViewById(R.id.et_newpwd2);//新密码
//        number= (EditText) findViewById(R.id.et_number);//电话号码
//        conf= (EditText) findViewById(R.id.et_confirm);//验证码
        confirm.setOnClickListener(new View.OnClickListener() {//发送验证码
            @Override
            public void onClick(View view) {
                telnumber = number.getText().toString();//手机号
                Log.i("bmob", "短信id：" + telnumber);//用于查询本次短信发送详情
                BmobSMS.requestSMSCode(BackActivity.this, telnumber, "模板名称", new RequestSMSCodeListener() {

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
        sunmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verifySmsCode = conf.getText().toString();//验证码
                if (!verifySmsCode.equals("")) {
                    BmobSMS.verifySmsCode(BackActivity.this, telnumber, verifySmsCode, new VerifySMSCodeListener() {//验证验证码
                        @Override
                        public void done(BmobException ex) {
                            // TODO Auto-generated method stub
                            if (ex == null) {//短信验证码已验证成功
                                Log.i("bmob", "验证通过，正在前往注册");
                                String pwds = newpwd.getText().toString();
                                String pwds2 = newpwd2.getText().toString();
                                if (telnumber.equals("") || pwds.equals("")) {
                                    Toast.makeText(BackActivity.this, "您有重要信息未填写，请确认输入", Toast.LENGTH_SHORT).show();
                                } else if (!pwds.equals(pwds2)) {
                                    Toast.makeText(BackActivity.this, "您输入的两次密码并不相同，请重新输入", Toast.LENGTH_SHORT).show();
                                    newpwd.setText("");
                                    newpwd2.setText("");
                                } else {

                                    RequestParams params = new RequestParams(NetUtil.url + "BackServlet");
                                    params.addBodyParameter("numbers", telnumber);//post方法的传值
//                                  params.addBodyParameter("conf", conf);
                                    params.addBodyParameter("pwds", pwds);
                                    Toast.makeText(BackActivity.this, "正在修改中，请稍等。。。", Toast.LENGTH_SHORT).show();
                                    x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                                        @Override
                                        public void onSuccess(String result) {
                                            Log.e("TAG", "u3  get yes" + result);
                                            if (result.equals("修改成功，跳转至登录页面")) {
                                                Toast.makeText(BackActivity.this, result, Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(BackActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                Toast.makeText(BackActivity.this, result, Toast.LENGTH_SHORT).show();
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
                            } else {
                                Log.i("bmob", "验证失败：code =" + ex.getErrorCode() + ",msg = " + ex.getLocalizedMessage());
                                Toast.makeText(BackActivity.this, "验证码无效，请输入正确验证码", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @OnClick(R.id.b_returnsunmit)
    public void onClick() {
        finish();
    }
}
