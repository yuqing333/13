package com.example.administrator.animatortest;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/3/5.
 */

public class CustomViewGroup extends ViewGroup {
    private int mItemMarginVertical = 0;
    private int mItemMarginHorizontal = 0;

    public CustomViewGroup(Context context) {
        this(context, null);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomViewGroup);
        mItemMarginVertical = (int) typedArray.getDimension(R.styleable.CustomViewGroup_tag_margin_vertical, mItemMarginVertical);
        mItemMarginHorizontal=(int)typedArray.getDimension(R.styleable.CustomViewGroup_tag_margin_horizontal,mItemMarginHorizontal);
        typedArray.recycle();


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        //宽高模式都为EXACTLY,不用测量
        if (modeHeight == MeasureSpec.EXACTLY && modeWidth == MeasureSpec.EXACTLY) {
            setMeasuredDimension(sizeWidth, sizeHeight);
            return;
        }

        int width = 0;
        int height = 0;

        int lineWidth = 0;
        int lineHeight = 0;

        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            //超过最大，换行
            if (lineWidth + childWidth > sizeWidth) {
                width = Math.max(lineWidth, childWidth);
                lineWidth = childWidth;
                height += lineHeight;
                lineHeight = childHeight;
            } else {
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }

            if (i == cCount - 1) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        setMeasuredDimension((modeWidth == MeasureSpec.EXACTLY) ? sizeWidth : width, (modeHeight == MeasureSpec.EXACTLY) ? sizeHeight : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int startLeft = getPaddingLeft();
        int startTop = getPaddingTop();

        int drawWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int lineWidth = 0;
        int lineBottom = 0;
        int left, top, right, bottom;

        int layoutLeft = startLeft;
        int layoutTop = startTop;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            if (childWidth + lp.leftMargin + lp.rightMargin + lineWidth > drawWidth) {
                layoutLeft = startLeft + lp.leftMargin;
                layoutTop = lineBottom + lp.topMargin;

                left = layoutLeft;
                top = layoutTop + lp.topMargin;
                right = left + childWidth;
                bottom = top + childHeight;
                childView.layout(left, top, right, bottom);

                lineBottom = bottom + lp.bottomMargin;
                layoutLeft = right + lp.rightMargin;
                lineWidth = childWidth + lp.leftMargin + lp.rightMargin;

            } else {
                left = layoutLeft + lp.leftMargin;
                top = layoutTop + lp.topMargin;
                right = left + childWidth;
                bottom = top + childHeight;
                childView.layout(left, top, right, bottom);

                lineBottom = Math.max(lineBottom, bottom + lp.bottomMargin);
                layoutLeft = right + lp.rightMargin;
                lineWidth = right + lp.rightMargin - startLeft;
            }


        }

    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }


}
