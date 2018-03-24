package com.windman.hellocustomview.tabWave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.windman.hellocustomview.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class TranslateTab extends View {

    private int time = 1 * 1000;
    private float mHeight;
    private float mWidth;
    private Paint mPaint;
    private Paint mTextPaint;
    private float mPadding;
    private int mPosition;
    private float mUnitWidth;
    private float mHalfHeight;
    private Rect mRect;
    private float mX;
    private int mDuration;
    private Context mContext;

    private List<String> textList;
    private List<Interval> mInterval;
    private boolean isTranslate = false;

    public TranslateTab(Context context) {
        this(context, null);
    }

    private static final String TAG = "TranslateTab";

    public TranslateTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    void init() {
        mPadding = Utils.dip2px(mContext, 2);
        mPosition = 0;
        mX = 0;
        mDuration = 1000;
        textList = new ArrayList<>();
        mInterval = new ArrayList<>();
        mPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(Utils.dip2px(mContext, 15));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = 500; //设定一个最小值
        int height = 50;
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST || widthMode == MeasureSpec.UNSPECIFIED || heightMeasureSpec == MeasureSpec.AT_MOST || heightMeasureSpec == MeasureSpec.UNSPECIFIED) {

        } else {
            if (widthMode == MeasureSpec.EXACTLY) {
                width = widthSize;
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                height = heightSize;
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
        mUnitWidth = (w - 2 * mPadding) / textList.size();
        mHalfHeight = h / 2;
        mRect = new Rect((int) mPadding, (int) -(mHalfHeight * 1.5), (int) (mWidth - mPadding), (int) (mHalfHeight * 1.5));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (textList.size() == 0) {
            return;
        }
        canvas.translate(0, mHeight / 2);
        mPaint.reset();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        drawLayoutBg(canvas);
        drawTabItem(canvas);
        drawText(canvas);
        if (isTranslate) {
            float distance = mPadding + mPosition * mUnitWidth - mX;
            mX = mX + distance / (mDuration / 100);
            postInvalidateDelayed(10);
            if (distance >= 0) {
                if (mX + 2 >= mPosition * mUnitWidth + mPadding) {
                    isTranslate = false;
                }
            } else {
                if (mX - 2 <= mPosition * mUnitWidth + mPadding) {
                    isTranslate = false;
                }
            }
        }
    }

    private void drawLayoutBg(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#39394A"));
        mPaint.setStrokeWidth(Utils.dip2px(mContext, 1));
        mInterval.clear();
        for (int i = 0; i < textList.size(); i++) {
            mInterval.add(new Interval(i * mUnitWidth, (i + 1) * mUnitWidth));
//            Log.d(TAG, "drawLayoutBg: value " + i + ",," + mInterval.get(i));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(mPadding, -mHalfHeight, mWidth - mPadding, mHalfHeight, mHalfHeight, mHalfHeight, mPaint);
        } else {
            canvas.drawRect(mPadding, -mHalfHeight, mWidth - mPadding, mHalfHeight, mPaint);
        }
    }

    private void drawTabItem(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#0C78E4"));
        float start = mX;
        float end = mX + mUnitWidth;
        LinearGradient shader = new LinearGradient(start, 0, end, 0,
                Color.parseColor("#6445FB"), Color.parseColor("#0C78E4"), Shader.TileMode.REPEAT);
        mPaint.setShader(shader);
        mPaint.setAlpha(150);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            canvas.drawRoundRect(start, -mHalfHeight, end, mHalfHeight, mHalfHeight, mHalfHeight, mPaint);
        } else {
            canvas.drawRect(start, -mHalfHeight, end, mHalfHeight, mPaint);
        }
    }

    private void drawText(Canvas canvas) {
//        float start = mPadding + mPosition * mUnitWidth;
//        float end = (mPosition + 1) * mUnitWidth - mPadding;
        for (int i = 0; i < textList.size(); i++) {
            if (mPosition == i) {
                mTextPaint.setColor(Color.parseColor("#ffffff"));
            } else {
                mTextPaint.setColor(Color.parseColor("#666666"));
            }
            String text = textList.get(i);
            int x = (int) (mPadding + mUnitWidth / 2 +
                    i * mUnitWidth - mTextPaint.measureText(text) / 2);
            Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
            float y = 0 - (fontMetrics.top + fontMetrics.bottom) / 2;
            canvas.drawText(text, x, y, mTextPaint);
        }
    }

    public int getmPosition() {
        return mPosition;
    }

    public void setmPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public void setTextList(List<String> textList) {
        this.textList.clear();
        this.textList = textList;
    }

    private onTabClickListener listener;

    public interface onTabClickListener {
        void onTabClick(int position);
    }

    public void setOnTabClickListener(onTabClickListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mRect.contains(x, y)) {
                    int position = 0;
                    isTranslate = true;
                    for (int i = 0; i < mInterval.size(); i++) {
                        if (Utils.isInInerval(x, mInterval.get(i).min, mInterval.get(i).max)) {
                            position = i;
                        }
                    }
                    postInvalidate();
                    mPosition = position;
                    if (listener != null) {
                        listener.onTabClick(position);
                    }
                }
                break;
        }
        return true;
    }

    class Interval {
        float min;
        float max;

        public Interval(float min, float max) {
            this.min = min;
            this.max = max;
        }
    }
}
