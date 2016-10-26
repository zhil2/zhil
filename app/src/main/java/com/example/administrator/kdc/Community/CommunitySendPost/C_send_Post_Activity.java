package com.example.administrator.kdc.Community.CommunitySendPost;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.kdc.Community.CommunityGetPicture.Bimp;
import com.example.administrator.kdc.Community.CommunityGetPicture.FileUtils;
import com.example.administrator.kdc.Community.CommunityGetPicture.PhotoActivity;
import com.example.administrator.kdc.Community.CommunityGetPicture.TestPicActivity;
import com.example.administrator.kdc.Community.CommunityLayout.CommunityHome;
import com.example.administrator.kdc.Community.MyXListView.NoScrollGridView;
import com.example.administrator.kdc.Community.ServletURL.URL;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.MyApplication;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class C_send_Post_Activity extends AppCompatActivity {

    @InjectView(R.id.return_commuinty)
    ImageView returnCommuinty;
    @InjectView(R.id.tv_comunityname)
    TextView tvComunityname;
    @InjectView(R.id.et_community_post_title)
    EditText etCommunityPostTitle;
    @InjectView(R.id.et_community_sendpost)
    EditText etCommunitySendpost;
    @InjectView(R.id.sendpost_tocommunity)
    public Button sendpostTocommunity;
    private RadioButton btnPosttypeAll;
    private RadioButton btnPosttypeActivity;
    private RadioButton btnPosttypeShare;
    private RadioButton btnPosttypeTeach;
    private RadioGroup radioGrouptype;
    private NoScrollGridView noScrollgridview;//图片展示
    private GridAdapter adapter;//gridview适配器
    public static int posttype;
    //public final int user_id= ((MyApplication) C_send_Post_Activity.this.getApplication()).getUser().getUser_id();
    public int community_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_send__post_);
        ButterKnife.inject(this);
        Intent intent=getIntent();
        community_id=intent.getIntExtra("community_id",0);
        btnPosttypeAll = (RadioButton) findViewById(R.id.btn_posttype_all);
        btnPosttypeActivity = (RadioButton) findViewById(R.id.btn_posttype_activity);
        btnPosttypeShare = (RadioButton) findViewById(R.id.btn_posttype_share);
        btnPosttypeTeach = (RadioButton) findViewById(R.id.btn_posttype_teach);
        radioGrouptype = (RadioGroup) findViewById(R.id.radioGrouptype);
        radioGrouptype.setOnCheckedChangeListener(rdgcc);
        Init();
        Log.i("community_id1111", "onClick: "+ CommunityHome.community_id+"");
    }

    private RadioGroup.OnCheckedChangeListener rdgcc = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // TODO Auto-generated method stub
            if (checkedId == btnPosttypeAll.getId()) {
                posttype = 0;
            } else if (checkedId == btnPosttypeActivity.getId()) {
                posttype = 1;
            } else if (checkedId == btnPosttypeShare.getId()) {
                posttype = 2;
            } else if (checkedId == btnPosttypeTeach.getId()) {
                posttype = 3;
            }
        }
    };
    public void Init() {//适配器，点击添加图片
        noScrollgridview = (NoScrollGridView) findViewById(R.id.noScrollgridview);//不能滚动
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new GridAdapter(this);
        adapter.update();//适配器更新
        noScrollgridview.setAdapter(adapter);//noScrollgridview点击事件
        noScrollgridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                if (arg2 == Bimp.bmp.size()) {
                    new PopupWindows(C_send_Post_Activity.this, noScrollgridview);
                } else {
                    Intent intent = new Intent(C_send_Post_Activity.this,
                            PhotoActivity.class);
                    intent.putExtra("ID", arg2);
                    startActivity(intent);//图片删除
                }
            }
        });
    }
    @SuppressLint("HandlerLeak")
    public class GridAdapter extends BaseAdapter {//grid适配器构造（图片）
        private LayoutInflater inflater; // 视图容器
        private int selectedPosition = -1;// 选中的位置
        private boolean shape;

        public boolean isShape() {//图片形状
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public GridAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        public void update() {//图片更新
            loading();
        }//更新

        public int getCount() {
            return (Bimp.bmp.size() + 1);
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }
        /**
         * ListView Item设置
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int coord = position;
            ViewHolder holder = null;
            if (convertView == null) {

                convertView = inflater.inflate(R.layout.item_published_grida,
                        parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);//图片要显示的控件
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == Bimp.bmp.size()) {//添加图片按钮
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                if (position == 9) {
                    holder.image.setVisibility(View.GONE);//gridview中显示的图片=9时则图片按钮显示
                }
            } else {
                holder.image.setImageBitmap(Bimp.bmp.get(position));
            }

            return convertView;
        }

        public class ViewHolder {//图片显示
            public ImageView image;
        }

        Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter.notifyDataSetChanged();
                        break;
                }
                super.handleMessage(msg);
            }
        };

        public void loading() {//加载图片
            new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        if (Bimp.max == Bimp.drr.size()) {
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                            break;
                        } else {
                            try {
                                String path = Bimp.drr.get(Bimp.max);
                                System.out.println(path);
                                Bitmap bm = Bimp.revitionImageSize(path);
                                Bimp.bmp.add(bm);
                                String newStr = path.substring(
                                        path.lastIndexOf("/") + 1,
                                        path.lastIndexOf("."));
                                FileUtils.saveBitmap(bm, "" + newStr);
                                Bimp.max += 1;
                                Message message = new Message();
                                message.what = 1;
                                handler.sendMessage(message);
                            } catch (IOException e) {

                                e.printStackTrace();
                            }
                        }
                    }
                }
            }).start();
        }
    }//适配器


    public String getString(String s) {
        String path = null;
        if (s == null)
            return "";
        for (int i = s.length() - 1; i > 0; i++) {
            s.charAt(i);
        }
        return path;
    }

    protected void onRestart() {
        adapter.update();
        super.onRestart();
    }

    public class PopupWindows extends PopupWindow {//PopupWindow窗口显示图片获取方式

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);//拍照
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);//相册
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);//取消
            bt1.setOnClickListener(new View.OnClickListener() {//拍照
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {//相册
                public void onClick(View v) {
                    Intent intent = new Intent(C_send_Post_Activity.this,
                            TestPicActivity.class);
                    startActivity(intent);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {//取消
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    private static final int TAKE_PICTURE = 0x000000;
    private String path = "";

    public void photo() {//检查sd卡，并拍照//拍照

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCameraIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        StringBuffer sDir = new StringBuffer();
        if (hasSDcard()) {
            sDir.append(Environment.getExternalStorageDirectory() + "/MyPicture/");
        } else {
            String dataPath = Environment.getRootDirectory().getPath();
            sDir.append(dataPath + "/MyPicture/");
        }

        File fileDir = new File(sDir.toString());
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File file = new File(fileDir, String.valueOf(System.currentTimeMillis()) + ".jpg");//拍照图片命名

        path = file.getPath();
        Uri imageUri = Uri.fromFile(file);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);

    }

    public static boolean hasSDcard() {//相机权限
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
    //gridview中判断图片数目是否大于9，否则再添加
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (Bimp.drr.size() < 9 && resultCode == -1) {
                    Bimp.drr.add(path);
                }
                break;
        }
    }
    /**
     * 删除所有的图片
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            Bimp.bmp.clear();
            Bimp.drr.clear();
            Bimp.max = 0;
            FileUtils.deleteDir();

            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.return_commuinty, R.id.sendpost_tocommunity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_commuinty:
                finish();
                break;
            case R.id.sendpost_tocommunity://发送帖子
                List<String> list = new ArrayList<String>();//图片集，String对象
                for (int i = 0; i < Bimp.drr.size(); i++) {
                    String Str = Bimp.drr.get(i).substring(
                            Bimp.drr.get(i).lastIndexOf("/") + 1,
                            Bimp.drr.get(i).lastIndexOf("."));
                    list.add(FileUtils.SDPATH+Str+".JPEG");	//图片集的路径
                }
                Log.i("sendpost_tocommunity", "onClick: "+11111);
                // 高清的压缩图片全部就在  list 路径里面了
                // 高清的压缩过的 bmp 对象  都在 Bimp.bmp里面
                // 完成上传服务器后 .........删除路径

                //发送数据到服务器上数据库中的帖子表
                Gson gson=new Gson();
                String imagelist=gson.toJson(list);//转换为gson数据String类型
                Log.i("imagelist", "onClick: "+imagelist);
                String post_picture=imagelist;
                String post_name = etCommunityPostTitle.getText().toString();
                String post_text = etCommunitySendpost.getText().toString();
                if(post_name.equals("")){
                    Toast.makeText(getApplicationContext(), "标题不能为空！", Toast.LENGTH_SHORT).show();
                } else if (post_text.equals("")){
                    Toast.makeText(getApplicationContext(), "内容不能为空！", Toast.LENGTH_SHORT).show();
                }else if(!post_name.equals("")&&!post_text.equals("")){
//                    Date date1 = new Date();//获取当前时间
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String str = sdf.format(date1);//时间存储为字符串
//                    System.out.println("222  "+muster_time);
//                    Timestamp.valueOf(str);//转换时间字符串为Timestamp
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String post_time = sDateFormat.format(new Date());
                String endrely_time = post_time;
                Log.i("Post_tbl_Insert_Servlet", "onSuccess: get" + endrely_time);
                int post_type = posttype;
                MyApplication myApplication=(MyApplication) getApplication();
                Log.i("post_picture111111", "onClick1111: "+myApplication);
                RequestParams requestParams = new RequestParams(URL.url + "Post_tbl_Insert_Servlet");
                Log.i(" post_picture111111", "onClick:1111111111111 "+ 7777);
                requestParams.addQueryStringParameter("post_user_id", ((MyApplication) C_send_Post_Activity.this.getApplication()).getUser().getUser_id()+"");
                requestParams.addQueryStringParameter("post_name", post_name);
                requestParams.addQueryStringParameter("post_click", 0+"");
                requestParams.addQueryStringParameter("post_type", post_type+"");
                requestParams.addQueryStringParameter("community_id", community_id+"");
                Log.i("post_picture111111", "onClick: "+community_id);
                requestParams.addQueryStringParameter("post_picture", post_picture);
                requestParams.addQueryStringParameter("post_text", post_text);
                requestParams.addQueryStringParameter("post_time", post_time);
                requestParams.addQueryStringParameter("endrely_time", endrely_time);
                Log.i("post_picture111111", "onClick: "+9999);
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("Post_tbl_Insert_Servlet", "onSuccess: get" + result);
                        Toast.makeText(getApplicationContext(), "发帖成功", Toast.LENGTH_SHORT).show();
                        FileUtils.deleteDir();//图片上传后删除图片集路径
                        Intent intent1=new Intent();
                        intent1.putExtra("posttype",posttype);
                        setResult(RESULT_OK,intent1);
                        Log.i("onActivityResult", "onActivityResult:111222333 "+community_id+posttype);
                        finish();
                    }
                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("xUtils_Activity", "onError: get" + ex.getMessage());
                    }
                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
                //上传图片到服务器
                RequestParams requestParams2 = new RequestParams(URL.url + "UpImageServlet");
                requestParams2.setMultipart(true);
                    for(int i=0;i<list.size();i++) {
                    requestParams2.addQueryStringParameter("file", list.get(i));
                    }
                x.http().get(requestParams2, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.i("ModifyPersonInfo", "onSuccess: ");
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
                FileUtils.deleteDir();//图片上传后删除图片集路径
                }
                break;
                }

    }
}
