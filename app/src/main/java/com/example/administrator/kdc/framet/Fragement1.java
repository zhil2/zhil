package com.example.administrator.kdc.framet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.kdc.Community.CommunityMainLayout.CommunityMainHome;
import com.example.administrator.kdc.Community.Myfocus.MyMain2;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.CollectionActivity2;
import com.example.administrator.kdc.activity.MapActivity;
import com.example.administrator.kdc.activity.MusterActivity;
import com.example.administrator.kdc.activity.OrderActivity2;
import com.example.administrator.kdc.vo.Venues_tbl;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/9/18.
 */
public class Fragement1 extends Fragment {


    @InjectView(R.id.iv_b)
    ImageView ivB;
    @InjectView(R.id.iv_b3)
    ImageView ivB3;
    @InjectView(R.id.iv_b1)
    ImageView ivB1;
    @InjectView(R.id.iv_b2)
    ImageView ivB2;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.textView3)
    TextView textView3;
    @InjectView(R.id.textView5)
    TextView textView5;
    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.textView6)
    TextView textView6;
    @InjectView(R.id.imageView3)
    ImageView imageView3;
    @InjectView(R.id.textView7)
    TextView textView7;
    Bundle bundle;
    int user_id=-1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment1, null);
        bundle = getArguments();
       //
        if(bundle!=null){
            user_id= bundle.getInt("str");
            Log.e("BBBBB", "fragem" + " user "+user_id+" bundle "+bundle);
        }

        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.iv_b, R.id.iv_b3, R.id.iv_b1, R.id.iv_b2, R.id.imageView2, R.id.imageView3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_b:
                Intent intent1=new Intent(getActivity(), MusterActivity.class);
                Venues_tbl venues_tbl=new Venues_tbl();
                intent1.putExtra("venues_tbl",venues_tbl);
                startActivity(intent1);
                break;
            case R.id.iv_b3://跳转到社区主页
                Intent intent2=new Intent(getActivity(),CommunityMainHome.class);
                intent2.putExtra("user_id",user_id);
                startActivity(intent2);
                break;
            case R.id.iv_b1:
                Intent intent3=new Intent(getActivity(),CollectionActivity2.class);

                startActivity(intent3);
                break;
            case R.id.iv_b2:
                Intent intent4=new Intent(getActivity(), OrderActivity2.class);
                startActivity(intent4);
                break;
            case R.id.imageView2:
                Intent intent5=new Intent(getActivity(),MapActivity.class);

                startActivityForResult(intent5,1);

                break;
            case R.id.imageView3:

                Intent intent=new Intent(getActivity(),MyMain2.class);

                intent.putExtra("user_id",user_id);

                startActivity(intent);

                break;
        }
    }
}