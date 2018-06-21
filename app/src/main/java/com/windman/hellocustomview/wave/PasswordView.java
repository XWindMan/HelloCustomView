package com.windman.hellocustomview.wave;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2018/4/20/020.
 * 描述：
 */

public class PasswordView extends AppCompatTextView {

    private float mWidth, mHeight;
    private List<Float> centerList;
    private float mUnit;
    private Bitmap bitmap;
    private float padding = 20;
    private int mCount = 5;
    private Paint mPaint;

    public PasswordView(Context context) {
        this(context, null);
    }

    public PasswordView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PasswordView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        centerList = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;

        mUnit = (mWidth - (mCount + 2) * padding) / mCount;
        for (int i = 1; i <= mCount; i++) {
            centerList.add(mWidth / mCount * i - (mWidth / mCount / 2));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mCount; i++) {
            float cx = centerList.get(i);
            canvas.drawRoundRect(new RectF(cx - mUnit / 2,
                            mHeight / 2 - mUnit / 2, cx + mUnit / 2,
                            mHeight / 2 + mUnit / 2)
                    , 5, 5, mPaint);
            mPaint.setColor(Color.RED);
            canvas.drawCircle(centerList.get(i), mHeight / 2, 10, mPaint);
//            canvas.drawPoint(centerList.get(i), mHeight / 2, mPaint);
        }
    }
}
