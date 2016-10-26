package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.R;
import com.example.administrator.kdc.framet.BaseFragment;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.example.administrator.kdc.vo.VC_tbl;
import com.example.administrator.kdc.vo.Venues_tbl;
import com.example.administrator.kdc.vo.Venuesshow_tbl;
import com.example.administrator.kdc.vo.Venuestime_tbl;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class VenuesshowActivity extends AppCompatActivity {
    List<Venuesshow_tbl> venuesshowList = new ArrayList<Venuesshow_tbl>();


    String url2;
    ImageLoader myImageLoader;


    int user_id;
    @InjectView(R.id.iv_portrait)
    ImageView ivPortrait;
    @InjectView(R.id.tv_user)
    TextView tvUser;
    @InjectView(R.id.tv_order)
    TextView tvOrder;
    @InjectView(R.id.ib_collection)
    ImageButton ibCollection;
    @InjectView(R.id.tv_muster)
    TextView tvMuster;
    @InjectView(R.id.textView9)
    TextView textView9;
    @InjectView(R.id.tv_number)
    TextView tvNumber;
    @InjectView(R.id.tv_current)
    TextView tvCurrent;
    @InjectView(R.id.tv_address)
    TextView tvAddress;
    @InjectView(R.id.tv_yes1)
    TextView tvYes1;
    @InjectView(R.id.tv_no)
    TextView tvNo;
    @InjectView(R.id.tv_type)
    TextView tvType;
    @InjectView(R.id.tv_price)
    TextView tvPrice;
    @InjectView(R.id.tv_sports)
    TextView tvSports;
    @InjectView(R.id.tv_lookmuster)
    TextView tvLookmuster;
    @InjectView(R.id.textView8)
    TextView textView8;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.b_evaluation)
    Button bEvaluation;
    @InjectView(R.id.b_friends)
    Button bFriends;
    @InjectView(R.id.textView19)
    TextView textView19;
    @InjectView(R.id.textView20)
    TextView textView20;
    Venues_tbl venues_tbl;
    VC_tbl vc_tbl;
    int good, no, yn, sc;
    int flag = 0,flag2=0;
    @InjectView(R.id.im_yes)
    ImageButton imYes;
    @InjectView(R.id.im_no)
    ImageButton imNo;

    Usershow_tbl usershow_tbl=null;
    Venuestime_tbl  venuestime_tbl=null;


    List<BaseFragment> fragmentList = new ArrayList<BaseFragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venuesshow);
        ButterKnife.inject(this);

        Intent intent = getIntent();

        user_id = ((MyApplication)getApplication()).getUsershow().getUser_tbl().getUser_id();

        vc_tbl = (VC_tbl) getIntent().getParcelableExtra("vc_tbl");
        sc = intent.getIntExtra("sc", -1);
        good = intent.getIntExtra("yes", -1);
        no = intent.getIntExtra("no", -1);
        yn = intent.getIntExtra("yn", -1);

        venues_tbl = vc_tbl.getVenues_tbl();



        RequestParams params = new RequestParams(NetUtil.url +"UsershowServlet");
        params.addBodyParameter("user_id",venues_tbl.getVenuesshow_tbl().getUser_id()+"" );

        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                Gson gson = new Gson();
                usershow_tbl =gson.fromJson(result,Usershow_tbl.class );


                tvUser.setText("发布者:" + usershow_tbl.getUsershow_name() + "");


                String b = "";



                switch (venues_tbl.getVenues_type()) {
                    case 1:
                        b = "支持预约的付费场地";
                        tvCurrent.setText(" 容量：" + venues_tbl.getVenues_current() + "/" + venues_tbl.getVenues_ceiling());
                        tvPrice.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price() + "￥每人/每小时");

                        break;
                    case 2:
                        b = "不支持预约的付费场地";
                        tvCurrent.setText(" 容量："  + venues_tbl.getVenues_ceiling());
                        tvPrice.setText(venues_tbl.getVenuesshow_tbl().getVenuesshow_price() + "￥每人/每小时");
                        tvOrder.setText("无法预约");
                        break;
                    case 3:
                        b = "免费的野场地";
                        tvCurrent.setText(" 容量："+ venues_tbl.getVenues_ceiling());
                        tvPrice.setText( "免费");
                        tvOrder.setText("无法预约");
                        break;
                }

                if(user_id==usershow_tbl.getUser_tbl().getUser_id()){//自己发布的场馆
                    tvOrder.setText("场馆管理");
                }

        //        Log.d("vgdhrtyyrj","     usershow_tbl.getUsershow_name()"+usershow_tbl.getUsershow_name());

                textView9.setText("" + venues_tbl.getVenues_name());

                tvType.setText("" + b);


            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {
            }
        });


        RequestParams params2 = new RequestParams(NetUtil.url +"VenuestimeServlet");
        params2.addBodyParameter("venues_id",venues_tbl.getVenues_id()+"" );

        x.http().post(params2, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                Log.d("dfghdfnxcv","result"+result);

                Gson gson = new Gson();
                venuestime_tbl =gson.fromJson(result,Venuestime_tbl.class );

                textView20.setText("开馆时间："+venuestime_tbl.getVenuestime_kg()+"~"+venuestime_tbl.getVenuestime_bg());

                Log.d("dfghdfnxcv","venuestime_tbl.getVenuestime_kg()"+venuestime_tbl.getVenuestime_kg());

            }
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }
            @Override
            public void onCancelled(CancelledException cex) {

            }
            @Override
            public void onFinished() {
            }
        });



        goodorno();

        url2 = venues_tbl.getVenuesshow_tbl().getVenuesshow_portrait();
        myImageLoader = new ImageLoader(VenuesshowActivity.this);
        myImageLoader.showImageByUrl(url2, ivPortrait);
        tvNumber.setText(" 联系电话：" + venues_tbl.getVenuesshow_tbl().getVenuesshow_number());

        myImageLoader.showImageByUrl(url2, imageView4);
        tvAddress.setText(" 地址:" + venues_tbl.getAddress_tbl().getAddress_city() + "" + venues_tbl.getAddress_tbl().getAddress_county() + "" + venues_tbl.getAddress_tbl().getAddress_town() + "" + venues_tbl.getAddress_tbl().getAddress_show());



        if (sc != 0) {
            ibCollection.setBackgroundResource(R.drawable.sc);
        } else {
            ibCollection.setBackgroundResource(R.drawable.sc2);
        }

        if (yn == 1) {
            imNo.setBackgroundResource(R.drawable.no);
            imYes.setBackgroundResource(R.drawable.good2);
        } else if (yn == 2) {
            imNo.setBackgroundResource(R.drawable.no2);
            imYes.setBackgroundResource(R.drawable.good);
        } else {
            imNo.setBackgroundResource(R.drawable.no);
            imYes.setBackgroundResource(R.drawable.good);
        }


    }


