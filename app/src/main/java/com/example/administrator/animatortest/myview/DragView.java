package com.example.administrator.animatortest.myview;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by Administrator on 2018/3/7.
 */

public class DragView extends ViewGroup {

    private final static String TAG = DragView.class.getSimpleName();
    private Point mPoint;
    private int width;
    private int height;
    private int childWidth;
    private int childHeight;


    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPoint();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);

            childWidth = childView.getMeasuredWidth();
            childHeight = childView.getMeasuredHeight();

            childView.layout(mPoint.x, mPoint.y, mPoint.x + childWidth, mPoint.y + childHeight);

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int offSetX, offSetY;
        int x = (int) event.getX();
        int y = (int) event.getY();
        View childView;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                Log.i(TAG, "ViewGroup ACTION_DOWN");
                childView = findTopChildUnder(x, y);
                if (childView != null) {
                    Log.i(TAG, "Has View");
                    break;
                } else {
                    Log.i(TAG, "No View");
                    return false;
                }

            case MotionEvent.ACTION_MOVE:
                Log.i(TAG, "ViewGroup ACTION_MOVE");
                offSetX = x - mPoint.x;
                offSetY = y - mPoint.y;
                layout(getLeft() + offSetX, getTop() + offSetY, getRight() + offSetX, getBottom() + offSetY);
                mPoint.x = x;
                mPoint.y = y;

                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "ViewGroup ACTION_UP");
                break;
            default:
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }


    private View findTopChildUnder(int x, int y) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            int bottm = getBottom();
            if (x >= child.getLeft() && x < child.getRight() && y < child.getBottom() && y >= child.getTop()) {
                return child;
            }
        }
        return null;

    }

    private void initPoint() {
        mPoint = new Point(0, 0);
    }


}
