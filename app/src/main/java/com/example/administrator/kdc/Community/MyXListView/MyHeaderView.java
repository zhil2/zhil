package com.example.administrator.kdc.Community.MyXListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.kdc.R;

import java.text.SimpleDateFormat;
import java.util.Date;



/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class MyHeaderView extends LinearLayout {
    View headerView;
    ImageView header_arrow;
    ProgressBar header_progressBar;//进度条
    TextView header_hint_textView;
    TextView header_time;
    RotateAnimation animation_up;
    RotateAnimation animation_down;
    //定义三种状态
    public static final int STATE_NORMAL = 1;//初始状态
    public static final int STATE_READY = 2;//准备刷新状态
    public static final int STATE_REFREASHING = 3;//松开刷新状态
    /*
    当前状态值
     */
    private int mstate = STATE_NORMAL;
    //xml
    public MyHeaderView(Context context) {
        this(context,null);
    }
    //xml
    public MyHeaderView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }
    //new
    public MyHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        headerView  = View
                .inflate(getContext(), R.layout.xlistview_header,null);
        //设置初始的布局参数
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,0);
        this.addView(headerView,params);
        //初始化控件
        header_arrow = (ImageView) headerView
                .findViewById(R.id.xlistview_header_arrow);
        header_progressBar = (ProgressBar) headerView
                .findViewById(R.id.xlistview_header_progressbar);
        header_hint_textView = (TextView) headerView
                .findViewById(R.id.xlistview_header_hint_textview);
        header_time = (TextView) headerView.findViewById(R.id.xlistview_header_time);
        //初始化向上旋转动画
        animation_up = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animation_up.setDuration(500);//设置旋转时间
        animation_up.setFillAfter(true);
        //初始化向下旋转动画
        animation_down = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        animation_down.setDuration(500);//设置旋转时间
        animation_down.setFillAfter(true);
    }
    //对外提供一个方法设置状态值
    public void setState(int state){
        //上一个状态是现在的状态则不做状态设置
        if(state == mstate){
            return;
        }
        if (state == STATE_REFREASHING){
            header_progressBar.setVisibility(View.VISIBLE);
            header_arrow.setVisibility(View.INVISIBLE);
            header_arrow.clearAnimation();//调用view中的clearAnimation()方法，即可消除view上正在运行的动画效果。
        }else {
            header_progressBar.setVisibility(View.INVISIBLE);
            header_arrow.setVisibility(View.VISIBLE);
        }
        switch (state){
            case STATE_NORMAL:
                if(mstate == STATE_REFREASHING){
                    //如果之前是正在刷新
                }else if(mstate == STATE_READY){
                    //如果之前是准备刷新
                    header_arrow.startAnimation(animation_down);
                }
                header_hint_textView.setText(R.string.xListView_header_hint_normal);
                break;
            case STATE_REFREASHING:
                header_hint_textView.setText(R.string.xListView_header_hint_loading);
                header_time.setText((new SimpleDateFormat("HH:mm:ss").format(new Date()))+"");
                break;
            case STATE_READY:
                header_arrow.startAnimation(animation_up);
                header_hint_textView.setText(R.string.xListView_header_hint_ready);
            default:
                break;
        }
        //状态置位
        mstate = state;
    }

    /**
     * 设置headerView的可见高度
     * @param height
     */
    public void setVisibleHeight(int height){
        //生成布局参数
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,height);
        headerView.setLayoutParams(layoutParams);
    }

    /**
     * 获取headerView的可见高度
     * @return
     */
    public int getVisibleHeight(){
        return headerView.getLayoutParams().height;
    }
}
