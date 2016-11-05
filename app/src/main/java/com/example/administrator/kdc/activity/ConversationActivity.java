package com.example.administrator.kdc.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.kdc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.rong.imkit.RongIM;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;
import io.rong.message.ContactNotificationMessage;

/**
 * Created by Allen on 2016/10/28.
 */
public class ConversationActivity extends FragmentActivity {


    @InjectView(R.id.btn_back)
    ImageButton btnBack;
    @InjectView(R.id.tv_title)
    TextView tvTitle;
    /**
     * 目标 Id
     */
    private String mTargetId;
    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        ButterKnife.inject(this);
        Log.i("chat", "onCreate2: " + "进来了 啊 ");
        tvTitle.setText(getIntent().getData().getQueryParameter("title"));


        //融云会话列表监听
        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String s) {
                Log.i("chat", "onConversationClick: "+"ccc");
                return false;

            }

            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String s) {
                Log.i("chat", "onConversationClick: "+"bbb");
                return false;
            }

            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation) {
                Log.i("chat", "onConversationClick: "+"aaa");
                return false;
            }

            @Override
            public boolean onConversationClick(Context context, View view, UIConversation uiConversation) {

                if(uiConversation.getMessageContent() instanceof ContactNotificationMessage)
                {
                    Log.i("chat", "onConversationClick: "+"1111");
                    ContactNotificationMessage message = (ContactNotificationMessage) uiConversation.getMessageContent();
                    Log.i("chat", "onConversationClick: "+"2222");
                    if(message.getOperation().equals("Request"))
                    {

                        //这里进行你自己的操作，我是启动了另一个Activity来处理这个消息
                        Log.i("chat", "onConversationClick: "+"3333");
                        Intent intent =new Intent(context,FriendRequestActivity.class);
                        Log.i("chat", "onConversationClick: "+message);

                        startActivity(intent);

                    }else if(message.getOperation().equals(ContactNotificationMessage.CONTACT_OPERATION_ACCEPT_RESPONSE))
                    {}
                    else if(message.getOperation().equals(ContactNotificationMessage.CONTACT_OPERATION_REJECT_RESPONSE))
                    {}


                    return true;
                }
                else
                {
                    return false;
                }

            }
        });


    }

}