//    public void initView() {
//        fragmentList.add(new ItemFragment());//全部
//
//
//        vpPj.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
//            @Override
//            public Fragment getItem(int position) {
//                Log.d("jj","position"+position);
//                return fragmentList.get(position);
//            }
//            @Override
//            public int getCount() {
//                vpPj.getCurrentItem();
//
//                return fragmentList.size();
//            }
//        });
//    }





    @OnClick({R.id.tv_order, R.id.tv_muster, R.id.im_yes, R.id.im_no, R.id.ib_collection, R.id.b_evaluation, R.id.b_friends, R.id.tv_lookmuster})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_order:

                if(tvOrder.getText().equals("我要预约")) {
                    Intent intent = new Intent(VenuesshowActivity.this, OrderActivity.class);
                    intent.putExtra("venues_tbl", venues_tbl);
                    startActivity(intent);
                }else if(tvOrder.getText().equals("场馆管理")){

                    Intent intent = new Intent(VenuesshowActivity.this, ManagementActivity.class);
                    intent.putExtra("venues_tbl", venues_tbl);
                    intent.putExtra("venuestime_tbl", venuestime_tbl);
                    startActivityForResult(intent,1);

                }

                break;
            case R.id.tv_muster:
                Intent intent2 = new Intent(VenuesshowActivity.this, MusterActivity.class);

                intent2.putExtra("venues_tbl", venues_tbl);//发送数据
                startActivity(intent2);
                break;

            case R.id.im_yes:
                if (flag == 1) {
                    flag = 0;
                    good--;
                } else if (flag == 0) {
                    flag = 1;
                    good++;
                } else {
                    flag = 1;
                    good++;
                    no--;
                }
                goodorno();
                evaluation(flag);
                break;
            case R.id.im_no:
                if (flag == 2) {
                    flag = 0;
                    no--;
                } else if (flag == 0) {
                    flag = 2;
                    no++;
                } else {
                    flag = 2;
                    no++;
                    good--;
                }
                goodorno();
                evaluation(flag);
                break;

            case R.id.ib_collection:
                sc(user_id, vc_tbl.getVenues_tbl().getVenues_id(), 0, view);
                break;
            case R.id.b_evaluation:
