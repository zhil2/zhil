package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CollectionActivity extends AppCompatActivity {

    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.linearLayout)
    RelativeLayout linearLayout;
    @InjectView(R.id.listView)
    ListView listView;

    List<Venues_tbl> venueslist = new ArrayList<Venues_tbl>();
    CommonAdapter<Venues_tbl> orderAdapter;
    int user_id, flag = 0, ys = 1;
    ImageLoader myImageLoader;
    Venues_tbl item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.inject(this);
        user_id = ((MyApplication) getApplication()).getUser().getUser_id();
        getOrderData();
    }

    public void getOrderData() {//CollectionServlet2
        orderAdapter = null;
        RequestParams params = new RequestParams(NetUtil.url + "CollectionServlet2");
        params.addBodyParameter("user_id", user_id + "");//post方法的传值
        Log.d("gddsfgd","传user_id"+user_id);
        params.addBodyParameter("ys", ys + "");//post方法的传值
        params.addBodyParameter("flag", flag + "");//排序类型

        //获取网络数据，获取到之后，设置数据源
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                List<Venues_tbl> newOrders = gson.fromJson(result, new TypeToken<List<Venues_tbl>>() {
                }.getType());

                venueslist.clear();
                venueslist.addAll(newOrders);//添加新的集合
                //  fragAllordersListview.setEmptyView(fragAllordersRl);//设置没有数据时，显示
                if (orderAdapter == null) {
                    // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
                    orderAdapter = new CommonAdapter<Venues_tbl>(CollectionActivity.this, venueslist, R.layout.list) {
                        @Override
                        public void convert(ViewHolder viewHolder, final Venues_tbl item2, final int position) {

                            TextView venues_name = viewHolder.getViewById(R.id.tv_name);
                            TextView address_id = (TextView) viewHolder.getViewById(R.id.tv_add);
                            TextView venues_type = (TextView) viewHolder.getViewById(R.id.tv_type);
                            TextView venues_ceiling = (TextView) viewHolder.getViewById(R.id.tv_current);
                            TextView venues_yes = (TextView) viewHolder.getViewById(R.id.tv_yes);
                            TextView venues_no = (TextView) viewHolder.getViewById(R.id.tv_no);
                            ImageView venues_image = (ImageView) viewHolder.getViewById(R.id.imageView);

                            ImageView sc = (ImageView) viewHolder.getViewById(R.id.iv_sc);


                            item = venueslist.get(position);

                            sc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                    Log.d("111","我点击了");
                                    sc(user_id,item.getVenues_id(),0,v);

                                }
                            });



                            String b = "";
                            switch (item.getVenues_type()) {
                                case 1:
                                    b = "支持预约的付费场地";
                                    break;
                                case 2:
                                    b = "不支持预约的付费场地";
                                    break;
                                case 3:
                                    b = "免费的野场地";
                                    break;
                            }

                            venues_name.setText(item.getVenues_name());
                            address_id.setText("地点：" + item.getAddress_tbl().getAddress_city() + "" + item.getAddress_tbl().getAddress_county() + "" + item.getAddress_tbl().getAddress_town() + "" + item.getAddress_tbl().getAddress_show());
                            venues_type.setText(b + "");
                            venues_ceiling.setText("     人数：" + item.getVenues_ceiling() + "/" + item.getVenues_current());
                            venues_yes.setText(+ item.getVenues_yes() + "");
                            venues_no.setText( + item.getVenues_no() + "");

                            String url2;
                            url2 = item.getVenuesshow_tbl().getVenuesshow_portrait();

                            sc.setBackgroundResource(R.drawable.sc);

                            //    Log.d("BBBBB","url2"+url2);
                            myImageLoader = new ImageLoader(CollectionActivity.this);
                            myImageLoader.showImageByUrl(url2, venues_image);
                        }
                    };

                    //listview中显示的是所有的数据信息
                    listView.setAdapter(orderAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override//点击此view进行界面的跳转
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Venues_tbl upload = venueslist.get(position);
                            //   Log.d("BBBBB","url2 ven____________________"+upload.getVenuesshow_tbl().getVenuesshow_portrait());
                            Intent intent = new Intent(CollectionActivity.this, VenuesshowActivity.class);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("venues_tbl", upload);//发送数据


                            startActivity(intent);
                        }
                    });


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


    public void sc(int  user_id, int collection_object, int flag, final View v){

        RequestParams params = new RequestParams(NetUtil.url +"CollectionServlet");
        params.addBodyParameter("user_id", user_id+"");//post方法的传值
        params.addBodyParameter("collection_type", 1+"");
        params.addBodyParameter("collection_object",item.getVenues_id()+"");
        params.addBodyParameter("flag",0+"");
        //     Toast.makeText(getActivity(), "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                if(result.equals("收藏成功")) {
                    v.setBackgroundResource(R.drawable.sc);
                }else{
                    v.setBackgroundResource(R.drawable.sc2);
                    getOrderData();
                }
            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("BBBBB", "u3  get no");

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
