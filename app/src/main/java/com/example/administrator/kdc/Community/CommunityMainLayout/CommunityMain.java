package com.example.administrator.kdc.Community.CommunityMainLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseActivity;
import com.example.administrator.kdc.Community.CommunityMainLayout.SportsGridview.SportsGridviewAdapter;
import com.example.administrator.kdc.Community.MyXListView.NoScrollGridView;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.vo.Sportstype_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CommunityMain extends BaseActivity {//点击图标改变图片及跳转到home
    @InjectView(R.id.return_main)
    ImageView returnMain;
    @InjectView(R.id.main)
    public ImageView main1;//修改的图片
    @InjectView(R.id.community_gridview)
    NoScrollGridView communityGridview;
    @InjectView(R.id.tbn_sendsports)
    Button tbnSendsports;
    static int count=0;
    int user_id;
   // private static List<Sports_tbl> sports_tbls=new ArrayList<Sports_tbl>();//集合
    private SportsGridviewAdapter sportsAdapter;//适配器
    public static List<Sportstype_tbl> sportstype_tbls=new ArrayList<Sportstype_tbl>();
    public static List<Sportstype_tbl> sportstype_tbls2=new ArrayList<Sportstype_tbl>();
    //public static Sports_tbl sports_tbl;//对象
  //  public int user_id = ((MyApplication) getApplication()).getUser().getUser_id();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_main);
        ButterKnife.inject(this);
        //Intent intent=getIntent();
        //user_id=intent.getIntExtra("user_id",0);
        sportsAdapter = new SportsGridviewAdapter(sportstype_tbls, this);
        Log.i("sportsAdapter", "onCreate: ");
        Log.i("xUtils_Activity", "onSuccess: get post_tblstrach222221111" + sportstype_tbls);
        communityGridview.setAdapter(sportsAdapter);
        Log.i("xUtils_Activity", "onSuccess: get post_tblstrach22222" + sportstype_tbls);
        communityGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sportsAdapter.setSelection(position);
                Log.i("xUtils_Activity", "onSuccess: get post_tblstrach333333" + sportstype_tbls);
               // Map<Integer, String> map = new HashMap<Integer, String>();
                LinearLayout communitygridview= (LinearLayout) view.findViewById(R.id.community_gridview);
                if ( communitygridview.isSelected() == false) {//选择选中
                    communitygridview.setSelected(true);
                        sportstype_tbls2.add(sportstype_tbls.get(position));
                    Log.i("xUtils_Activity", "onSuccess: get post_tblstrach444444" + sportstype_tbls);
                    //map.put(position, sports_tbls.get(position).getSportstype_tbl().getSportstype_name());
                } else {//选择取消选中
                    communitygridview.setSelected(false);
                    Iterator<Sportstype_tbl> it = sportstype_tbls2.iterator();
                    int i=0;
                    while (it.hasNext()){
                        i=it.next().getSportstype_id();
                        if (i==position){
                            it.remove();
                        }
                    }
//                    Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
//                    int key = 0;
//                    while (it.hasNext()) {
//                        key = it.next().getKey();
//                        if (key == position) {
//                            it.remove();
//                        }
//                    }
                    //Log.i("CommunityMain", "onItemClick: it"+it);
                }
                Log.i("CommunityMain", "onItemClick: map ");
                sportsAdapter.setSelection(position);
                sportsAdapter.notifyDataSetChanged();
            }
        });
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
//    public void showImage(ImageView imageView,String imageUrl){//显示图片
//                               String url2= URL.url+imageUrl;
//                                //setAutoRotate(true)
//                                ImageOptions imageOptions=new ImageOptions.Builder()
//                                        .setCircular(true)//截图为圆图
////                                        .setFailureDrawableId(R.mipmap.ic_launcher)
////                                        .setLoadingDrawableId(R.mipmap.ic_launcher)
////                                        .setCrop(true)
////                                        .setAutoRotate(true)
//                                        .build();
//                                x.image().bind(imageView,url2,imageOptions);
//    }
}
