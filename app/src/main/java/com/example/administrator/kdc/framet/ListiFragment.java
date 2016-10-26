package com.example.administrator.kdc.framet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.example.administrator.kdc.Adapter.MyAdapter;
import com.example.administrator.kdc.Community.CommunityMainLayout.CommunityMainHome;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.NewFriendActivity;
import com.example.administrator.kdc.activity.UserInfoActivity;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Allen on 2016/10/22.
 */
public class ListiFragment extends BaseFragment {

    @InjectView(R.id.btn_friend)
    Button btnFriend;
    @InjectView(R.id.btn_sport)
    Button btnSport;
    @InjectView(R.id.btn_person)
    Button btnPerson;
    @InjectView(R.id.elv)
    ExpandableListView elv;

    Map<String, List<Usershow_tbl>> map1 = null;
    String type1;
    List<Usershow_tbl> childs = new ArrayList<Usershow_tbl>();
    List<String> types = new ArrayList<String>();
    List<Map<String, String>> groupData = new ArrayList<Map<String, String>>();
    List<List<Map<String, Object>>> childData = new ArrayList<List<Map<String, Object>>>();
    Map<String, Usershow_tbl> map2 = new HashMap<>();
    MyAdapter adapter;
    int user_id;

    int flag = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_listi, null);
//        getData();
        user_id = ((MyApplication) getActivity().getApplication()).getUser().getUser_id();
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void initView() {


    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //可见时执行的操作

            getData();
        } else {
            //不可见时执行的操作
        }
    }


    public void getData(){

        adapter=null;
        childs.clear();
        types.clear();
        groupData.clear();
        childData.clear();

        RequestParams requestParams = new RequestParams(NetUtil.url + "ListQueryServlet");
        requestParams.addParameter("userId", ((MyApplication) getActivity().getApplication()).getUser().getUser_id() + "");
        Log.i("ListiFragment", "onSuccess: "+ ((MyApplication) getActivity().getApplication()).getUser().getUser_id());
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                Log.i("ListiFragment", "onSuccess: "+"555555555");
                Type type = new TypeToken<Map<String, List<Usershow_tbl>>>() {}.getType();
                map1 = gson.fromJson(result,type);
                Log.i("ListiFragment", "onSuccess: "+result);
                Log.i("ListiFragment", "onSuccess: "+map1);
                Set<Map.Entry<String, List<Usershow_tbl>>> entries = map1.entrySet();
                for (Map.Entry<String, List<Usershow_tbl>> entry : entries) {

                    type1 = entry.getKey();
                    types.add(type1);
                    Log.i("ListiFragment", "onSuccess: " + type1 + "0000000000000");
                    childs = entry.getValue();
                    // 创建一级条目标题
                    Map<String, String> groupdata = new HashMap<String, String>();
                    groupdata.put("tv_group_name", type1);// group对应layout中的id：group
                    groupData.add(groupdata);
                    Log.i("ListiFragment", "onSuccess: "+groupdata);

                    types.size();

                    int f = flag;
                    flag++;
                    Log.i("ListiFragment", "ffffffffffonSuccess: " + f);

                    // 创建一级条目下的的二级条目
                    List<Map<String, Object>> child1 = new ArrayList<Map<String, Object>>();
                    for (int i = 0; i < childs.size(); i++) {
                        Log.i("aa", "onSuccess: " + childs.size());
                        Map<String, Object> childData1 = new HashMap<String, Object>();
                        childData1.put("iv_userimage", childs.get(i).getUsershow_head());

                        childData1.put("tv_username", childs.get(i).getUsershow_name());
                        child1.add(childData1);
                        Log.i("aa", "onSuccess: " + childData1);

                        int z = i;
                        Log.i("ffffffffffffffff", "zzzzzzzzzzzzzonSuccess: " + z);
                        map2.put(f + "," + z, childs.get(i));


                    }
                    childData.add(child1);
                }
                if (adapter==null) {
                    adapter = new MyAdapter(
                            getActivity(), groupData, R.layout.group_item,
                            new String[]{"tv_group_name"}, new int[]{R.id.tv_group_name}
                            , childData, R.layout.child_item, new String[]{"iv_userimage", "tv_username"},
                            new int[]{R.id.iv_userimage, R.id.tv_username});


//                elv.setAdapter(adapter);
                elv.setAdapter(adapter);
                    elv.setSelectedGroup(flag);
                    elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                            Intent intent=new Intent(getActivity(),UserInfoActivity.class);

                            Log.i("ListiFragment", "onChildClick11111111: "+groupPosition+"22222"+childPosition+"11111"+id);
                            Log.i("ListiFragment", "onChildClick: "+childs.get(groupPosition));
                            //传子item的用户id
                            String key=groupPosition+","+childPosition;
                            intent.putExtra("use",map2.get(key).getUsershow_id());//得到的两个子item一样<因为conview的复用，
                            // 导致第二个子item复用第一的，使第一个为空>
                            Log.i("ListiFragment", "onChildClick: "+map2.get(key).getUsershow_id()+"11111111");
                            startActivity(intent);

                            return true;
                        }
                    });
                Log.i("ListiFragment", "onSuccess: "+"成功");

                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i("ListiFragment", "onError: "+"失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                Log.i("ListiFragment", "onFinished: ");
            }

        });

    }

    @OnClick({R.id.btn_friend, R.id.btn_sport, R.id.btn_person})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_friend:
                Intent intent=new Intent(getActivity(),NewFriendActivity.class);

                startActivity(intent);
                break;
            case R.id.btn_sport:

                Intent intent2=new Intent(getActivity(),CommunityMainHome.class);
                intent2.putExtra("user_id",user_id);
                startActivity(intent2);

                break;
            case R.id.btn_person:
                break;
        }
    }
}
