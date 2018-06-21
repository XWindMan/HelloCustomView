package com.windman.hellocustomview.wave;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

/**
 * 1. 求控制点的直线
 * 2. 根据一屏幕内的波形个数求出控制点
 * 3. 根据控制点绘制峰值不同的贝塞尔曲线
 * 4. 改变控制点的曲线的斜率，从而改变波浪的高低
 */
public class BezierView extends View {
    private static final String TAG = "BezierView";
    private int dx;
    private float waveNum = 2f;
    private float waveWidth, waveHeight;
    private float mWidth, mHeight;
    private float scole;// 斜率
    private Path mPath;
    private Path mPath2;
    private Paint mPaint;
    private int topColor, bottomColor;
    private float A = 200;


    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
        mPath2 = new Path();

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        topColor = Color.parseColor("#FF00FF");
        bottomColor = Color.parseColor("#7B68EE");

        scole = (float) Math.tan(Math.PI / 6);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        this.waveWidth = mWidth / waveNum;
    }

    private int mItemWaveLength = 400;
    private long lastTime = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, mHeight / 2);
        mPaint.setAlpha(150);
        canvas.drawPath(getPath(mPath, 0), mPaint);
        canvas.drawPath(getPath(mPath2, 150), mPaint);
        lastTime = System.currentTimeMillis();
    }

    private Path getPath(Path path, float pathOffset) {
        path.reset();
        path.moveTo(0, mHeight / 2);
        float diffX = (float) (mWidth / 2 * Math.PI / 180 % (Math.PI * 2));
        float offset = (System.currentTimeMillis() - lastTime) / 5;
        Log.d(TAG, "onDraw: offset== " + offset);
        for (int i = 0; i < mWidth; i++) {
            float y = (float) (getA() * Math.sin(i * Math.PI / 180 + Math.PI / 2 - offset + pathOffset)
                    );
            path.lineTo(i, y);
        }
        path.lineTo(mWidth, mHeight / 2);
        path.lineTo(0, mHeight / 2);
        path.close();
        LinearGradient shader = new LinearGradient(0, 0, 0, mHeight / 2,
                topColor, bottomColor,
                Shader.TileMode.CLAMP);
        mPaint.setShader(shader);
        return path;
    }

    private float mScoleDy;// 衰减因子直线在y轴的△y

    private float getFactor(float a) {
        float fx2 = 0.5f;
        if (a <= mWidth / 2) {
            fx2 = mScoleDy / (mWidth / 2 - 0) * a;
        }
        if (a >= mWidth / 2) {
            fx2 = -mScoleDy / (mWidth - mWidth / 2) * a + 2 * mScoleDy;
        }
        return fx2;
    }

    private float minA = 100;

    private float getA() {
        return minA + (A - minA) * (curLevel / 10f);
    }

    private float baseLevel = 0.3f;
    private float curLevel = 0.5f;

    public void setLevel(int level) {
        this.curLevel = level == 0 ? 1 : level;
        start();
    }

    public void start() {
        float min = baseLevel - curLevel / 10;
        ValueAnimator animator = ValueAnimator.ofFloat(min <= 0 ? 0 : min, baseLevel + curLevel / 10);
        animator.setRepeatCount(ValueAnimator.INFINITE);
//        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScoleDy = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
