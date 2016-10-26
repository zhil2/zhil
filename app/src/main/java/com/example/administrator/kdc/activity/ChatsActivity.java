package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Content_tbl;
import com.example.administrator.kdc.vo.Usershow_tbl;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Allen on 2016/10/21.
 */
public class ChatsActivity extends AppCompatActivity {
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.btn_back)
    Button btnBack;
    @InjectView(R.id.btn_send)
    Button btnSend;
    @InjectView(R.id.et_content)
    EditText etContent;
    @InjectView(R.id.contentView)
    ListView contentView;
    @InjectView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @InjectView(R.id.btn_record)
    Button btnRecord;

    List<Content_tbl> contentLists = new ArrayList<Content_tbl>();
    CommonAdapter<Content_tbl> chatAdapet;
    Usershow_tbl userDetail=new Usershow_tbl();
    String url;
    ImageLoader myimageloader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_chat);
        ButterKnife.inject(this);
        initContent_tbl();//测试数据
        getData();//初始化数据
    }

    public void getData(){
        //跳转传值
        Intent intent=getIntent();
        userDetail=intent.getParcelableExtra("userDetail");
        Log.i("ChatActivity", "onCreate: "+userDetail);
        tvTitle.setText(userDetail.getUsershow_name());

        if(chatAdapet==null){
            chatAdapet=new CommonAdapter<Content_tbl>(this,contentLists,R.layout.content_item_chat) {


                @Override
                public void convert(ViewHolder viewHolder, Content_tbl content_tbl, int position) {
                    LinearLayout leftLayout = (LinearLayout)viewHolder.getViewById(R.id.left_layout);
                    LinearLayout rightLayout = (LinearLayout)viewHolder.getViewById(R.id.right_layout);
                    TextView leftContent = (TextView)viewHolder.getViewById(R.id.left_content);
                    TextView rightContent = (TextView)viewHolder.getViewById(R.id.right_content);
                    TextView tvTime = (TextView)viewHolder.getViewById(R.id.tv_time);
                    ImageButton friendHead=viewHolder.getViewById(R.id.btn_item_left);
                    ImageButton userHead=viewHolder.getViewById(R.id.btn_item_right);

                    //传用户头像
                    url = userDetail.getUsershow_head();
                    Log.i("cc", "convert: "+url);
                    myimageloader=new ImageLoader(contentView.getContext());
//                    myimageloader.showImageByUrl(url,friendHead);

                    if(content_tbl.getFriends_id() == Content_tbl.friends_id_friend_id) {
                        leftLayout.setVisibility(View.VISIBLE);
                        rightLayout.setVisibility(View.GONE);
                        leftContent.setText(content_tbl.getContent_text());
                    } else if(content_tbl.getFriends_id() == Content_tbl.friends_id_user_id) {
                        rightLayout.setVisibility(View.VISIBLE);
                        leftLayout.setVisibility(View.GONE);
                        rightContent.setText(content_tbl.getContent_text());
                    }
                }
            };contentView.setAdapter(chatAdapet);

        }else{
            chatAdapet.notifyDataSetChanged();
        }
    }
    //定义消息数据的方法
    public void initContent_tbl() {
        Content_tbl content1 = new Content_tbl(Content_tbl.friends_id_friend_id, "你好，我是回复者");
        contentLists.add(content1);
        Content_tbl content2 = new Content_tbl(Content_tbl.friends_id_user_id, "你好，我是发送者");
        contentLists.add(content2);
        Content_tbl content3 = new Content_tbl(Content_tbl.friends_id_friend_id, "很高兴见到你");
        contentLists.add(content3);
    }
    @OnClick({R.id.btn_back, R.id.btn_send, R.id.btn_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                break;
            case R.id.btn_send:
                String content = etContent.getText().toString();
                if (!"".equals(content)) {
                    Content_tbl msg = new Content_tbl(Content_tbl.friends_id_user_id, content);
                    contentLists.add(msg);
                    chatAdapet.notifyDataSetChanged();
                    contentView.setSelection(contentLists.size());
                    etContent.setText("");
                }
                //连接服务器
                RequestParams params = new RequestParams(NetUtil.url + "ChatServlet");
                params.addParameter("content_text", content);
                x.http().get(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        btnRecord.setText(result);
                        Log.i("XutilsActivity", "接受成功" + result);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Log.i("XutilsActivity", "接受错误");
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {
                        Log.i("XutilsActivity", "XutilsActivity: onFinished");
                    }
                });
                break;
            case R.id.btn_record:
                break;
        }
    }
}
