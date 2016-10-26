package com.example.administrator.kdc.Community.MyXListView;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.example.administrator.kdc.R;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class MyXListView extends ListView implements OnScrollListener {
    private MyHeaderView myHeaderView;
    private View header_content;
    private int header_height;
    private float lastY = -1;
    private  int firstVisibleItem;
    private  int totalItemCount;
    private  float ratio =1.8f;
    private MyFooterView myFooterView;
    private int footViewMaginBottom = 40;
    private  OnXListViewListener onXListViewListener;
    public MyXListView(Context context) {
        this(context,null);
    }
    public MyXListView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MyXListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
        //对XListView设置滑动监听事件
        this.setOnScrollListener(this);
    }


    /**
     * 初始化HeaderView
     */
    private void initHeaderView() {
        myHeaderView = new MyHeaderView(getContext());
        //添加headerView
        this.addHeaderView(myHeaderView);
        header_content = myHeaderView.findViewById(R.id.xlistview_header_content);
        //直接获取控件高获取不到
        //header_content.getHeight();
        /**
         * 给布局添加监听，当视图可见状态改变的时候，回调到onGlobalLayout
         */
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                header_height = header_content.getHeight();
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    /**
     * 初始化FooterView
     */
    private void initFooterView() {
        myFooterView = new MyFooterView(getContext());
        this.addFooterView(myFooterView);
    }

    /**
     * 触摸滑动
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (lastY == -1){
                    lastY = ev.getRawY();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //手指滑动的距离
                float gapY = ev.getRawY() - lastY;
                if(firstVisibleItem == 0 && (gapY > 0||myHeaderView.getVisibleHeight() >0)) {
                    updateHeaderHeight(gapY/ratio);
                }else if((getLastVisiblePosition() == totalItemCount - 1)&&(gapY<0||myFooterView.getMarginBottom()>0)){
                    updateFooterViewMaginBottom(gapY/ratio);
                }
                //将当前的y坐标赋值给上一次
                lastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_CANCEL:
                lastY = -1;
                if (firstVisibleItem == 0){
                    //如果当前手指抬起时，headerView的可见高度大于headerView的高度，正在刷新
                    if(myHeaderView.getVisibleHeight()>header_height){
                        myHeaderView.setState(myHeaderView.STATE_REFREASHING);
                        if (onXListViewListener!=null){
                            onXListViewListener.setRefreshing();
                        }

                    }
                    resetHeaderViewHeight();
                }else if(getLastVisiblePosition() == totalItemCount -1 ){
                    if(myFooterView.getMarginBottom()>footViewMaginBottom){
                        //设置正在加载的状态
                        myFooterView.setState(MyFooterView.STATE_LOADING);
                        if(onXListViewListener != null) {
                            onXListViewListener.setLoading();
                        }
                    }
                    resetFooterViewMarginBottom();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void resetFooterViewMarginBottom() {
        myFooterView.setMarginBottom(0);
    }

    private void updateFooterViewMaginBottom(float v) {
        //重新设置距离底部的高度
        myFooterView.setMarginBottom((int) (myFooterView.getMarginBottom() - v));
        if(myFooterView.getMarginBottom()>footViewMaginBottom){
            myFooterView.setState(MyFooterView.STATE_READY);
        }else{
            myFooterView.setState(MyFooterView.STATE_LOADING);
        }
    }

    /**
     * 重置可见高度
     */
    private void resetHeaderViewHeight() {
        if(firstVisibleItem == 0){
            if(myHeaderView.getVisibleHeight()<=0){
                return;
            }
            if(myHeaderView.getVisibleHeight()>header_height){
                myHeaderView.setVisibleHeight(header_height);
            }else{
                myHeaderView.setVisibleHeight(0);
            }
        }
    }

    /**
     * 更新头部高度
     * @param gapY
     */
    private void updateHeaderHeight(float gapY) {
        if (firstVisibleItem == 0){
            myHeaderView.setVisibleHeight((int) (myHeaderView.getVisibleHeight()+gapY));
            //当前头部可视的高度大于HaderView的高度时，状态为ready状态
            if(myHeaderView.getVisibleHeight()>header_height){
                myHeaderView.setState(MyHeaderView.STATE_READY);
            }else{
                myHeaderView.setState(MyHeaderView.STATE_NORMAL);
            }
        }
        //设置当前选中的条目索引
        setSelection(0);
    }




    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstVisibleItem = firstVisibleItem;
        this.totalItemCount = totalItemCount;
    }

    public void setOnXListViewListener(OnXListViewListener onXListViewListener){
        this.onXListViewListener =  onXListViewListener;
    }


    public interface  OnXListViewListener{
        public void setRefreshing();
        public void setLoading();
    }

    /**
     * 设置更新结束后的方法
     */
    public void setRefreshFinished(){
        resetHeaderViewHeight();
    }
    /**
     * 设置加载结束后的方法
     */
    public void setLoadFinished(){
        myFooterView.setState(MyFooterView.STATE_NORMAL);
    }
}
