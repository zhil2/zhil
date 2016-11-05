package com.example.administrator.kdc.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.service.LocationService;
import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Newuseradd_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class one extends Activity {
    @InjectView(R.id.listView_one)
    ListView listViewOne;

    CommonAdapter<Map<Newuseradd_tbl,Double>> orderAdapter;

    List<Map<Newuseradd_tbl,Double>> venueslist = new ArrayList<Map<Newuseradd_tbl,Double>>();



    private LocationService locationService;

    String a1, a2, a3, a4, a5, a6;
    Double w = 0.0, j = 0.0;
    int user_id = 0;
    boolean flag = true;//获取附近的人只做一次



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // -----------demo view config ------------
        setContentView(R.layout.activity_one);
        ButterKnife.inject(this);
        user_id = ((MyApplication) getApplication()).getUser().getUser_id();

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
        super.onStop();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        // -----------location config ------------
        locationService = ((MyApplication) getApplication()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        int type = getIntent().getIntExtra("from", 0);
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        locationService.start();// 定位SDK

    }


    /*****
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                StringBuffer sb = new StringBuffer(256);

                sb.append("\nlatitude : ");// 纬度
                sb.append(location.getLatitude());
                w = location.getLatitude();
                sb.append("\nlontitude : ");// 经度
                sb.append(location.getLongitude());
                j = location.getLongitude();

                if (flag) {
                    flag = false;
                    showLocation2();
                }


            }
        }
    };

    private void showLocation2() {
        //+location.getLatitude()+",%20"+location.getLongitude()  注：经度需要前面加20！！！！！！！！！
        String url = "http://api.map.baidu.com/geocoder?output=json&location=" + w + ",%20" + j + "&key=hN4VoCixFHSih157j8PaX8mpDSkRvolb";
        RequestParams params = new RequestParams(url);
        // RequestParams params = new RequestParams("http://api.map.baidu.com/geocoder?output=json&location=32.983424"+",%20116.322987"+"&key=hN4VoCixFHSih157j8PaX8mpDSkRvolb");
        x.http().get(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
                String a = result;
                Pattern p = Pattern.compile("\t|\r|\n");
                Matcher m = p.matcher(a);
                a = m.replaceAll("");
                a = a.replaceAll(" +", "");
                //省
                a1 = a.substring(a.indexOf("province") + 11, a.indexOf("street") - 3);
                //市
                a2 = a.substring(a.indexOf("city") + 7, a.indexOf("direct") - 3);
                //县
                a3 = a.substring(a.indexOf("district") + 11, a.indexOf("province") - 3);
                //街道
                a4 = a.substring(a.indexOf("street") + 9, a.indexOf("street_number") - 3);
                //门牌
                a5 = a.substring(a.indexOf("street_number") + 16, a.indexOf("cityCode") - 4);
                //全部地址信息
                String a6 = a.substring(a.indexOf("formatted_address") + 20, a.indexOf("business") - 3);
                Log.d("rqwer233", "wz" + a6);


                RequestParams params = new RequestParams(NetUtil.url + "NewuseraddServlet");
                params.addBodyParameter("j", j + "");//post方法的传值
                params.addBodyParameter("w", w + "");
                params.addBodyParameter("user_id", user_id + "");
                params.addBodyParameter("a1", a1 + "");
                params.addBodyParameter("a2", a2 + "");
                params.addBodyParameter("a3", a3 + "");
                params.addBodyParameter("a4", a4 + "");
                params.addBodyParameter("a5", a5 + "");

                x.http().get(params, new CommonCallback<String>() {//post的方式网络通讯
                    @Override
                    public void onSuccess(String result) {

                        Gson gson = new Gson();

                        List<Newuseradd_tbl> newOrders = gson.fromJson(result, new TypeToken<List<Newuseradd_tbl>>() {
                        }.getType());

                        Map<Newuseradd_tbl,Double> map=null;
                        Double h =1000.0;

                        for(Newuseradd_tbl newuseradd_tbl:newOrders){
                            h= Distance(j,w,newuseradd_tbl.getNewuseradd_j(),newuseradd_tbl.getNewuseradd_w());
                            map.put(newuseradd_tbl,h);
                            venueslist.add(map);

                        }
                        sorting2(venueslist);


                        if (orderAdapter == null) {
                            // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
                            orderAdapter = new CommonAdapter<Map<Newuseradd_tbl,Double>>(one.this, venueslist, R.layout.list_one) {
                                @Override
                                public void convert(final ViewHolder viewHolder, final Map<Newuseradd_tbl,Double> item2, final int position) {

                                    TextView venues_name = viewHolder.getViewById(R.id.tv_name);
                                    venues_name.setText("11111");
//                                    TextView tv_time = viewHolder.getViewById(R.id.tv_time);
//                                    TextView tv_nr = viewHolder.getViewById(R.id.tv_nr);
//                                    ImageView iv_tx=viewHolder.getViewById(R.id.iv_tx);
//
//                                    venues_name.setText(" "+item);

//                                    tv_time.setText(" "+item2.getReply_date());
//                                    tv_nr.setText(" "+item2.getReply_text());
//
//                                    url2=item2.getUsershow_tbl().getUsershow_head();
//                                    myImageLoader = new ImageLoader(EvaluationActivity.this);
//                                    myImageLoader.showImageByUrl(url2, iv_tx);


                                }

                            };

                            listViewOne.setAdapter(orderAdapter);

                        } else {
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


    public static double Distance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2
                * R
                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
                * Math.cos(lat2) * sb2 * sb2));
        return d;
    }



    public void sorting2(List venuesList) {

        Collections.sort(venuesList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Map<Newuseradd_tbl,Double> venues1 = (Map<Newuseradd_tbl,Double>) o1;
                Map<Newuseradd_tbl,Double> venues2 = (Map<Newuseradd_tbl,Double>) o2;

                Log.d("afgqwew","venues1.get(venues1.keySet())"+venues1.get(venues1.keySet()));

                if (venues1.get(venues1.keySet())> venues2.get(venues2.keySet())) {
                    return -1;
                } else if (venues1.get(venues1.keySet()) == venues2.get(venues2.keySet())) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });

    }





}
