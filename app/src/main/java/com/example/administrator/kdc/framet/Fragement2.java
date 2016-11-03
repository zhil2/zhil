package com.example.administrator.kdc.framet;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.MapActivity;
import com.example.administrator.kdc.activity.VenuesshowActivity;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.VC_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/9/18.
 */
public class Fragement2 extends Fragment {

    List<VC_tbl> venueslist = new ArrayList<VC_tbl>();

    CommonAdapter<VC_tbl> orderAdapter;

    int user_id, ys = 1, zhan = 0, cai = 0;

    ImageLoader myImageLoader;

    String flag = "00";
    VC_tbl item;
    Map<Integer, Boolean> checkStatus = new HashMap<>();//是否收藏
    Map<Integer, Integer> checkStatus2 = new HashMap<>();//评价
    Map<Integer, Integer> good = new HashMap<>();//赞
    Map<Integer, Integer> no = new HashMap<>();//踩


    int orderFlag = 0;
    int addFlag = 0;
    int typeflag=0;
    List<String> popContents = new ArrayList<String>();
    List<String> popContents2 = new ArrayList<String>();
    Boolean flag1 = true, flag2 = true, flag3 = true ,flag11 = flag1, flag22 = flag2, flag33 = flag3;;

    @InjectView(R.id.button2)
    Button button2;
    @InjectView(R.id.button3)
    Button button3;
    @InjectView(R.id.button4)
    Button button4;
    @InjectView(R.id.linearLayout)
    LinearLayout linearLayout;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.button5)
    Button button5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment2, null);
        ButterKnife.inject(this, v);

        popContents.add("按好评排序");
        popContents.add("按差评逆排序");
        popContents.add("按场馆容量排序");

        popContents2.add("显示附近");
        popContents2.add("显示本镇/路");
        popContents2.add("显示本区");
        popContents2.add("显示本市");
        popContents2.add("显示本省");

        user_id = ((MyApplication) getActivity().getApplication()).getUser().getUser_id();
        getOrderData();
        return v;

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    public void getOrderData() {
        //  Log.d("wefdszg","我启动了");

        orderAdapter = null;
        RequestParams params = new RequestParams(NetUtil.url + "MaplistServlet");
        params.addBodyParameter("userId", user_id + "");//post方法的传值
        params.addBodyParameter("ys", ys + "");//post方法的传值
        params.addBodyParameter("orderFlag", orderFlag + "");//post方法的传值
        params.addBodyParameter("addFlag", addFlag + "");//post方法的传值

        //   Log.d("wefdszg","我启动了 params"+params);

        //获取网络数据，获取到之后，设置数据源
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //     Log.d("wefdszg","我启动了   result"+result);
                Gson gson = new Gson();
                List<VC_tbl> newOrders = gson.fromJson(result, new TypeToken<List<VC_tbl>>() {
                }.getType());

                venueslist.clear();
                venueslist.addAll(newOrders);//添加新的集合

                for (int i = 0; i < venueslist.size(); i++) {
                    if (venueslist.get(i).getFlag() == 0) {
                        checkStatus.put(i, false);
                    } else {
                        checkStatus.put(i, true);
                    }
                    checkStatus2.put(i, venueslist.get(i).getFlag2());
                    good.put(i, venueslist.get(i).getVenues_tbl().getVenues_yes());
                    no.put(i, venueslist.get(i).getVenues_tbl().getVenues_no());

                  //  Log.d("tzcg", "checkStatus    " + checkStatus.get(i) + "      i" + i + "     venueslist.get(i).getFlag2()" + venueslist.get(i).getFlag2());
                }

                //  fragAllordersListview.setEmptyView(fragAllordersRl);//设置没有数据时，显示
                if (orderAdapter == null) {
                    // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
                    orderAdapter = new CommonAdapter<VC_tbl>(getActivity(), venueslist, R.layout.list) {
                        @Override
                        public void convert(final ViewHolder viewHolder, final VC_tbl item2, final int position) {

                            TextView venues_name = viewHolder.getViewById(R.id.tv_name);
                            TextView address_id = (TextView) viewHolder.getViewById(R.id.tv_add);
                            TextView venues_type = (TextView) viewHolder.getViewById(R.id.tv_type);
                            TextView venues_ceiling = (TextView) viewHolder.getViewById(R.id.tv_current);
                            TextView venues_yes = (TextView) viewHolder.getViewById(R.id.tv_yes);
                            TextView venues_no = (TextView) viewHolder.getViewById(R.id.tv_no);
                            ImageView venues_image = (ImageView) viewHolder.getViewById(R.id.imageView);
                            ImageView imageView13 = (ImageView) viewHolder.getViewById(R.id.imageView13);
                            ImageView imageView12 = (ImageView) viewHolder.getViewById(R.id.imageView12);
                            ImageView sc = (ImageView) viewHolder.getViewById(R.id.iv_sc);
                            item = venueslist.get(position);

                            zhan = (int) item.getVenues_tbl().getVenues_yes();
                            cai = (int) item.getVenues_tbl().getVenues_no();

                            sc.setTag(position);//定义唯一位置标识

                         //   Log.d("ewrwe", "    getTag()   " + getTag() + "     position" + position);


                            sc.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                //    Log.d("111", "我点击了");
                                    getTag();

                                    if (checkStatus.get(position)) {

                                        checkStatus.put(position, false);
                                    } else if (!checkStatus.get(position)) {
                                        checkStatus.put(position, true);
                                    }

                                    sc(user_id, item2.getVenues_tbl().getVenues_id(), 0, v);

                                }
                            });

                            imageView12.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                 //   Log.d("111", "我赞了");
                                    getTag();
                                    if (checkStatus2.get(position) == 1) {
                                        flag = "10";
                                        checkStatus2.put(position, 0);
                                        good.put(position, good.get(position) - 1);
                                        no.put(position, no.get(position));
                                        a(viewHolder, position);
                                        evaluation(0, item2.getVenues_tbl().getVenues_id(), viewHolder);
                                    } else if (checkStatus2.get(position) == 2) {
                                        flag = "21";
                                        checkStatus2.put(position, 1);
                                        good.put(position, good.get(position) + 1);
                                        no.put(position, no.get(position) - 1);
                                        a(viewHolder, position);

                                        evaluation(1, item2.getVenues_tbl().getVenues_id(), viewHolder);

                                    } else if (checkStatus2.get(position) == 0 || checkStatus2.get(position) == -1) {
                                        flag = "01";
                                        checkStatus2.put(position, 1);
                                        good.put(position, good.get(position) + 1);
                                        no.put(position, no.get(position));
                                        a(viewHolder, position);

                                        evaluation(1, item2.getVenues_tbl().getVenues_id(), viewHolder);

                                    }
                                }
                            });

                            imageView13.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(final View v) {
                                  //  Log.d("111", "我踩了");
                                    if (checkStatus2.get(position) == 1) {
                                        flag = "12";
                                        checkStatus2.put(position, 2);
                                        good.put(position, good.get(position) - 1);
                                        no.put(position, no.get(position) + 1);
                                        a(viewHolder, position);

                                        evaluation(2, item2.getVenues_tbl().getVenues_id(), viewHolder);

                                    } else if (checkStatus2.get(position) == 2) {
                                        flag = "20";
                                        checkStatus2.put(position, 0);
                                        good.put(position, good.get(position));
                                        no.put(position, no.get(position) - 1);
                                        a(viewHolder, position);
                                        evaluation(0, item2.getVenues_tbl().getVenues_id(), viewHolder);
                                    } else if (checkStatus2.get(position) == 0 || checkStatus2.get(position) == -1) {
                                        flag = "02";

                                        checkStatus2.put(position, 2);
                                        good.put(position, good.get(position));
                                        no.put(position, no.get(position) + 1);
                                        a(viewHolder, position);
                                        evaluation(2, item2.getVenues_tbl().getVenues_id(), viewHolder);
                                    }
                                }
                            });


                            String b = "";
                            switch (item.getVenues_tbl().getVenues_type()) {
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

                            venues_name.setText(item.getVenues_tbl().getVenues_name());
                            address_id.setText("" + item.getVenues_tbl().getAddress_tbl().getAddress_city() + "" + item.getVenues_tbl().getAddress_tbl().getAddress_county() + "" + item.getVenues_tbl().getAddress_tbl().getAddress_town() + "" + item.getVenues_tbl().getAddress_tbl().getAddress_show());
                            venues_type.setText(b + "");
                            venues_ceiling.setText("     容量：" + item.getVenues_tbl().getVenues_ceiling() );

                            String url2;

                            venues_yes.setText(good.get(position) + "");
                            venues_no.setText(no.get(position) + "");

                         //   Log.d("etyrturtd", "tag" + sc.getTag() + "       position" + position + "        checkStatus.get(position)" + checkStatus.get(position));

                            if (checkStatus.get(position)) {
                                sc.setBackgroundResource(R.drawable.sc);
                            } else {
                                sc.setBackgroundResource(R.drawable.sc2);
                            }


                            if (checkStatus2.get(position) == 1) {
                                imageView13.setBackgroundResource(R.drawable.no);
                                imageView12.setBackgroundResource(R.drawable.good2);
                            } else if (checkStatus2.get(position) == 2) {
                                imageView13.setBackgroundResource(R.drawable.no2);
                                imageView12.setBackgroundResource(R.drawable.good);
                            } else {
                                imageView13.setBackgroundResource(R.drawable.no);
                                imageView12.setBackgroundResource(R.drawable.good);
                            }

                            url2 = item.getVenues_tbl().getVenuesshow_tbl().getVenuesshow_portrait();

                            //    Log.d("BBBBB","url2"+url2);
                            myImageLoader = new ImageLoader(getActivity());
                            myImageLoader.showImageByUrl(url2, venues_image);
                        }
                    };

                    //listview中显示的是所有的数据信息
                    listView.setAdapter(orderAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override//点击此view进行界面的跳转
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            VC_tbl upload = venueslist.get(position);
                            //   Log.d("BBBBB","url2 ven____________________"+upload.getVenuesshow_tbl().getVenuesshow_portrait());
                            Intent intent = new Intent(getActivity(), VenuesshowActivity.class);
                            intent.putExtra("user_id", user_id);
                            intent.putExtra("vc_tbl", upload);//发送数据


                            int sc=-1;
                            if (checkStatus.get(position)){
                                sc=1;
                            }
                        //    Log.d("dfdsaf","f2 sc   "+sc);
                            intent.putExtra("sc", sc);//发送数据
                            intent.putExtra("yn", checkStatus2.get(position));//发送数据
                            intent.putExtra("yes", good.get(position));//发送数据
                            intent.putExtra("no", no.get(position));//发送数据

                            startActivityForResult(intent, 3);
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


    @OnClick({R.id.button2, R.id.button3, R.id.button4,R.id.button5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.button3:
                initPopupWindow(view);

                break;
            case R.id.button4:
                initPopupWindow2(view);
                break;
            case R.id.button5:

                final String[] hobbies = {"显示支持预约的付费场地", "显示不支持预约的付费场地", "显示免费的野场地"};
                new AlertDialog.Builder(getActivity())
                        .setTitle("选择显示的场地类型（多选）")
                        .setMultiChoiceItems(hobbies, new boolean[]{flag1, flag2, flag3}, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (which == 0) {
                                    flag1 = !flag1;
                                } else if (which == 1) {
                                    flag2 = !flag2;
                                } else if (which == 2) {
                                    flag3 = !flag3;
                                }
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                if (flag1&&flag2&&flag3) {
                                    typeflag=0;//全选
                                }else
                                if (flag1&&flag2) {
                                    typeflag=1;
                                }else
                                if (flag1&&flag3) {
                                    typeflag=2;
                                }else
                                if (flag2&&flag3) {
                                    typeflag=3;
                                }else
                                if (flag1&&flag3) {
                                    typeflag=4;
                                }else
                                if(flag1){
                                    typeflag=5;
                                }else
                                if(flag2){
                                    typeflag=6;
                                }else
                                if(flag3){
                                    typeflag=7;
                                }
                                else{
                                    typeflag=8;
                                }

                            }


                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                flag1 = flag11;
                                flag2 = flag22;
                                flag3 = flag33;

                            }
                        })
                        .show();
                break;

        }
    }


    public void initPopupWindow(View v) {
        //content
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.lv_zonghe_paixu, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 200);
        //listview设置数据源
        ListView lv = (ListView) view.findViewById(R.id.lv_zonghe_paixu);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.lv_item_zonghe_paixu, popContents);
        lv.setAdapter(arrayAdapter);
        //popupwiondow外面点击，popupwindow消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示在v的下面
        popupWindow.showAsDropDown(v);
        //listview的item点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupwidow
                popupWindow.dismiss();
                //排序
                if (position == 0) {
                    orderFlag = 0;
                } else if (position == 1) {
                    orderFlag = 1;
                } else if (position == 2) {
                    orderFlag = 2;
                }
                //排序
                button3.setText(popContents.get(position));
                getOrderData();
            }
        });
    }


    public void initPopupWindow2(View v) {
        //content
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.lv_zonghe_paixu, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 200);
        //listview设置数据源
        ListView lv = (ListView) view.findViewById(R.id.lv_zonghe_paixu);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.lv_item_zonghe_paixu, popContents2);
        lv.setAdapter(arrayAdapter);
        //popupwiondow外面点击，popupwindow消失
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //显示在v的下面
        popupWindow.showAsDropDown(v);
        //listview的item点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关闭popupwidow
                popupWindow.dismiss();
                //排序
                if (position == 0) {
                    addFlag = 0;
                } else if (position == 1) {
                    addFlag = 1;
                } else if (position == 2) {
                    addFlag = 2;
                } else if (position == 3) {
                    addFlag = 3;
                } else if (position == 4) {
                    addFlag = 4;
                }
                button4.setText(popContents2.get(position));
                //排序
                getOrderData();
            }
        });
    }


    public void sc(int user_id, int collection_object, int flag, final View v) {

        RequestParams params = new RequestParams(NetUtil.url + "CollectionServlet");
        params.addBodyParameter("user_id", user_id + "");//post方法的传值
        params.addBodyParameter("collection_type", 1 + "");
        params.addBodyParameter("collection_object", collection_object + "");
        params.addBodyParameter("flag", flag + "");
        //     Toast.makeText(getActivity(), "正在登录中，请稍等。。。", Toast.LENGTH_SHORT).show();
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                if (result.equals("收藏成功")) {
                    v.setBackgroundResource(R.drawable.sc);
                } else {
                    v.setBackgroundResource(R.drawable.sc2);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
               // Log.e("BBBBB", "u3  get no");

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

    public void a(ViewHolder viewHolder, int position) {

        ImageView imageView13 = (ImageView) viewHolder.getViewById(R.id.imageView13);
        ImageView imageView12 = (ImageView) viewHolder.getViewById(R.id.imageView12);
        TextView venues_yes = (TextView) viewHolder.getViewById(R.id.tv_yes);
        TextView venues_no = (TextView) viewHolder.getViewById(R.id.tv_no);

        if (flag.equals("10")) {
            imageView13.setBackgroundResource(R.drawable.no);
            imageView12.setBackgroundResource(R.drawable.good);
        } else if (flag.equals("21")) {

            imageView13.setBackgroundResource(R.drawable.no);
            imageView12.setBackgroundResource(R.drawable.good2);
        } else if (flag.equals("01")) {

            imageView13.setBackgroundResource(R.drawable.no);
            imageView12.setBackgroundResource(R.drawable.good2);
        }
        if (flag.equals("20")) {

            imageView13.setBackgroundResource(R.drawable.no);
            imageView12.setBackgroundResource(R.drawable.good);
        } else if (flag.equals("12")) {

            imageView13.setBackgroundResource(R.drawable.no2);
            imageView12.setBackgroundResource(R.drawable.good);
        } else if (flag.equals("02")) {

            imageView13.setBackgroundResource(R.drawable.no2);
            imageView12.setBackgroundResource(R.drawable.good);
        }

        venues_yes.setText(good.get(position) + "");
        venues_no.setText(no.get(position) + "");


    }


    public void evaluation(int flag2, int venues_id, final ViewHolder viewHolder) {

        RequestParams params = new RequestParams(NetUtil.url + "EvaluationServlet");
        params.addBodyParameter("flag", flag2 + "");//post方法的传值
        params.addBodyParameter("user_id", user_id + "");
        params.addBodyParameter("type", 2 + "");
        params.addBodyParameter("Venues_id", venues_id + "");
      //  Log.d("safgdfdfb", "启动传值  Venues_id" + venues_id);
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {


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


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent date) {
//     //   Log.d("124436345","详情的回调1111111111");
//        switch (requestCode) {
//
//            case 2:
//                if (resultCode == 2) {
//
//               //     Log.d("124436345","详情的回调22222222");
//
//                    getOrderData();
//                }
//        }
//    }


}