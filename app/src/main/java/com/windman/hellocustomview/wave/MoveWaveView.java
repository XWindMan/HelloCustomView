package com.windman.hellocustomview.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2018/4/25/025.
 * 描述：
 */

public class MoveWaveView extends View {
    private static final String TAG = "MoveWaveView";
    private float mWidth, mHeight;
    private Paint mPaint;
    private List<Path> waves;
    private float[] shakeRatio = new float[]{5f, 10f, 15f};
    private float[] waveOffse = new float[]{(float) (Math.PI / 2),
            0f,
            (float) -(Math.PI / 2)};

    public MoveWaveView(Context context) {
        this(context, null);
    }

    public MoveWaveView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MoveWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        waves = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#DA70D6"));
        mPaint.setAlpha(150);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0, mHeight / 2);
        refresh();
        for (int i = 0; i < waves.size(); i++) {
            canvas.drawPath(waves.get(i), mPaint);
        }
    }

    private float maxA = 200;

    private float getA(int i) {
        if (i <= mWidth / 2) {
            return i / (mWidth / 2) * maxA;
        } else {
            return maxA - (mWidth - i) / (mWidth / 2) * maxA;
        }
    }

    private float offseX = 0.1f;

    public void refresh() {
        waves.clear();
        offseX += 0.1f;
        for (int i = 0; i < shakeRatio.length; i++) {
            Path path = new Path();
            path.moveTo(0, mHeight);
            for (int j = 0; j < mWidth; j++) {
                path.lineTo(j, (float) (getA(j) * Math.sin(Math.toRadians(j) + waveOffse[i] + offseX)));
                path.lineTo(mWidth, mHeight);
                path.close();
            }
            waves.add(i, path);
        }
        postInvalidate();
    }
}
