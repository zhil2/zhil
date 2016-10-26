package com.example.administrator.kdc.framet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.GoPayActivity;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Order_tbl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Allen on 2016/10/16.
 */
public class UnpayFragment extends BaseFragment{

    @InjectView(R.id.fragment_allorders_listview)
    ListView fragmentAllordersListview;
    List<Order_tbl> orders=new ArrayList<>();//从服务器获取的订单信息

    CommonAdapter<Order_tbl> orderAdapter;//适配器

    public static final String UNPAY="未付款";
    public static final String UNSEND="已取消";
    String orderState="未付款";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_allorders, null);
        ButterKnife.inject(this,v);
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见时执行的操作
            getOrderData();
        } else {
            //不可见时执行的操作
        }
    }
    //获取网络数据
    public void getOrderData(){
        orderAdapter=null;
        RequestParams requestParams=new RequestParams(NetUtil.url+"OrderQueryServlet");
        requestParams.addQueryStringParameter("userId",((MyApplication)getActivity().getApplication()).getUser().getUser_id()+"");
        requestParams.addQueryStringParameter("orderState",orderState);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Type type=new TypeToken<List<Order_tbl>>(){}.getType();
                List<Order_tbl> newOrders=gson.fromJson(result,type);
                orders.clear();
                orders.addAll(newOrders);
                //设置listview的adapter
                if(orderAdapter==null){
                    orderAdapter=new CommonAdapter<Order_tbl>(getActivity(),orders,R.layout.frag_allorders_item) {
                        @Override
                        public void convert(ViewHolder viewHolder, Order_tbl order, int position) {
                            //设置item中控件的取值

                            initItemView(viewHolder,order,position);
                        }
                    };
                    //listview中显示的是所有的数据信息
                    fragmentAllordersListview.setAdapter(orderAdapter);
                }else{
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

    public void initItemView(ViewHolder viewHolder,Order_tbl order,int count){
        //找控件
        TextView tvPlace=viewHolder.getViewById(R.id.frag_allorders_item_place);
        TextView tvOrderState=viewHolder.getViewById(R.id.frag_allorders_item_trade);
        TextView tvStartTime=viewHolder.getViewById(R.id.frag_allorders_item_starttime);
        TextView tvTimeLength=viewHolder.getViewById(R.id.frag_allorders_item_timelength);
        TextView tvPeople=viewHolder.getViewById(R.id.frag_allorders_item_people);
        TextView tvTotalPrice=viewHolder.getViewById(R.id.frag_allorders_item_totalprice);
        Button btnLeft=viewHolder.getViewById(R.id.btn_item_left);
        Button btnRight=viewHolder.getViewById(R.id.btn_item_right);
        //控件赋值

        tvPlace.setText("场馆:"+order.getVenuesDetail().getVenues_name());
        tvOrderState.setText(order.getOrder_state());
        tvStartTime.setText("预约时间:"+order.getOrder_time());
        tvTimeLength.setText("时长:"+order.getOrder_length());
        tvPeople.setText("人数:"+order.getOrder_number());
        tvTotalPrice.setText("合计:"+order.getTotal_price());

        //设置按钮的初始显示内容
        btnShow(order.getOrder_state(),btnLeft,btnRight);
        //设置按钮点击事件
        btnClick(order.getOrder_state(),order,btnLeft,btnRight,count);


    }

    public void btnShow(String orderState,Button btnLeft,Button btnRight){
        switch (orderState){
            case UNPAY:
                //待付款:
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setText("取消预约");
                btnRight.setText("付款");
                break;
            default:
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
                btnLeft.setText("再次发布");
                btnRight.setText("消除订单");
                break;
        }
    }

    //按钮点击事件
    public void btnClick(final String orderState,final Order_tbl order, Button btnLeft, Button btnRight,final int count){
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断订单状态
                switch (orderState){
                    case UNPAY:
                        //取消订单(更新订单状态，更新界面)
                        updateOrder(order.getOrder_id(),UNSEND);
//
//                        //取消订单：更新当前订单的状态（传参：订单id，当前状态，需要改的状态，返回当前状态的订单记录）
//
//                        //当前的订单集合中的数据源发生改变；直接更新界面
//                        //调用adapter方法更新集合
                        orders.get(count).setOrder_state("取消成功");

                        orderAdapter.notifyDataSetChanged();
                        break;


                }

            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (orderState){
                    case UNPAY:
                        //付款
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                        String orderinfo=gson.toJson(order);
                        Intent intent=new Intent(getActivity(),GoPayActivity.class);
                        intent.putExtra("orderInfo",orderinfo);

                        startActivity(intent);
                        break;
                }
            }
        });

    }

    /**
     * 更改OrderId的订单状态，返回新的所有的订单集合，赋值给list，重新更新适配器

     */
    public void updateOrder(int orderId,String newState){
        RequestParams requestParams=new RequestParams(NetUtil.url+"OrderUpdateServlet");
        requestParams.addQueryStringParameter("orderId",orderId+"");
        requestParams.addQueryStringParameter("newState",newState);

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.i("OrderAllFragment", "updateOrder: "+result);

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

    @Override
    public void initData() {

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
