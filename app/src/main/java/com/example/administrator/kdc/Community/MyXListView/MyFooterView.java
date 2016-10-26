package com.example.administrator.kdc.Community.MyXListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.kdc.R;


/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class MyFooterView extends LinearLayout {
    private View footerView;
    private View footer_content;
    private TextView footer_hint_textView;
    private ProgressBar footer_progressbar;
    //定义三种状态
    public static final int STATE_NORMAL = 1;//初始状态
    public static final int STATE_READY = 2;//准备加载状态
    public static final int STATE_LOADING = 3;//正在加载状态

    private int mstate = STATE_NORMAL;

    public MyFooterView(Context context) {
        this(context,null);
    }

    public MyFooterView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyFooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView(){
        footerView = View.inflate(getContext(), R.layout.xlistview_footer,null);
        footer_content = footerView.findViewById(R.id.xlistview_footer_content);
        footer_hint_textView = (TextView) footerView.findViewById(R.id.xlistview_footer_hint_textview);
        footer_progressbar = (ProgressBar) footerView.findViewById(R.id.xlistview_footer_progressbar);

        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.addView(footerView,params);
    }
    //对外提供一个方法设置状态值
    public void setState(int state){
        if(state == mstate){
            return;
        }

        switch (state){
            case STATE_NORMAL:
                footer_hint_textView.setVisibility(View.VISIBLE);
                footer_progressbar.setVisibility(View.INVISIBLE);
                footer_hint_textView.setText(R.string.xListView_footer_hint_normal);
                break;

            case STATE_READY:
                footer_hint_textView.setVisibility(View.VISIBLE);
                footer_progressbar.setVisibility(View.INVISIBLE);
                footer_hint_textView.setText(R.string.xListView_footer_hint_ready);
                break;
            case STATE_LOADING:
                footer_hint_textView.setVisibility(View.INVISIBLE);
                footer_progressbar.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        //状态置位
        mstate = state;
    }

    /**
     * 设置当前footerView距离底部的高度
     * @param height
     */
    public void setMarginBottom(int height){
        if(height < 0){
            return;
        }
        //获取布局参数
        LayoutParams layoutParams = (LayoutParams) footer_content.getLayoutParams();
        //设置布局参数距离底部高度
        layoutParams.bottomMargin = height;
        //重新设置footer_content的布局参数
        footer_content.setLayoutParams(layoutParams);
    }

    /**
     * 获取当前footerView距离底部的高度
     * @return
     */
    public int getMarginBottom() {
        //获取布局参数
        LayoutParams layoutParams = (LayoutParams) footer_content.getLayoutParams();
        return  layoutParams.bottomMargin;
    }
}
