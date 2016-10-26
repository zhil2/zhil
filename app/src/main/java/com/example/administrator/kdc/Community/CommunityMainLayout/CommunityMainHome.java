package com.example.administrator.kdc.Community.CommunityMainLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseActivity;
import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter;
import com.example.administrator.kdc.Community.AdapterViewHolder.ViewHolder;
import com.example.administrator.kdc.Community.CommunityLayout.CommunityHome;
import com.example.administrator.kdc.Community.MyXListView.NoScrollGridView;
import com.example.administrator.kdc.Community.Search.Community_SearchActivity;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Sports_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class CommunityMainHome extends BaseActivity {//点击图标改变图片及跳转到home
    @InjectView(R.id.return_main)
    ImageView returnMain;
    @InjectView(R.id.main)
    public ImageView main1;//修改的图片
    @InjectView(R.id.tv_searchcommuinty)
    TextView tvSearchcommuinty;
    @InjectView(R.id.community_gridview)
    NoScrollGridView communityGridview;
    private List<Sports_tbl> sports_tbls;//集合
    private CommonAdapter<Sports_tbl> sportsAdapter;//适配器
    public Sports_tbl sports_tbl;//对象
    //public int user_id= ((MyApplication)CommunityMainHome.this.getApplication()).getUser().getUser_id();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_main);
        ButterKnife.inject(this);
        getData();
        Intent intent=getIntent();
        int user_id=intent.getIntExtra("user_id",0);
    }

    @Override
    public void initView() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    public void getData() {
        String url = URL.url + "Sports_Object_Servlet";//查询sports_tbl的数据包括图片以及社区信息
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("sports_type","1");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Sports_tbl>>() {
                        }.getType();
                        sports_tbls = gson.fromJson(result, type);//解析成List<Product>
                        sportsAdapter = new CommonAdapter<Sports_tbl>(CommunityMainHome.this, sports_tbls, R.layout.community_main_griditem) {
                            @Override
                            public void convert(ViewHolder viewHolder, Sports_tbl sports_tbl, int position) {
                                ImageView tvimage = viewHolder.getViewById(R.id.iv_communityimage_item);
                                String imageurl=sports_tbl.getSportstype_tbl().getSportstype_picture();
                                Log.i("xUtils_Activity", "onSuccess: get post_tblstrach11111111111111111111" + imageurl);
                                //showImage(tvimage,imageurl);
                                TextView tvname = viewHolder.getViewById(R.id.tv_communityname_item);
                                tvname.setText(sports_tbl.getSportstype_tbl().getSportstype_name());
                            }
                        };
                        communityGridview.setAdapter(sportsAdapter);
                        communityGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {//item点击事件
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                        sports_tbl = sports_tbls.get(position);//点击右上角图标变化
//                                showImage(main1,sports_tbl.getSportstype_tbl().getSportstype_picture());
                               Intent intent2 = new Intent(CommunityMainHome.this, CommunityHome.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("community_tbl", sports_tbl.getCommunity_tbl());
                            Log.i("CommunityHome", "onClick: "+sports_tbl.getCommunity_tbl());
                            intent2.putExtras(bundle);
                            startActivity(intent2);
                            }
                        });
                        Log.i("xUtils_Activity", "onSuccess: get post_tblstrach" + result);
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
    public void showImage(ImageView imageView,String imageUrl){
                               String url2= URL.url+imageUrl;
                                //setAutoRotate(true)
                                ImageOptions imageOptions=new ImageOptions.Builder()
                                        .setCircular(true)//截图为圆图
                                        .setFailureDrawableId(R.drawable.main)
                                        .setLoadingDrawableId(R.drawable.main)
                                        .setCrop(true)
                                        .setAutoRotate(true)
                                        .build();
                                x.image().bind(imageView,url2,imageOptions);
    }

    @OnClick({R.id.return_main, R.id.main, R.id.tv_searchcommuinty})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_main://返回主页
                finish();
                break;
            case R.id.main://修改的图片//跳转到home
                break;
            case R.id.tv_searchcommuinty://搜索社区
                Intent intent3 = new Intent(this, Community_SearchActivity.class);
                startActivity(intent3);
                break;
        }
    }

}
