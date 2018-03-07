package com.example.administrator.animatortest.myview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2018/3/7.
 */

public class TouchBallView extends View {

    private PointF ballPosition;
    private PointF touchStartPostiton;
    private PointF touchEndPosition;

    private int width;
    private int height;
    private int radius;

    private Paint ballPaint;

    public TouchBallView(Context context) {
        super(context);
        initPaint();
    }

    public TouchBallView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);

        radius = 20;
        initPosition();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(ballPosition.x, ballPosition.y, radius, ballPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                touchStartPostiton.x = event.getX();
                touchStartPostiton.y = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                ballPosition.x = event.getX();
                ballPosition.y = event.getY();
                this.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                ballPosition.x = event.getX();
                ballPosition.y = event.getY();
                if (ballPosition.x < width / 2) {
                    ballPosition.x = 0;
                    this.postInvalidate();
                } else {
                    ballPosition.x = width;
                    this.postInvalidate();
                }
                break;
            default:
        }
        return true;
    }

    private void initPosition() {
        ballPosition = new PointF(100, 100);
        touchStartPostiton = new PointF(0, 0);
        touchEndPosition = new PointF(0, 0);
    }

    private void initPaint() {
        ballPaint = new Paint();
        ballPaint.setColor(Color.GRAY);
        ballPaint.setStyle(Paint.Style.FILL);
        ballPaint.setAntiAlias(true);
    }
}
