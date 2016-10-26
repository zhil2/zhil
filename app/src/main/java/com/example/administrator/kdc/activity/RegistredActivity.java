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

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class RegistredActivity extends AppCompatActivity {

    @InjectView(R.id.cb_yes)
    CheckBox cbYes;
    private EditText pwd, pwd2, number, conf, email;
    private Button sunmit, confirm;
    int yes1=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registred);
        ButterKnife.inject(this);

        sunmit = (Button) findViewById(R.id.b_sunmit);
        confirm = (Button) findViewById(R.id.b_confirm);


        pwd = (EditText) findViewById(R.id.et_pwd);
        pwd2 = (EditText) findViewById(R.id.et_pwd2);
        number = (EditText) findViewById(R.id.et_number);
        conf = (EditText) findViewById(R.id.et_confirm);
        email = (EditText) findViewById(R.id.et_email);


        sunmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numbers = number.getText().toString();
                String conf = number.getText().toString();
                String pwds = pwd.getText().toString();
                String pwds2 = pwd2.getText().toString();

                String emails = email.getText().toString();

                if(numbers.equals("")||pwds.equals("")||conf.equals("")||emails.equals("")){
                    Toast.makeText(RegistredActivity.this, "您有重要信息未填写，请确认输入", Toast.LENGTH_SHORT).show();
                }else
                if (!pwds.equals(pwds2)) {
                    Toast.makeText(RegistredActivity.this, "您输入的两次密码并不相同，请重新输入", Toast.LENGTH_SHORT).show();
                    pwd.setText("");
                    pwd2.setText("");
                }else if(yes1%2==1){
                    Toast.makeText(RegistredActivity.this, "您还未同意用户协议，请确认同意条款", Toast.LENGTH_SHORT).show();
                }else{

                RequestParams params = new RequestParams("http://172.29.48.1:8080/kdc/RegistredServlet");
                params.addBodyParameter("numbers", numbers);//post方法的传值
                params.addBodyParameter("conf", conf);
                params.addBodyParameter("pwds", pwds);
                params.addBodyParameter("emails", emails);

                Toast.makeText(RegistredActivity.this, "正在注册中，请稍等。。。", Toast.LENGTH_SHORT).show();
                x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                    @Override
                    public void onSuccess(String result) {
                        Log.e("TAG", "u3  get yes" + result);
                        if (result.equals("注册成功，跳转至登录页面")) {
                            Toast.makeText(RegistredActivity.this,result, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistredActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegistredActivity.this,result, Toast.LENGTH_SHORT).show();
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
            }
        });
    }

    @OnClick(R.id.cb_yes)
    public void onClick() {
        yes1++;
    }
}
