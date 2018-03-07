package com.example.administrator.animatortest.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2018/3/7.
 */

public class SudokuView extends ViewGroup {
    private int width;
    private int height;
    private int childWidth;
    private int childHeight;

    public SudokuView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        childWidth = width / 3;
        childHeight = childWidth;


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int startLeft = 0;
        int startTop = 0;
        int maxHeight = 0;

        int left, right, top, bottom;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childrenView = getChildAt(i);
            if (childrenView.getVisibility() == GONE) {
                continue;
            }

            if (startLeft + childWidth > width) {
                maxHeight += childHeight;
                startTop = maxHeight;
                startLeft = 0;

                left = startLeft;
                top = startTop;
                right = left + childWidth;
                bottom = top + childHeight;

                childrenView.layout(left, top, right, bottom);
                startLeft = right;
            } else {
                left = startLeft;
                top = startTop;
                right = left + childWidth;
                bottom = top + childHeight;

                childrenView.layout(left, top, right, bottom);
                startLeft = right;

            }


        }


    }


}
