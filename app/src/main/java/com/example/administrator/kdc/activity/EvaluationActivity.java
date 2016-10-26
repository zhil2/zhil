package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Reply_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class EvaluationActivity extends AppCompatActivity {

    CommonAdapter<Reply_tbl> orderAdapter;

    List<Reply_tbl> venueslist = new ArrayList<Reply_tbl>();

    int user_id,venues_id;

    String url2;
    ImageLoader myImageLoader;

    @InjectView(R.id.ev_pj)
    EditText evPj;
    @InjectView(R.id.b_pj)
    Button bPj;
    @InjectView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @InjectView(R.id.listView_a)
    ListView listViewA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_a);
        ButterKnife.inject(this);

        user_id=((MyApplication)getApplication()).getUsershow().getUser_tbl().getUser_id();

        Intent intent=getIntent();
        venues_id=intent.getIntExtra("venues_id",0);
        getOrderData();

    }

    @OnClick(R.id.b_pj)
    public void onClick() {
        RequestParams params = new RequestParams(NetUtil.url +"ReplyServlet2");
        params.addBodyParameter("user_id", user_id+"");//post方法的传值
        params.addBodyParameter("venues_id", venues_id+"");
        params.addBodyParameter("reply_text", evPj.getText()+"");

        Log.d("ferhrdgfg","user_id"+venues_id+"     "+venues_id);

        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                getOrderData();
                Toast.makeText(EvaluationActivity.this, result, Toast.LENGTH_SHORT).show();
                evPj.setText("");

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


    public void getOrderData() {
      //  orderAdapter = null;

        RequestParams params = new RequestParams(NetUtil.url +"ReplyServlet");

        params.addBodyParameter("venues_id", venues_id+"");

        Log.d("ferhrdgfg","user_id"+venues_id+"     "+venues_id);

        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
                Log.e("BBBBB", "u3  get yes+" + result);
                Gson gson = new Gson();
                List<Reply_tbl> newOrders = gson.fromJson(result, new TypeToken<List<Reply_tbl>>() {
                }.getType());

                venueslist.clear();

                venueslist.addAll(newOrders);//添加新的集合

                Log.i("elav", "onSuccess: venueslist.size"+venueslist.size());

                if (orderAdapter == null) {
                    // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
                    orderAdapter = new CommonAdapter<Reply_tbl>(EvaluationActivity.this, venueslist, R.layout.list_a) {
                        @Override
                        public void convert(final ViewHolder viewHolder, final Reply_tbl item2, final int position) {

                            TextView venues_name = viewHolder.getViewById(R.id.tv_nr);
                            TextView tv_time = viewHolder.getViewById(R.id.tv_time);
                            TextView tv_nr = viewHolder.getViewById(R.id.tv_nr);
                            ImageView iv_tx=viewHolder.getViewById(R.id.iv_tx);

                            venues_name.setText("评论者："+item2.getUsershow_tbl().getUsershow_name());
                            tv_time.setText("评论时间"+item2.getReply_date());
                            tv_nr.setText(""+item2.getReply_text());

                            url2=item2.getUsershow_tbl().getUsershow_head();
                            myImageLoader = new ImageLoader(EvaluationActivity.this);
                            myImageLoader.showImageByUrl(url2, iv_tx);


//                    sc.setTag(position);//定义唯一位置标识
//
//                    Log.d("ewrwe", "    getTag()   " + getTag() + "     position" + position);
//
//
//                    sc.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(final View v) {
//
//
//                        }
//                    });

                        }

                    };

                    //listview中显示的是所有的数据信息
                    listViewA.setAdapter(orderAdapter);
//            listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override//点击此view进行界面的跳转
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    Reply_tbl upload = venueslist.get(position);
//                    //   Log.d("BBBBB","url2 ven____________________"+upload.getVenuesshow_tbl().getVenuesshow_portrait());
//                    Intent intent = new Intent(EvaluationActivity.this, VenuesshowActivity.class);
//
//                    startActivity(intent);
//                }
//            });
                } else {
                    //  Log.i("OrderAllFragment", "onSuccess: notifyDataSetChanged:+"+orders+":"+fragAllordersListview);
                    orderAdapter.notifyDataSetChanged();
                }



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
