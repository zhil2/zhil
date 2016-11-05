package com.example.administrator.kdc.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.kdc.Adapter.CommonAdapter;
import com.example.administrator.kdc.R;
import com.example.administrator.kdc.utils.ImageLoader;
import com.example.administrator.kdc.utils.MyApplication;
import com.example.administrator.kdc.utils.NetUtil;
import com.example.administrator.kdc.utils.ViewHolder;
import com.example.administrator.kdc.vo.Usershow_tbl;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import io.rong.message.ContactNotificationMessage;

/**
 * Created by Allen on 2016/11/4.
 */
public class FriendRequestActivity extends AppCompatActivity {
    List<ContactNotificationMessage> contactNotificationMessages;
    List flags;
    Usershow_tbl usershow_tbl;
    CommonAdapter<ContactNotificationMessage> commonAdapter;
    ImageLoader myimageLoader;
    String url;
    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.newFriend)
    ListView newFriend;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendsrequest);
        ButterKnife.inject(this);

        Log.i("chat2", "onCreate: " + "11111111");
        initEvent();
        Log.i("chat2", "onCreate: " + "2222222222");
        newFriend.setAdapter(commonAdapter);
        Log.i("chat2", "initEvent: " + "33333");

    }

    void initEvent() {

        final MyApplication myapplication = (MyApplication) getApplication();
        contactNotificationMessages = myapplication.getContactNotificationMessages();
        flags = myapplication.getFlags();
        Log.i("chat2", "initEvent: " + contactNotificationMessages);
        if (commonAdapter == null) {
            commonAdapter = new CommonAdapter<ContactNotificationMessage>(FriendRequestActivity.this, contactNotificationMessages, R.layout.friendrequestitem) {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void convert(ViewHolder viewHolder, final ContactNotificationMessage contactNotificationMessage, final int position) {
                    Log.i("chat2", "111onSuccess: " + contactNotificationMessages + flags);
                    final ImageView headdImage = viewHolder.getViewById(R.id.requestfriendheadimg);
                    final TextView userNickName = viewHolder.getViewById(R.id.requestUserNickName);
                    final TextView requestMessage = viewHolder.getViewById(R.id.messaage_context);
                    final TextView isAgree = viewHolder.getViewById(R.id.isAgree);
                    RequestParams requestParams = new RequestParams(NetUtil.url + "QueryFriendInfo");
                    Log.i("chat2", "onSuccess: " + "33333333333333");
                    requestParams.addParameter("userId", contactNotificationMessage.getSourceUserId());
                    x.http().get(requestParams, new Callback.CommonCallback<String>() {
                        @Override
                        public void onSuccess(String result) {
                            Gson gson = new Gson();
                            usershow_tbl = gson.fromJson(result, new TypeToken<Usershow_tbl>() {
                            }.getType());
                            url = usershow_tbl.getUsershow_head() + "";
                            myimageLoader = new ImageLoader(FriendRequestActivity.this);
                            myimageLoader.showImageByUrl(url, headdImage);
                            userNickName.setText(usershow_tbl.getUsershow_name() + "");
                            Log.i("chat2", "onSuccess: " + userNickName);
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

                    requestMessage.setText(contactNotificationMessage.getMessage() + "");


                    if ((boolean) flags.get(position)) {
                        isAgree.setText("已同意");
                        isAgree.setTextColor(Color.GRAY);
                        isAgree.setBackground(null);

                    } else {
                        isAgree.setText("同意");
                        isAgree.setTextColor(Color.BLUE);
                        isAgree.setBackgroundResource(R.drawable.friendagreequest);
                    }
                    isAgree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ((boolean) flags.get(position)) {
                            } else {
                                RequestParams requestParms = new RequestParams(NetUtil.url + "AddFriendsAgreeServlet");
                                requestParms.addParameter("peopleId1", usershow_tbl.getUser_id());
                                requestParms.addParameter("peopleId2", myapplication.getUser().getUser_id());
                                x.http().get(requestParms, new Callback.CommonCallback<String>() {
                                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                                    @Override
                                    public void onSuccess(String result) {
                                        //通知好友列表跟新数据及页面
                                        //访问服务器
                                        isAgree.setText("已同意");
                                        isAgree.setTextColor(Color.GRAY);
                                        isAgree.setBackground(null);
                                        flags.set(position, true);
                                        MyApplication myapplication = (MyApplication) getApplication();
                                        myapplication.setFlags(flags);
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
                        }
                    });

                }
            };
        }

    }

    @OnClick(R.id.btn_back)
    public void onClick() {
    }
}
