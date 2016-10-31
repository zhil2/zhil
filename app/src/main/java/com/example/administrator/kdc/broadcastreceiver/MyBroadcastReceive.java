package com.example.administrator.kdc.broadcastreceiver;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.administrator.kdc.activity.AllOrderActivity;
import com.example.administrator.kdc.activity.HomeActivity;
import com.example.administrator.kdc.activity.MusterlistActivity;
import com.example.administrator.kdc.utils.MyApplication;
import com.igexin.sdk.PushConsts;

import java.util.List;

/**
 * Created by Administrator on 2016/10/28.
 */
public class MyBroadcastReceive extends BroadcastReceiver{


    @Override
    public void onReceive(Context context, Intent intent) {
        String cid="";
        String taskid="";
        String messageid="";
     //   Log.d("12433","收到通知了。。。。。"+cid);
        Bundle bundle = intent.getExtras();
        Log.d("sdafxcz","打开召集333");
        switch (bundle.getInt(PushConsts.CMD_ACTION)) {
            case PushConsts.GET_CLIENTID:
                 cid = bundle.getString("clientid");
                // TODO:处理cid返回
                break;
            case PushConsts.GET_MSG_DATA:

                taskid = bundle.getString("taskid");
                messageid = bundle.getString("messageid");
                byte[] payload = bundle.getByteArray("payload");

              //  Log.d("sdafxcz","打开召集222"+payload);
                if (payload != null) {
                    String data = new String(payload);
               //     Log.d("sdafxcz","打开召集4444"+data);
                    // TODO:接收处理透传（payload）数据
                    if(data.equals("打开召集")){

                        Intent mainIntent = new Intent(context, HomeActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent intent2=new Intent(context,MusterlistActivity.class);
                        intent2.putExtra("venues_id",0+"");
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent[] intents = {mainIntent, intent2};
                        context.startActivities(intents);

                    }else if(data.equals("打开预约")){

                        Log.d("aaf","asdf");
                        Intent mainIntent = new Intent(context, HomeActivity.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent intent2=new Intent(context,AllOrderActivity.class);
                        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Intent[] intents = {mainIntent, intent2};
                        context.startActivities(intents);

                    }
                }
                break;
            default:
                break;
        }
        Log.d("12433","收到通知了。。。。。cid "+cid+"     taskid "+taskid+"    messageid "+messageid);

        //如果初始cid未赋值，则赋值
        if(!((MyApplication) context.getApplicationContext()).getCid().equals("初始cid")) {
            ((MyApplication) context.getApplicationContext()).setCid(cid);
        }





    }


    public static boolean isAppInForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                return appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND;
            }
        }
        return false;
    }

}