package com.windman.hellocustomview.shader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

public class SweepGradientView extends View {

    private Paint mPaint = null;
    private float degrees;
    private SweepGradient mSweepGradient = null;

    public SweepGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mSweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2,
                Color.parseColor("#3F51B5"), Color.parseColor("#B0C4DE"));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);

        mPaint.setShader(mSweepGradient);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.rotate(-90, getWidth() / 2, getHeight() / 2);

        canvas.drawArc(new RectF(20, 20, getWidth() - 20, getHeight() - 20), 2, degrees, false, mPaint);
    }

    public float getDegrees() {
        return degrees;
    }

    public void setDegrees(float degrees) {
        this.degrees = degrees;
        postInvalidate();
    }
}