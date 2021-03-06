package com.example.administrator.kdc.Community.MyCommunitymain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseFragment;
import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter;
import com.example.administrator.kdc.Community.AdapterViewHolder.ViewHolder;
import com.example.administrator.kdc.Community.CommunityPostDetails.ComunityPost_details_Activity;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
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
public class MyAllPost_Fragment extends BaseFragment {
    @InjectView(R.id.myXListView)
    public ListView myXListView;
    private List<Post_tbl> post_tblsactivity = new ArrayList<Post_tbl>();
    CommonAdapter<Post_tbl> allAdapter;
    public View v;
    ImageLoader myImageLoader;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("MyFragment", "onCreateView: GovFragment");
        if (v != null) {
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeView(v);
            }
            return v;
        }//判断view并且是否保存加载布局，否则重新生成
        v = inflater.inflate(R.layout.mymain_alllistview, null);
        ButterKnife.inject(this, v);
        initEvent();
        getData();
        return v;
    }

    @Override
    public void initView() {

    }
    @Override
    public void initEvent() {
        myXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//item点击事件
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ComunityPost_details_Activity.class);
                Post_tbl post_tbl = post_tblsactivity.get(position);
                Bundle bundle = new Bundle();
                bundle.putParcelable("post_tbl", post_tbl);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);

            }
        });
    }

    public void getData() {
        String url = URL.url + "Post_tbl_getPostByUserId_Servlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("post_user_id", MyMain.user_id+ "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Post_tbl>>() {
                        }.getType();
                        ArrayList<Post_tbl> post_tbls = gson.fromJson(result, type);//解析成List<Product>
                        post_tblsactivity.clear();
                        post_tblsactivity.addAll(post_tbls);
                        Log.i("xUtils_Activity", "成功2" + post_tblsactivity);
                        Log.i("xUtils_Activity", "onSuccess: get post_tblstrach" + result);
                        allAdapter = new CommonAdapter<Post_tbl>(getActivity(), post_tblsactivity, R.layout.community_post_textview) {
                            @Override
                            public void convert(ViewHolder viewHolder, Post_tbl post_tbl, int position) {
                                Log.i("activityAdapter", "convert: "+ 11111111);
                                TextView tvendtime = viewHolder.getViewById(R.id.post_endrelytime);
                                tvendtime.setText(post_tbl.getPost_time() + "");
                                Log.i("activityAdapter", "convert: "+ post_tbl.getPost_time());
                                TextView tvclick = viewHolder.getViewById(R.id.post_click);
                                tvclick.setText(post_tbl.getPost_click() + "");
                                TextView tvname = viewHolder.getViewById(R.id.post_name);
                                tvname.setText(post_tbl.getPost_name());
                                TextView tvtext = viewHolder.getViewById(R.id.post_text);
                                tvtext.setText(post_tbl.getPost_text());
                                ImageView ivuserimage=viewHolder.getViewById(R.id.user_image);
                                String imageurl=post_tbl.getUsershow_tbl().getUsershow_head();
                                myImageLoader = new ImageLoader(getActivity());
                                myImageLoader.showImageByUrl(imageurl, ivuserimage);//加载图片//显示图片
                            }
                        };
                        myXListView.setAdapter(allAdapter);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    }


