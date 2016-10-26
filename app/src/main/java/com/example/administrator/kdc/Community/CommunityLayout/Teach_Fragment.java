package com.example.administrator.kdc.Community.CommunityLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseFragment;
import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter;
import com.example.administrator.kdc.Community.AdapterViewHolder.ViewHolder;
import com.example.administrator.kdc.Community.CommunityPostDetails.ComunityPost_details_Activity;
import com.example.administrator.kdc.Community.MyXListView.MyXListView;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Post_tbl;
import com.google.gson.Gson;
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
 * Created by kskjf on 2016/9/27.
 */
public class Teach_Fragment extends BaseFragment implements MyXListView.OnXListViewListener {
    @InjectView(R.id.myXListView)
    public MyXListView myXListView;
    private int index = 0;
    private int MAXVALUE = 10;
    private Handler handler = new Handler();
    private List<Post_tbl> dataList = new ArrayList<Post_tbl>();
    private List<Post_tbl> post_tblsteach = new ArrayList<Post_tbl>();
    CommonAdapter<Post_tbl> teachAdapter;
    public View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MyFragment", "onCreateView: GovFragment");
        if(v !=null){
            ViewGroup parent= (ViewGroup) v.getParent();
            if (parent!=null){
                parent.removeView(v);
            }
            return v;
        }//判断view并且是否保存加载布局，否则重新生成
        v = inflater.inflate(R.layout.activity_community_post_listview, null);
        ButterKnife.inject(this, v);
        setRefreshing();
            loadData();
            setListAdapter();
            myXListView.setOnXListViewListener(this);
        return v;
    }

    @Override
    public void initView() {

    }
    @Override
    public void onHiddenChanged(boolean hidden) {//每次点击刷新fragment界面
        if(hidden){
        }
        else{
            setRefreshing();
            loadData();
            setListAdapter();
            myXListView.setOnXListViewListener(this);
        }
    }
    @Override
    public void initEvent() {
    }
    public void getData() {
        String url = URL.url + "Post_tbl_getPostById_servlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("community_id", CommunityHome.community_id+"");
        requestParams.addQueryStringParameter("post_type",3+"");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Post_tbl>>() {
                        }.getType();
                        ArrayList<Post_tbl> post_tbls = gson.fromJson(result, type);//解析成List<Product>
                        post_tblsteach.clear();
                        post_tblsteach.addAll(post_tbls);
                        Log.i("xUtils_Activity", "成功2" + post_tblsteach);
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
    public void setListAdapter() {
            teachAdapter = new CommonAdapter<Post_tbl>(getActivity(), dataList, R.layout.community_post_textview) {
                @Override
                public void convert(ViewHolder viewHolder, Post_tbl post_tbl, int position) {
                    TextView tvendtime = viewHolder.getViewById(R.id.post_endrelytime);
                    tvendtime.setText(post_tbl.getEndrely_time() + "");
                    TextView tvclick = viewHolder.getViewById(R.id.post_click);
                    tvclick.setText(post_tbl.getPost_click() + "");
                    TextView tvname = viewHolder.getViewById(R.id.post_name);
                    tvname.setText(post_tbl.getPost_name());
                    TextView tvtext = viewHolder.getViewById(R.id.post_text);
                    tvtext.setText(post_tbl.getPost_text());
                    TextView tvusername=viewHolder.getViewById(R.id.user_name);
                    tvusername.setText(post_tbl.getUsershow_tbl().getUsershow_name());
                    ImageView ivuserimage=viewHolder.getViewById(R.id.user_image);
                }
            };
            myXListView.setAdapter(teachAdapter);
            myXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//item点击事件
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    Intent intent=new Intent(getActivity(), ComunityPost_details_Activity.class);
                    Post_tbl post_tbl=dataList.get(position-1);
                    int post_id=post_tbl.getPost_id();
                    intent.putExtra("post_id",post_id);;
                    getActivity().startActivity(intent);

                }
            });
    }

    public void loadData() {//加载//获取数据
        for (int i = index; i < index + MAXVALUE; i++) {
            if(dataList.size()<post_tblsteach.size()) {
                if(i==post_tblsteach.size()) {
                    break;
                }
                dataList.add(post_tblsteach.get(i));
            }
        }
    }

    public void loadData1() {//刷新//获取数据
        for (int i = index; i < index + MAXVALUE; i++) {
            if(dataList.size()<post_tblsteach.size()) {
                dataList.add(post_tblsteach.get(i));
            }
        }
    }

    @Override
    public void setRefreshing() {//刷新
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                index = 0;
                dataList.clear();
                if (post_tblsteach.size()>0) {
                    loadData1();
                }
                setListAdapter();
                myXListView.setRefreshFinished();
            }
        }, 2000);
    }

    @Override
    public void setLoading() {//加载
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                index = index + MAXVALUE;
                if(post_tblsteach.size()>0){
                    loadData();
                }
                teachAdapter.notifyDataSetChanged();//notifyDataSetChanged()可以在修改适配器绑定的数组后，不用重新刷新Activity
                myXListView.setLoadFinished();
            }
        }, 2000);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}


