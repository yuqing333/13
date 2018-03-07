package com.example.administrator.animatortest.myview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.constraint.solver.widgets.Rectangle;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/6.
 */

public class MyRingView extends View {

    private int width;
    private int height;
    private int radius;
    private float sweepAngle;
    private float animatorValue;
    private ValueAnimator animator;
    private String drawText;

    int totalCompany = 1;
    int supportCompany = 0;

    Paint ringPaint;
    Paint backgroundPaint;
    Paint numberPaint;
    Paint ringBackgroundPaint;
    Paint textPaint;

    public MyRingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        initAnimation();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        radius = width / 4;


    }


    @TargetApi(21)
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, width, height, backgroundPaint);
        float numberWidth = numberPaint.measureText(int2Str(supportCompany));
        canvas.drawText(int2Str(supportCompany), width / 2 - numberWidth / 2, height / 2 + 6, numberPaint);
        float textWidth = textPaint.measureText(drawText);
        canvas.drawText(drawText, width / 2 - textWidth / 2, height * 3 / 4 + 24, textPaint);
        canvas.drawArc(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius, 90, 360, false, ringBackgroundPaint);
        canvas.drawArc(width / 2 - radius, height / 2 - radius, width / 2 + radius, height / 2 + radius, -90, sweepAngle * animatorValue, false, ringPaint);

    }

    private void initAnimation() {
        animator = ValueAnimator.ofFloat(0, 1.0f);
        animator.setDuration(2000);
//        animator.setRepeatCount(-1);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animatorValue = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

    }

    private void initPaint() {
        ringPaint = new Paint();
        ringPaint.setColor(Color.RED);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(6);
        ringPaint.setAntiAlias(true);

        backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.GRAY);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(20);
        textPaint.setAntiAlias(true);

        numberPaint = new Paint();
        numberPaint.setColor(Color.WHITE);
        numberPaint.setTextSize(24);
        numberPaint.setAntiAlias(true);

        ringBackgroundPaint = new Paint();
        ringBackgroundPaint.setColor(Color.WHITE);
        ringBackgroundPaint.setStyle(Paint.Style.STROKE);
        ringBackgroundPaint.setStrokeWidth(6);
        ringBackgroundPaint.setAntiAlias(true);

    }

    public void start() {
        if (animator.isRunning()) {
            animator.cancel();
        }
        animator.start();
    }

    public void setSupportAndTotalNumber(int supportNum, int totalNum) {
        supportCompany = supportNum;
        totalCompany = totalNum;
        setSweepAngle();
    }

    private void setSweepAngle() {
        sweepAngle = (float) supportCompany / totalCompany * 360;
    }

    public void setUnderText(String text) {
        drawText = text;
    }


    private String int2Str(int i) {
        return String.valueOf(i);

    }
}
