package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.NetUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class BackActivity extends AppCompatActivity {
    private EditText newpwd,newpwd2,number,conf;
    private Button sunmit,confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back);
        sunmit= (Button) findViewById(R.id.b_sunmit);
        confirm= (Button) findViewById(R.id.b_confirm);

        newpwd= (EditText) findViewById(R.id.et_newpwd);
        newpwd2= (EditText) findViewById(R.id.et_newpwd2);
        number= (EditText) findViewById(R.id.et_number);
        conf= (EditText) findViewById(R.id.et_confirm);

        sunmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String numbers = number.getText().toString();
                String conf = number.getText().toString();
                String pwds = newpwd.getText().toString();
                String pwds2 = newpwd2.getText().toString();

                if(numbers.equals("")||pwds.equals("")||conf.equals("")){
                    Toast.makeText(BackActivity.this, "您有重要信息未填写，请确认输入", Toast.LENGTH_SHORT).show();
                }else
                if (!pwds.equals(pwds2)) {
                    Toast.makeText(BackActivity.this, "您输入的两次密码并不相同，请重新输入", Toast.LENGTH_SHORT).show();
                    newpwd.setText("");
                    newpwd2.setText("");
                }else{

                    RequestParams params = new RequestParams(NetUtil.url +"BackServlet");
                    params.addBodyParameter("numbers", numbers);//post方法的传值
                    params.addBodyParameter("conf", conf);
                    params.addBodyParameter("pwds", pwds);

                    Toast.makeText(BackActivity.this, "正在修改中，请稍等。。。", Toast.LENGTH_SHORT).show();
                    x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                        @Override
                        public void onSuccess(String result) {
                            Log.e("TAG", "u3  get yes" + result);
                            if (result.equals("修改成功，跳转至登录页面")) {
                                Toast.makeText(BackActivity.this,result,Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(BackActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(BackActivity.this,result,Toast.LENGTH_SHORT).show();
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
}
