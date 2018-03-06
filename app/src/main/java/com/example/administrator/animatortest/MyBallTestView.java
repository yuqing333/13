package com.example.administrator.animatortest;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/3/2.
 */

public class MyBallTestView extends View {

    int isStart = 0;
    float translateFloat = 0.0f;
    final int ballPointX = 100;
    final int ballPointY = 100;
    final int ballRadius = 50;

    Paint ballPaint;

    public MyBallTestView(Context context) {
        this(context, null);
    }

    public MyBallTestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ballPaint = new Paint();
        ballPaint.setColor(Color.GRAY);
        ballPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (isStart < 1) {
            isStart++;
            startAnimation();
        } else {

            canvas.save();
            float dx = translateFloat * 100;
            float dy = translateFloat * translateFloat*100;
            canvas.translate(dx, dy);
            canvas.drawCircle(ballPointX, ballPointY, ballRadius, ballPaint);
            canvas.restore();
        }
    }

    private void startAnimation() {
        final ValueAnimator anim1 = ValueAnimator.ofFloat(0.0f, 2.5f);
        anim1.setDuration(3000);
        anim1.setRepeatCount(-1);
        anim1.setRepeatMode(ValueAnimator.RESTART);
        anim1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                translateFloat = (float) anim1.getAnimatedValue();
                postInvalidate();

            }
        });
        anim1.start();


    }


}
