package com.example.administrator.kdc.Community.Myfocus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseFragment;
import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Post_tbl;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by kskjf on 2016/9/27.
 */
public class MyAllFriend_Fragment extends BaseFragment {
    @InjectView(R.id.myXListView)
    public ListView myXListView;
    private List<Post_tbl> post_tblsactivity = new ArrayList<Post_tbl>();
    CommonAdapter<Post_tbl> allAdapter;
    public View v;
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
        //initEvent();
        //getData();
        return v;
    }

    @Override
    public void initView() {

    }
    @Override
    public void initEvent() {
//        myXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {//item点击事件
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), ComunityPost_details_Activity.class);
//                Post_tbl post_tbl = post_tblsactivity.get(position);
//                Bundle bundle = new Bundle();
//                bundle.putParcelable("post_tbl", post_tbl);
//                intent.putExtras(bundle);
//                getActivity().startActivity(intent);
//
//            }
//        });
    }

    @Override
    public void getData() {

    }

    //    public void getData() {
//        String url = URL.url + "Post_tbl_getPostByUserId_Servlet";
//        RequestParams requestParams = new RequestParams(url);
//        requestParams.addQueryStringParameter("post_user_id", MyMain2.user_id+ "");
//        x.http().get(requestParams, new Callback.CommonCallback<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
//                        Gson gson = new Gson();
//                        Type type = new TypeToken<List<Post_tbl>>() {
//                        }.getType();
//                        ArrayList<Post_tbl> post_tbls = gson.fromJson(result, type);//解析成List<Product>
//                        post_tblsactivity.clear();
//                        post_tblsactivity.addAll(post_tbls);
//                        Log.i("xUtils_Activity", "成功2" + post_tblsactivity);
//                        Log.i("xUtils_Activity", "onSuccess: get post_tblstrach" + result);
//                        allAdapter = new CommonAdapter<Post_tbl>(getActivity(), post_tblsactivity, R.layout.community_post_textview) {
//                            @Override
//                            public void convert(ViewHolder viewHolder, Post_tbl post_tbl, int position) {
//                                Log.i("activityAdapter", "convert: "+ 11111111);
//                                TextView tvendtime = viewHolder.getViewById(R.id.post_endrelytime);
//                                tvendtime.setText(post_tbl.getPost_time() + "");
//                                Log.i("activityAdapter", "convert: "+ post_tbl.getPost_time());
//                                TextView tvclick = viewHolder.getViewById(R.id.post_click);
//                                tvclick.setText(post_tbl.getPost_click() + "");
//                                TextView tvname = viewHolder.getViewById(R.id.post_name);
//                                tvname.setText(post_tbl.getPost_name());
//                                TextView tvtext = viewHolder.getViewById(R.id.post_text);
//                                tvtext.setText(post_tbl.getPost_text());
//                            }
//                        };
//                        myXListView.setAdapter(allAdapter);
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                        Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
//                    }
//
//                    @Override
//                    public void onCancelled(CancelledException cex) {
//                        Log.i("xUtils_Activity", "onCancelled: get" + cex);
//                    }
//
//                    @Override
//                    public void onFinished() {
//                        Log.i("xUtils_Activity", "onFinished: get");
//                    }
//                }
//        );
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    }


