package com.example.administrator.kdc.framet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.activity.VenuesshowActivity;
import com.example.administrator.kdc.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class ItemFragment extends BaseFragment {

    CommonAdapter<String> orderAdapter;

    List<String> venueslist = new ArrayList<String>();

    @InjectView(R.id.ev_pj)
    EditText evPj;
    @InjectView(R.id.b_pj)
    Button bPj;
    @InjectView(R.id.linearLayout5)
    LinearLayout linearLayout5;
    @InjectView(R.id.listView_a)
    ListView listViewA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_a, null);
        ButterKnife.inject(this, v);

        venueslist.add("1");
        venueslist.add("2");
        venueslist.add("3");

        getOrderData();


        return v;
    }



    public void getOrderData() {
        orderAdapter = null;


        if (orderAdapter == null) {
            // Log.i("OrderAllFragment", "onSuccess: orderAdapter==null;+"+fragAllordersListview);
            orderAdapter = new CommonAdapter<String>(getActivity(), venueslist, R.layout.list_a) {
                @Override
                public void convert(final ViewHolder viewHolder, final String item2, final int position) {

                    TextView venues_name = viewHolder.getViewById(R.id.tv_nr);

                    venues_name.setText(item2);

//                    TextView venues_name = viewHolder.getViewById(R.id.tv_name);
//                    TextView address_id = (TextView) viewHolder.getViewById(R.id.tv_add);
//                    TextView venues_type = (TextView) viewHolder.getViewById(R.id.tv_type);
//                    TextView venues_ceiling = (TextView) viewHolder.getViewById(R.id.tv_current);
//                    TextView venues_yes = (TextView) viewHolder.getViewById(R.id.tv_yes);
//                    TextView venues_no = (TextView) viewHolder.getViewById(R.id.tv_no);
//                    ImageView venues_image = (ImageView) viewHolder.getViewById(R.id.imageView);
//                    ImageView imageView13 = (ImageView) viewHolder.getViewById(R.id.imageView13);
//                    ImageView imageView12 = (ImageView) viewHolder.getViewById(R.id.imageView12);
//                    ImageView sc = (ImageView) viewHolder.getViewById(R.id.iv_sc);
//
//
//                    sc.setTag(position);//定义唯一位置标识
//
//                    Log.d("ewrwe", "    getTag()   " + getTag() + "     position" + position);
//
//
//                    sc.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(final View v) {
//
//
//                        }
//                    });

                }

            };

            //listview中显示的是所有的数据信息
            listViewA.setAdapter(orderAdapter);
            listViewA.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override//点击此view进行界面的跳转
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String upload = venueslist.get(position);
                    //   Log.d("BBBBB","url2 ven____________________"+upload.getVenuesshow_tbl().getVenuesshow_portrait());
                    Intent intent = new Intent(getActivity(), VenuesshowActivity.class);

                    startActivity(intent);
                }
            });
        } else {
            //  Log.i("OrderAllFragment", "onSuccess: notifyDataSetChanged:+"+orders+":"+fragAllordersListview);
            orderAdapter.notifyDataSetChanged();
        }
    }






    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick(R.id.b_pj)
    public void onClick() {
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
}
