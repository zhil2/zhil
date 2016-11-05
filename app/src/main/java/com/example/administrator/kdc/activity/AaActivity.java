package com.example.administrator.kdc.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Allen on 2016/9/29.
 */
public class AaActivity extends AppCompatActivity {
    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.btn_update)
    Button btnUpdate;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.view1)
    View view1;
    @InjectView(R.id.tv_head)
    TextView tvHead;
    @InjectView(R.id.iv_headimage)
    ImageView ivHeadimage;
    @InjectView(R.id.imageView20)
    ImageView imageView20;
    @InjectView(R.id.rl_head)
    RelativeLayout rlHead;
    @InjectView(R.id.view2)
    View view2;
    @InjectView(R.id.tv_name)
    TextView tvName;
    @InjectView(R.id.tv_real_name)
    TextView tvRealName;
    @InjectView(R.id.rl_edit_name)
    RelativeLayout rlEditName;
    @InjectView(R.id.view3)
    View view3;
    @InjectView(R.id.tv_sex)
    TextView tvSex;
    @InjectView(R.id.tv_real_sex)
    TextView tvRealSex;
    @InjectView(R.id.imageView11)
    ImageView imageView11;
    @InjectView(R.id.rl_edit_sex)
    RelativeLayout rlEditSex;
    @InjectView(R.id.view4)
    View view4;
    @InjectView(R.id.tv_age)
    TextView tvAge;
    @InjectView(R.id.tv_real_age)
    TextView tvRealAge;
    @InjectView(R.id.imageView14)
    ImageView imageView14;
    @InjectView(R.id.rl_edit_age)
    RelativeLayout rlEditAge;
    @InjectView(R.id.view5)
    View view5;
    @InjectView(R.id.tv_date)
    TextView tvDate;
    @InjectView(R.id.tv_real_date)
    TextView tvRealDate;
    @InjectView(R.id.imageView15)
    ImageView imageView15;
    @InjectView(R.id.rl_edit_date)
    RelativeLayout rlEditDate;
    @InjectView(R.id.view6)
    View view6;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_real_address)
    TextView tvRealAddress;
    @InjectView(R.id.imageView16)
    ImageView imageView16;
    @InjectView(R.id.rl_edit_address)
    RelativeLayout rlEditAddress;
    @InjectView(R.id.view7)
    View view7;
    @InjectView(R.id.tv_call)
    TextView tvCall;
    @InjectView(R.id.tv_real_call)
    TextView tvRealCall;
    @InjectView(R.id.rl_edit_call)
    RelativeLayout rlEditCall;
    @InjectView(R.id.view8)
    View view8;
    @InjectView(R.id.tv_youxiang)
    TextView tvYouxiang;
    @InjectView(R.id.tv_real_youxiang)
    TextView tvRealYouxiang;
    @InjectView(R.id.imageView18)
    ImageView imageView18;
    @InjectView(R.id.rl_edit_youxiang)
    RelativeLayout rlEditYouxiang;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update);
        ButterKnife.inject(this);
        getData();
    }
public void getData(){
    tvRealName.setText("昵称");
}
    @OnClick({R.id.btn_back, R.id.btn_update, R.id.rl_head, R.id.rl_edit_name, R.id.rl_edit_sex, R.id.rl_edit_age, R.id.rl_edit_date, R.id.rl_edit_address, R.id.rl_edit_youxiang})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                break;
            case R.id.btn_update:
                if (tvRealName.getText()==null){
                    Toast.makeText(AaActivity.this, "昵称不能为空", Toast.LENGTH_SHORT).show();}
                break;
            case R.id.rl_head:
                //头像控件名：ivHeadimage

                break;
            case R.id.rl_edit_name:

                tvRealName.getText();
                break;
            case R.id.rl_edit_sex:
                final String[] sexArry = new String[] { "男", "女" };//性别选择

                AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
                builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                        //showToast(which+"");
                        tvRealSex.setText(sexArry[which]);
                        dialog.dismiss();//随便点击一个item消失对话框，不用点击确认取消
                    }
                });
                builder.show();// 让弹出框显示
                break;
            case R.id.rl_edit_age:
                break;
            case R.id.rl_edit_date:
                break;
            case R.id.rl_edit_address:
                break;
            case R.id.rl_edit_youxiang:
                break;
        }
    }


}
