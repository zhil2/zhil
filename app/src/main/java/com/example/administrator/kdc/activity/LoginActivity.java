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
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.User_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class LoginActivity extends AppCompatActivity {

    private EditText name, pwd;
    private Button login, login2, back, registred;
    private User_tbl user = null;
    private ImageView b;
    String pwds;
    int names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        x.view().inject(this);
        b = (ImageView) findViewById(R.id.iv_b);
        login2 = (Button) findViewById(R.id.b_login2);
        login = (Button) findViewById(R.id.b_login);
        back = (Button) findViewById(R.id.b_back);
        registred = (Button) findViewById(R.id.b_registred);
        name = (EditText) findViewById(R.id.et_name);
        pwd = (EditText) findViewById(R.id.et_pwd);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//登录
                 names = Integer.parseInt(name.getText().toString());
                 pwds = pwd.getText().toString();

                RequestParams params = new RequestParams(NetUtil.url +"LoginServlet2");
                params.addBodyParameter("userName", names+"");//post方法的传值
                params.addBodyParameter("userPwd", pwds);
                Toast.makeText(LoginActivity.this, "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
                x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
                    @Override
                    public void onSuccess(String result) {
                        Log.e("BBBBB", "u3  get yes+" + result);
                        Gson gson=new Gson();
                        Usershow_tbl usershows=new Usershow_tbl();
                        usershows=gson.fromJson(result,Usershow_tbl.class);
                        User_tbl users=usershows.getUser_tbl();
                        if (!usershows.equals(null)) {
                            Log.d("BBBBB","登录成功");
                            //登录成功后将
                            ((MyApplication) getApplication()).setUsershow(usershows);
                            ((MyApplication) getApplication()).setUser(users);
                            Toast.makeText(LoginActivity.this, "登录成功，欢迎您", Toast.LENGTH_SHORT).show();
                           //将user_id传过去
                            Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                            intent.putExtra("user_id",users.getUser_id());
                            startActivity(intent);
                        } else {
                            Log.d("BBBBB","登录失败"+result);
                        }
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.e("BBBBB", "u3  get no");
                        pwd.setText("");
                        Toast.makeText(LoginActivity.this, "您的账号/密码错误，请区分大小写或者重新输入", Toast.LENGTH_SHORT).show();
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
        });
        login2.setOnClickListener(new View.OnClickListener() {//游客登录
            @Override
            public void onClick(View v) {
                      Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                     startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {//找回密码
            @Override
            public void onClick(View v) {


                   Intent intent=new Intent(LoginActivity.this,BackActivity.class);
                   startActivity(intent);

            }
        });
        registred.setOnClickListener(new View.OnClickListener() {//注册
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegistredActivity.class);
                startActivity(intent);
            }
        });
    }
/*    @Override
    protected void  onActivityResult(int requestCode,int resultCode,Intent data){
switch (requestCode){
    case 1:
        if(resultCode==RESULT_OK){

            user = (User_tbl) data.getSerializableExtra("user");
           // Toast.makeText(LoginActivity.this,"反馈"+user.getUserName(),Toast.LENGTH_SHORT).show();
            // Log.d("LoginActivity", "book title->" + user.getUserName());
            name.setText(user.getUserName());
            pwd.setText(user.getUserPwd());
        }}
*/

}
