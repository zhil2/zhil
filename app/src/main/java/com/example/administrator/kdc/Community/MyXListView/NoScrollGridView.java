package com.example.administrator.kdc.Community.MyXListView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class NoScrollGridView extends GridView{

        public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}

	public NoScrollGridView(Context context) {
		super(context);

	}

		public NoScrollGridView(Context context, AttributeSet attrs) {
                super(context, attrs);
        }

        /**
         * 设置不滚动
         */
//        public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
//        {
//                int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                                MeasureSpec.AT_MOST);
//                super.onMeasure(widthMeasureSpec, expandSpec);
//
//        }
        @Override

        public boolean dispatchTouchEvent(MotionEvent ev) {

            if (ev.getAction() == MotionEvent.ACTION_MOVE) {

                return true;  //禁止GridView滑动

            }
            return super.dispatchTouchEvent(ev);
        }

    /**
     * 其中onMeasure函数决定了组件显示的高度与宽度；
     * MeasureSpec.makeMeasureSpec函数中第一个参数指布局空间的大小，第二个参数是布局模式
     * MeasureSpec.AT_MOST的意思就是子控件需要多大的控件就扩展到多大的空间
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
} 