//                flag2=0;

                Intent intent4 = new Intent(VenuesshowActivity.this, EvaluationActivity.class);
                intent4.putExtra("venues_id", venues_tbl.getVenues_id());
                startActivity(intent4);
            //    Log.d("cbfdsfg","show    venues_id "+ venues_tbl.getVenues_id() );

                break;
            case R.id.b_friends:
                Intent intent1 = new Intent(VenuesshowActivity.this, VenuessfriendActivity.class);
                intent1.putExtra("user_id", user_id);
                startActivity(intent1);
                break;
            case R.id.tv_lookmuster:
                Intent intent3 = new Intent(VenuesshowActivity.this, MusterlistActivity.class);
                intent3.putExtra("venues_id", venues_tbl.getVenues_id() + "");
                Log.d("agfhjyir", "show  venues_id" + venues_tbl.getVenues_id());
                startActivity(intent3);
                break;
        }

//        vpPj.setOffscreenPageLimit(1);
//        vpPj.setCurrentItem(flag);
    }

    public void evaluation(int flag) {
        RequestParams params = new RequestParams(NetUtil.url + "EvaluationServlet");
        params.addBodyParameter("flag", flag + "");//post方法的传值
        params.addBodyParameter("user_id", user_id + "");
        params.addBodyParameter("type", 2 + "");
        params.addBodyParameter("Venues_id", venues_tbl.getVenues_id() + "");
        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
            }
        });

    }

    public void goodorno() {
        // Log.d("asdfad","good"+good+"  no"+no);
        if (flag == 0) {
            imYes.setBackgroundResource(R.drawable.good);
            imNo.setBackgroundResource(R.drawable.no);
        } else if (flag == 1) {
            imYes.setBackgroundResource(R.drawable.good2);
            imNo.setBackgroundResource(R.drawable.no);
        } else {
            imYes.setBackgroundResource(R.drawable.good);
            imNo.setBackgroundResource(R.drawable.no2);
        }
        tvYes1.setText("" + good);
        textView19.setText("" + no);
    }


    public void sc(int user_id, int collection_object, int flag, final View v) {

        RequestParams params = new RequestParams(NetUtil.url + "CollectionServlet");
        params.addBodyParameter("user_id", user_id + "");//post方法的传值
        params.addBodyParameter("collection_type", 1 + "");
        params.addBodyParameter("collection_object", collection_object + "");
        params.addBodyParameter("flag", flag + "");

        x.http().post(params, new Callback.CommonCallback<String>() {//post的方式网络通讯
            @Override
            public void onSuccess(String result) {

                if (result.equals("收藏成功")) {
                    v.setBackgroundResource(R.drawable.sc);
                } else {
                    v.setBackgroundResource(R.drawable.sc2);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("BBBBB", "u3  get no");

            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    protected   void onActivityResult(int requestCode,int resultCode,Intent date){
        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){

                    String venues_name=date.getStringExtra("venues_name");
                    String venues_ceiling=date.getStringExtra("venues_ceiling");
                    String venues_current=date.getStringExtra("venues_current");
                    String venues_kg=date.getStringExtra("venues_kg");
                    String venues_bg=date.getStringExtra("venues_bg");
                    String venues_type=date.getStringExtra("venues_type");
                    String venuesshow_price=date.getStringExtra("venuesshow_price");
                    String venuesshow_number=date.getStringExtra("venuesshow_number");

                    tvPrice.setText("开放时间："+venues_kg+"~"+venues_bg);

                    Log.d("dfghdfnxcv","ve    venues_name"+venues_name);

                    if (venues_type.equals("支持预约的付费场地")){
                        tvType.setText("支持预约的付费场地");
                        tvCurrent.setText(" 容量：" + venues_current + "/" + venues_ceiling);
                        tvPrice.setText(venuesshow_price + "￥每人/每小时");
                    }else if(venues_type.equals("不支持预约的付费场地")){
                        tvType.setText("不支持预约的付费场地");
                        tvCurrent.setText(" 容量："  + venues_ceiling);
                        tvPrice.setText(venuesshow_price + "￥每人/每小时");

                    }else{
                        tvType.setText("免费的野场地");
                        tvCurrent.setText(" 容量："+ venues_ceiling);
                        tvPrice.setText( "免费");

                    }
                    textView9.setText(""+venues_name);
                    tvNumber.setText("联系电话"+venuesshow_number);
                }
        }
    }

            @Override
         public void onBackPressed(){
                Intent intent= new Intent();
                setResult(2,intent);
                finish();
        }


}
