package com.example.administrator.kdc.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Order_tbl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import c.b.BP;
import c.b.PListener;

public class GoPayActivity extends AppCompatActivity {

    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.gopay_prodmoney)
    TextView gopayProdmoney;
    @InjectView(R.id.gopay_money)
    TextView gopayMoney;
    @InjectView(R.id.gopay_youhuimoney)
    TextView gopayYouhuimoney;
    @InjectView(R.id.alipay)
    RadioButton alipay;
    @InjectView(R.id.wxpay)
    RadioButton wxpay;
    @InjectView(R.id.type)
    RadioGroup type;
    @InjectView(R.id.go)
    Button go;
    @InjectView(R.id.tv_all)
    TextView tvAll;
    // 此为测试Appid,请将Appid改成你自己的Bmob AppId
    String APPID = "b196bd5a61fd7201f2bd4684f880c15f";
    // 此为支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;
    ProgressDialog dialog;
    boolean flag;
    double TotalPrice;
    Order_tbl orderinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_pay);

        // 必须先初始化
        BP.init(this, APPID);
        ButterKnife.inject(this);
//获得传来的订单信息
        initData();

    }
    private void initData() {

        Intent intent = getIntent();
       String   orderinfo2 = intent.getStringExtra("orderInfo");
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        orderinfo=gson.fromJson(orderinfo2,Order_tbl.class);

        Log.i("GoPayActivity", "onCreate: " + orderinfo2+"33333333333");
        TotalPrice=orderinfo.getTotal_price();
        gopayProdmoney.setText("￥" + TotalPrice + "");
        tvAll.setText("￥" + TotalPrice + "");
    }

    @OnClick({R.id.alipay, R.id.wxpay, R.id.go})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                Intent intent2=new Intent(this,OrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.alipay://支付宝
                flag=true;
                break;
            case R.id.wxpay://微信支付
                flag=false;
                break;
            case R.id.go://去付钱
                pay(flag);

                break;
        }
    }

    public void pay(final boolean flag) {
        showDialog("正在获取订单...");

        BP.pay("商品名", "商品描述",0.02, flag, new PListener() {
            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
                Toast.makeText(GoPayActivity.this,"获取订单成功!请等待跳转到支付页面~" + orderId, Toast.LENGTH_SHORT).show();

            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(GoPayActivity.this, "支付成功!" , Toast.LENGTH_SHORT).show();
                Log.i("GoPayActivity", "succeed: "+"支付成功!");
                changeOrderState(orderinfo.getOrder_id());
            }

            @Override
            public void fail(int i, String s) {
                String str = "错误";
                if (i == 10777) {
                    str = "网络繁忙，请稍后";
                }
                if (i == 6001) {
                    str = "操作中断";
                }
                if (i == -2) {
                    str = "操作中断";
                }
                Log.i("PayActivity", "fail: " + s);
                Toast.makeText(GoPayActivity.this, str, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void unknow() {
                    Log.i("PayActivity", "unknow: 网络繁忙");
                    Toast.makeText(GoPayActivity.this, "网络繁忙", Toast.LENGTH_SHORT).show();
                }
        });
    }


    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    // 默认为0.02
    double getPrice() {
        double price = 0.02;
        try {
            price = 0.02;
        } catch (NumberFormatException e) {
        }
        return price;
    }



    //更改订单id
    private void changeOrderState(int orderId) {
        RequestParams params = new RequestParams(NetUtil.url+ "/OrderUpdateServlet");
        params.addQueryStringParameter("orderId", orderId + "");
        params.addQueryStringParameter("newState","待评价");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Intent intent = new Intent(GoPayActivity.this, MainActivity.class);
                startActivity(intent);
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

    }
}
