package com.example.administrator.kdc.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.kdc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.rong.imlib.model.Conversation;

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
//we
    }

}
