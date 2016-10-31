package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

/**
 * Created by Allen on 2016/10/19.
 */
public class UserInfoActivity extends AppCompatActivity {
    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.btn_set)
    ImageButton btnSet;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.iv_avatar)
    ImageView ivAvatar;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.ll_name)
    LinearLayout llName;
    @InjectView(R.id.tv_accout)
    TextView tvAccout;
    @InjectView(R.id.tv_credit)
    TextView tvCredit;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.tv_region)
    TextView tvRegion;
    @InjectView(R.id.tv_address1)
    TextView tvAddress;
    @InjectView(R.id.tv_address)
    TextView tvSign;
    @InjectView(R.id.btn_sendmsg)
    Button btnSendmsg;
    String url;
    ImageLoader myimageLoader;
    Usershow_tbl userDetail;
    int useId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        ButterKnife.inject(this);
        getData();
    }

    public void getData(){
        Intent intent=getIntent();
        useId=intent.getIntExtra("use",-1);

        //连网，取数据
        RequestParams requestParams=new RequestParams(NetUtil.url+"QueryFriendInfo");
        requestParams.addQueryStringParameter("userId",useId+"");
        Log.i("UserInfoActivity", "getData: "+useId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                userDetail=gson.fromJson(result,Usershow_tbl.class);
                Log.i("UserInfoActivity", "onSuccess: "+userDetail);
            //赋值
        url=userDetail.getUsershow_head()+"";
        myimageLoader=new ImageLoader(UserInfoActivity.this);
        myimageLoader.showImageByUrl(url,ivAvatar);

        tvName.setText(userDetail.getUsershow_name());
                Log.i("UserInfoActivity", "onSuccess: "+userDetail.getUsershow_name());
        tvAccout.setText("用户ID:"+userDetail.getUser_id()+"");
        tvCredit.setText("信誉度:"+userDetail.getUsershow_credit()+""+"星");
                tvRegion.setText(userDetail.getUsershow_sex());
                tvSign.setText(userDetail.getAddress_id()+"");
                Log.i("UserInfoActivity", "onSuccess: "+result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("UserInfoActivity", "onError: "+"失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.i("UserInfoActivity", "onFinished: ");
            }
        });




    }
    @OnClick({R.id.btn_back, R.id.btn_set, R.id.btn_sendmsg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                break;
            case R.id.btn_set:
                break;
            case R.id.btn_sendmsg:
                /**
                 * 启动单聊
                 * context - 应用上下文。
                 * targetUserId - 要与之聊天的用户 Id。
                 * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
                 */

                if (RongIM.getInstance() != null) {

                    RongIM.getInstance().startPrivateChat(this, String.valueOf(useId),userDetail.getUsershow_name());

                }
                break;
        }
    }
}
