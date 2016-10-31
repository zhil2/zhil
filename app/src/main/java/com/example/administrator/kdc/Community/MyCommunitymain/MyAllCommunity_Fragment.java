package com.example.administrator.kdc.Community.MyCommunitymain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.kdc.Community.AdapterViewHolder.BaseFragment;
import com.example.administrator.kdc.Community.AdapterViewHolder.CommonAdapter;
import com.example.administrator.kdc.Community.AdapterViewHolder.ViewHolder;
import com.example.administrator.kdc.Community.CommunityLayout.CommunityHome;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.vo.Community_tbl;
import com.example.administrator.kdc.vo.Focus_tbl;
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
public class MyAllCommunity_Fragment extends BaseFragment {
    @InjectView(R.id.myXListView)
    ListView myXListView;
    private List<Focus_tbl> focus_tbls = new ArrayList<Focus_tbl>();
    public CommonAdapter<Focus_tbl> allAdapter;
    public View v;
   // public  int community_id2;
    //public  Community_tbl community_tbl2;//网络获得的社区

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
        getData();
        initEvent();
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void initView() {

    }
    public void initEvent() {
        myXListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Focus_tbl focus_tbl2 = focus_tbls.get(position);
                Intent intent2 = new Intent(getActivity(), CommunityHome.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("community_tbl", focus_tbl2.getCommunity_tbl());
                intent2.putExtras(bundle);
                getActivity().startActivity(intent2);
            }
        });
    }

    @Override
    public void getData() {
        String url = URL.url + "Focus_tbl_getAll_Servlet";
        RequestParams requestParams = new RequestParams(url);
        requestParams.addQueryStringParameter("focus_type", 2 + "");
        requestParams.addQueryStringParameter("user_id", MyMain.user_id + "");
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("xUtils_Activity", "成功" + result);//gson如何转集合
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<Focus_tbl>>() {
                        }.getType();
                        final ArrayList<Focus_tbl> focus_tbls1 = gson.fromJson(result, type);//解析成List<Product>
                        focus_tbls.clear();
                        focus_tbls.addAll(focus_tbls1);
                        Log.i("xUtils_Activity", "成功2" + focus_tbls);
                        Log.i("xUtils_Activity", "onSuccess: get Focus_tbl_getAll_Servlet" + result);
                        allAdapter = new CommonAdapter<Focus_tbl>(getActivity(), focus_tbls, R.layout.community_record_item) {
                            @Override
                            public void convert(final ViewHolder viewHolder, Focus_tbl focus_tbl, int position) {
                                Log.i("activityAdapter", "convert: " + 11111111);
                                TextView tvcommunityname = viewHolder.getViewById(R.id.record);
                                tvcommunityname.setText(focus_tbl.getCommunity_tbl().getCommunity_name());
                            }
                        };
                        myXListView.setAdapter(allAdapter);
                        initEvent();
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
}


