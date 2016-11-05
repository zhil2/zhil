package com.example.administrator.kdc.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.administrator.kdc.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class NewFriendActivity extends AppCompatActivity {

    @InjectView(R.id.query)
    EditText query;
    @InjectView(R.id.search_clear)
    ImageButton searchClear;
    @InjectView(R.id.tv_search)
    TextView tvSearch;
    @InjectView(R.id.btn_near)
    Button btnNear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);
        ButterKnife.inject(this);
    }



    @OnClick(R.id.btn_near)
    public void onClick() {
        Intent intent=new Intent(NewFriendActivity.this,one.class);
        startActivity(intent);
    }
}
