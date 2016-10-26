package com.example.administrator.kdc.framet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.MustershowActivity;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Muster_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/9/18.
 */
public class MusterFragement2 extends BaseFragment {


    @InjectView(R.id.frag_muster2)
    ListView fragMuster2;
    List<Muster_tbl> muster=new ArrayList<Muster_tbl>();
    CommonAdapter<Muster_tbl> orderAdapter;
    int user_id,flag=0;
    int venues_id;
    View v;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = LayoutInflater.from(getActivity()).inflate(R.layout.musterfragement2, null);//设置显示的是一个Listview
        //找到listview，设置listview的item显示内容；

        Bundle bundle = getArguments();

        if(bundle!=null){
          String  a= bundle.getString("venues_id");
            venues_id= Integer.parseInt( a );
            Log.d("agfhjyir","frag2  venues_id"+venues_id+"   a"+a);
        }

        ButterKnife.inject(this, v);
        //  Log.d("sdasdga", "onCreateView");
        user_id= ((MyApplication) getActivity().getApplication()).getUser().getUser_id();
        getOrderData();
        return v;

    }
    @Override
    public void initView() {
    }
    @Override
    public void initEvent() {
    }
    @Override
    public void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        orderAdapter=null;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见时执行的操作
            getOrderData();
        } else {
            //不可见时执行的操作
        }
    }

    public void getOrderData(){
          orderAdapter=null;
        RequestParams requestParams=new RequestParams(NetUtil.url+"MusterSelectServlet");
        requestParams.addQueryStringParameter("userId",user_id+"");
        requestParams.addQueryStringParameter("muster_state","本馆召集");//状态为0表示查询全部的订单信息
        requestParams.addQueryStringParameter("venues_id",venues_id+"");
        Log.d("agfhjyir","发送  venues_id"+venues_id);
        requestParams.addQueryStringParameter("address_id",((MyApplication)getActivity().getApplication()).getUsershow().getAddress_id()+"");

        //获取网络数据，获取到之后，设置数据源
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                List<Muster_tbl> newOrders=gson.fromJson(result,new TypeToken<List<Muster_tbl>>(){}.getType());

                Log.d("111222","result"+result);
                for(Muster_tbl newOrder:newOrders ){
                    if(newOrder.getMuster_state().equals("不在显示")){
                        newOrders.remove(newOrder);
                    }
                };
                muster.clear();
                muster.addAll(newOrders);//添加新的集合
                //  fragAllordersListview.setEmptyView(fragAllordersRl);//设置没有数据时，显示
                if(orderAdapter==null) {
                    // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
                    orderAdapter=new CommonAdapter<Muster_tbl>(getActivity(), muster, R.layout.list_muster) {
                        @Override
                        public void convert(ViewHolder viewHolder, final Muster_tbl item, final int position) {

                            Log.d("111222","item.getMuster_number()"+item.getMuster_number());

                            TextView tvCurrent = viewHolder.getViewById(R.id.tv_current);
                            tvCurrent.setText("召集人数 1/"+item.getMuster_number());//人数

                            TextView tvNameMuster = viewHolder.getViewById(R.id.tv_name_muster);//地址
                            tvNameMuster.setText(" 场馆名:"+item.getVenues_tbl().getVenues_name());

                            TextView tvsernameMuster = viewHolder.getViewById(R.id.tv_username_muster);
                            tvsernameMuster.setText("发布者："+item.getUsershow_tbl().getUsershow_name());//发布者

                            TextView tvType2 = viewHolder.getViewById(R.id.tv_type2);
                            tvType2.setText(""+item.getMuster_state());//类型

                            TextView tvType = viewHolder.getViewById(R.id.tv_type);
                            tvType.setText("费用方式"+item.getMuster_type());//类型

                            TextView tvTime = viewHolder.getViewById(R.id.tv_time);
                            tvTime.setText("时间"+item.getMuster_time());//类型


                            ImageLoader myImageLoader;
                            ImageView imageView=viewHolder.getViewById(R.id.imageView);
                            String url2 = item.getUsershow_tbl().getUsershow_head();
                            myImageLoader = new ImageLoader(getActivity());
                            myImageLoader.showImageByUrl(url2, imageView);

                            //具体按钮显示（文本），及点击事件
                            Button btnRight = viewHolder.getViewById(R.id.b_1);
                            btnRight.setFocusable(false);
                            Button btnLeft = viewHolder.getViewById(R.id.b_2);
                            btnLeft.setFocusable(false);
                            //根据订单状态，判断当前显示的文本
                   //         user_id= ((MyApplication) getActivity().getApplication()).getUser().getUser_id();
                            btnLeft.setVisibility(View.VISIBLE);
                         //   btnRight.setVisibility(View.VISIBLE);
//                            if(user_id==item.getUsershow_tbl().getUser_tbl().getUser_id()){
//                                flag=1;//表示发布者是自己
//                            }
                            switch (item.getMuster_state()) {
                                case "正在召集中":
                                    btnLeft.setText("申请加入");
                                    break;
                                case "正在进行中":

                                    btnLeft.setText("中途退出");
                                    break;
                                case "已经结束的":
                                    btnLeft.setText("消除痕迹");
                                    break;
//                                case "正在召集中":
//                                    btnLeft.setText("取消召集");
//                                    break;
//                                case "正在进行中":
//                                    btnLeft.setText("中途退出");
//                                    break;
//                                case "已经结束的1":
//                                    btnLeft.setText("删除召集");
//                                    btnRight.setVisibility(View.VISIBLE);
//                                    btnRight.setText("再次发布");
//                                    break;
                            }
                            //设置按钮点击事件
                            btnLeft.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (item.getMuster_state()) {
                                        case "正在召集中0":
                                            go(item,"申请加入");
                                            break;
                                        case "正在进行中":
                                            go(item,"中途退出");
                                            break;
                                        case "已经结束的":
                                            go(item,"消除痕迹");
                                            break;
//                                        case "正在召集中1":
//                                            go(item,"取消召集");
//                                            break;
//                                        case "正在进行中1":
//                                            go(item,"中途退出");
//                                            break;
//                                        case "已经结束的1":
//                                            go(item,"删除召集");
//                                            break;

                                    }
                                }
                            });

                            btnRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    switch (item.getMuster_state()) {
                                        case "已经结束的":
                                            //    go(item,"再次发布");



                                            break;
                                    }
                                }
                            });

                        }
                    };

                    //listview中显示的是所有的数据信息
                    fragMuster2.setAdapter(orderAdapter);

                    fragMuster2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override//点击此view进行界面的跳转
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Muster_tbl muster_tbl=muster.get(position);

                            Intent intent = new Intent(getActivity(), MustershowActivity.class);
                            Gson gson=new Gson();
                            String gsonmuster_tbl=gson.toJson(muster_tbl);
                            intent.putExtra("gsonmuster_tbl", gsonmuster_tbl);//发送数据
                            Log.d("agtzdscb","frage  etTime"+muster_tbl.getMuster_time()+"");
                            startActivity(intent);

                        }
                    });


                }else{

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


    public void go(Muster_tbl muster_tbl,String type){

        String url = NetUtil.url + "MustergoServlet2";//访问网络的url
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("user_id", user_id + "");
        requestParams.addQueryStringParameter("muster_id", muster_tbl.getMuster_id() + "");
        requestParams.addQueryStringParameter("type",type+"");

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                getOrderData();
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
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