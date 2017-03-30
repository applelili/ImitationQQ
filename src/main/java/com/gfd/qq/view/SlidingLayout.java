package com.example.myqq;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

/**
 * Created by Administrator on 2017/3/29.
 */

public class SlidingLayout extends HorizontalScrollView{

    /** 左侧右边间距 */
    private float rightPadding;
    /** 左侧菜单的宽度 */
    private int leftWidth;
    private ViewGroup leftView;
    private ViewGroup contentView;
    private final Context context;
    private boolean isOpenMeun = true;
    private ImageView shadowView;

    public SlidingLayout(Context context) {
        this(context,null);
    }

    public SlidingLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlidingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        //获取自定义的属性
        TypedArray typedArray=context.obtainStyledAttributes(attrs,R.styleable.SlidingLayout);
        rightPadding=typedArray.getDimension(R.styleable.SlidingLayout_rightPadding,80);
        //计算左侧菜单的宽度
        leftWidth = (int) (getScreenWidth() - rightPadding + 0.5f);
    }

    //获取屏幕的宽度
    private float getScreenWidth() {
        return getResources().getDisplayMetrics().widthPixels;
    }

    @Override    /** 布局解析完毕的时候 */
    protected void onFinishInflate() {
        super.onFinishInflate();
        ViewGroup container= (ViewGroup) getChildAt(0);
        if(container.getChildCount() > 2){
            throw  new IllegalStateException("SlidingLayout中只能放两个子View");
        }
        //获取左侧菜单view
        leftView = (ViewGroup) container.getChildAt(0);
        //获取主布局的Viwe
        contentView = (ViewGroup) container.getChildAt(1);
        //设置子view 的宽度
        leftView.getLayoutParams().width = leftWidth;
        contentView.getLayoutParams().width = (int) getScreenWidth();

        //移除父布局
        container.removeView(contentView);
        FrameLayout frameLayout=new FrameLayout(context);
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.addView(contentView);
        //添加阴影
        shadowView = new ImageView(context);
        shadowView.setBackgroundColor(Color.parseColor("#99000000"));
        frameLayout.addView(shadowView);
        container.addView(frameLayout);
    }

    /**
     * 该方法在滑动的时候会不断的调用
     * @param l : left
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float x=l*0.8f;//偏移量
        leftView.setTranslationX(x);//平移
        float color = 1 - l * 1.0f / leftWidth;
      shadowView.setAlpha(color);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP://手指抬起的时候判断是否关闭
                int currentX = getScrollX();
                if (isOpenMeun) {
                    if (currentX >= leftWidth / 2) {
                        closeMeun();
                    } else {
                        openMeun();
                    }
                    //点击关闭
                    float x = ev.getX();
                    if (x > leftWidth) {
                        closeMeun();
                    }
                    return true;
                } else {//关闭状态
                    if (currentX < leftWidth / 2) {
                        openMeun();
                    } else {
                        closeMeun();

                    }
                    return true;
                }

        }
        return super.onTouchEvent(ev);

    }
    /** 关闭菜单 */
    public void closeMeun(){
        isOpenMeun = false;
        smoothScrollTo(leftWidth,0);// 250ms
    }

    /** 打开菜单 */
    public void openMeun(){
        isOpenMeun = true;
        smoothScrollTo(0,0);
    }
}
