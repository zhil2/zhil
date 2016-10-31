package com.example.administrator.kdc.Community.CommunityMainLayout;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseActivity;
import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter2;
import com.example.administrator.kdc.Community.AdapterViewHolder.ViewHolder;
import com.example.administrator.kdc.Community.MyXListView.NoScrollGridView;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.vo.Sportstype_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CommunityMain extends BaseActivity {//点击图标改变图片及跳转到home
    public ImageView tvimage;
    ImageLoader myImageLoader;
    @InjectView(R.id.return_main)
    ImageView returnMain;
    @InjectView(R.id.main)
    public ImageView main1;//修改的图片
    @InjectView(R.id.community_gridview)
    NoScrollGridView communityGridview;
    @InjectView(R.id.tbn_sendsports)
    Button tbnSendsports;
    //SportsAdapter sportsAdapter;
    static int count=0;
    int user_id;
    private int clickTemp = -1;
    public Map<Integer, Integer> map = new HashMap<Integer, Integer>();//key：位置id，value：运动id
    private CommonAdapter2<Sportstype_tbl> sportsAdapter;//适配器
    public static List<Sportstype_tbl> sportstype_tbls=new ArrayList<Sportstype_tbl>();
    public static List<Sportstype_tbl> sportstype_tbls2=new ArrayList<Sportstype_tbl>();
    public static Sportstype_tbl sportstype_tbl;//对象
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_main);
        ButterKnife.inject(this);
        getData();
//        Intent intent=getIntent();
//        user_id=intent.getIntExtra("user_id",0);
        if(sportstype_tbls!=null) {
            communityGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    sportsAdapter.setSelection(position);
                    tvimage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("xUtils_Activity", "onSuccess: get post_tblstrach333333" + sportstype_tbls);
                            if (tvimage.isSelected() == false) {//选择选中
                                tvimage.setSelected(true);
                                // sportstype_tbls2.add(sportstype_tbls.get(position));
                                Log.i("xUtils_Activity", "onSuccess: get post_tblstrach444444" + sportstype_tbls);
                                map.put(position, sportstype_tbls.get(position).getSportstype_id());
                                Log.i("Community", "onItemClick: 111"+map);
                            } else if(tvimage.isSelected()==true){//选择取消选中
                                tvimage.setSelected(false);
//                    Iterator<Sportstype_tbl> it = sportstype_tbls2.iterator();
//                    int i=0;
//                    while (it.hasNext()){
//                        i=it.next().getSportstype_id();
//                        if (i==position){
//                            it.remove();
//                        }
//                    }
                                Iterator<Map.Entry<Integer, Integer>> it = map.entrySet().iterator();
                                int key = 0;
                                while (it.hasNext()) {
                                    key = it.next().getKey();
                                    if (key == position) {
                                        it.remove();
                                    }
                                    Log.i("Community", "onItemClick: 111222"+map);
                                }
                                //Log.i("CommunityMain", "onItemClick: it"+it);
                            }
                        }
                    });
                    Log.i("CommunityMain", "onItemClick: map ");
                    sportsAdapter.setSelection(position);
                    sportsAdapter.notifyDataSetChanged();
                }
            });
        }

    }

    @Override
    public void initView() {
    }

    @Override
    public void initEvent() {
    }

    @Override
    public void getData() {//获取网络数据
        String url = URL.url + "Sportstyepe_tbl_showAll_Servlet";//查询sports_tbl的数据包括图片以及社区信息
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Sportstype_tbl>>() {
                        }.getType();
                        sportstype_tbls = gson.fromJson(result, type);//解析成List<Product>
                        Log.i("xUtils_Activity", "onSuccess: get post_tblstrach" + result);
                        Log.i("xUtils_Activity", "onSuccess: get post_tblstrach11111" + sportstype_tbls);
                        sportsAdapter = new CommonAdapter2<Sportstype_tbl>(CommunityMain.this, sportstype_tbls, R.layout.community_main_griditem) {
                            @Override
                            public void convert(ViewHolder viewHolder, Sportstype_tbl sportstype_tbl, int position) {
                                tvimage = viewHolder.getViewById(R.id.iv_communityimage_item);
                                String imageurl=sportstype_tbl.getSportstype_picture();
                                Log.i("xUtils_Activity", "onSuccess: get post_tblstrach11111111111111111111" + imageurl);
                                myImageLoader = new ImageLoader(CommunityMain.this);
                             //   myImageLoader.showImageByUrl(imageurl, tvimage);//加载图片//显示图片
                                TextView tvname = viewHolder.getViewById(R.id.tv_communityname_item);
                                tvname.setText(sportstype_tbl.getSportstype_name());
                                if (clickTemp == position) {
                                    tvimage.setBackgroundResource(R.drawable.shape7);//点击了
                                } else {
                                    tvimage.setBackgroundResource(R.drawable.shape5);;//未点击
                                }

                            }
                            public void setSelection(int position) {
                                clickTemp = position;
                            }//选择

                        };
                        communityGridview.setAdapter(sportsAdapter);
                        communityGridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.i("xUtils_Activity", "onCancelled: get" + cex);
                    }

                    @Override
                    public void onFinished() {
                        Log.i("xUtils_Activity", "onFinished: get");
                    }
                }
        );
    }
    public void insertSports(int sportstype_id){
        String url = URL.url + "Sports_tbl_insertData_Servlet";//查询sports_tbl的数据包括图片以及社区信息
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("sportstype_id", sportstype_id+"");//运动id
        requestParams.addQueryStringParameter("sports_type", "1");//类型id
        requestParams.addQueryStringParameter("sports_object", ((MyApplication) CommunityMain.this.getApplication()).getUser().getUser_id()+"");
        //((MyApplication) CommunityMain.this.getApplication()).getUser().getUser_id()+""
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
                            Toast.makeText(getApplicationContext(), "选择成功", Toast.LENGTH_SHORT).show();}

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        Log.i("xUtils_Activity", "onCancelled: get" + cex);
                    }

                    @Override
                    public void onFinished() {
                        Log.i("xUtils_Activity", "onFinished: get");
                    }
                }
        );
    }
    @OnClick({R.id.return_main, R.id.main, R.id.tbn_sendsports})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_main://返回上一级
                finish();
                break;
            case R.id.main://图片
                break;
            case R.id.tbn_sendsports://发送运动项目
                for (int i=0;i<sportstype_tbls2.size();i++){
                    count+=1;
                    insertSports(sportstype_tbls2.get(i).getSportstype_id());
                }
                break;
        }
    }
}
