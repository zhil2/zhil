package com.example.administrator.kdc.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/10/28.
 */
public class MyBroadcastReceive extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"接收推送",Toast.LENGTH_SHORT).show();
    }
}
