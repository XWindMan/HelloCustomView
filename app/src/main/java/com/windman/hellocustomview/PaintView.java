package com.windman.hellocustomview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PaintView extends View implements View.OnTouchListener {
    private static final String TAG = "PaintView";

    private Circle mCurrentCircle;
    private int randomIndex;
    private List<Circle> listCircle;
    private float radius = dp2px(20);
    private int screenWidth;
    private int screenHeight;
    private int controlWidth;
    private int controlHeight;
    private String[] randomColor = {"#601986",
            "#ee7800",
            "#b5d100",
            "#e5006e",
            "#71c7d1"};
    // 标题栏高度
    private int mTitleHeight;

    public PaintView(Context c) {
        this(c, null);
    }

    public PaintView(Context c, AttributeSet attrs) {
        super(c, attrs);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        mTitleHeight = dp2px(56f);
        listCircle = new ArrayList<>();
        randomIndex = 1;
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画布颜色
        canvas.drawARGB(80, 112, 128, 144);
        // 画圆
        if (listCircle != null) {
            drawCircle(canvas);
            compactCircles();
        }
    }

    private void drawCircle(Canvas canvas) {
        for (Circle circle : listCircle) {
            canvas.drawCircle(circle.mX, circle.mY, radius, circle.mPaint);
        }
    }

    /**
     * 外部调用
     *
     * @param x
     * @param y
     */
    public void addPostion(float x, float y) {
        if (x == 0 && (y == 0 || y == 400)) {
            Random random = new Random();
            randomIndex = random.nextInt(5);
        } else {
            mCurrentCircle = new Circle();
            mCurrentCircle.mX = x;
            mCurrentCircle.mY = y + mTitleHeight;
            addList(mCurrentCircle);
            invalidate();
        }
    }

    private void addList(Circle circle) {
        if (listCircle == null) {
            listCircle = new ArrayList<>();
        }
        listCircle.add(circle);
    }

    private void compactCircles() {
        int size = listCircle.size();
        int index = size - 1;
        if (size == 0) {
            return;
        }
        int baseAlpha = 250 - Circle.ALPHA_STEP;
        int itselfAlpha;
        Circle circle;
        for (; index >= 0; index--, baseAlpha -= Circle.ALPHA_STEP) {
            circle = listCircle.get(index);
            itselfAlpha = circle.mPaint.getAlpha();
            if (itselfAlpha == 255) {
                if (baseAlpha <= 0) {
                    ++index;
                    break;
                }
                circle.setAlpha(baseAlpha);
            } else {
                itselfAlpha -= Circle.ALPHA_STEP;
                if (itselfAlpha <= 0) {
                    ++index;
                    break;
                }
                circle.setAlpha(itselfAlpha);
            }
        }

        if (index >= size) {
            listCircle = null;
        } else if (index >= 0) {
            listCircle = listCircle.subList(index, size);
        }
        invalidate();
    }

    public void setTouchAble(boolean click) {
        if (!click) {
            setOnTouchListener(null);
        }
    }

    private int dp2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Random random = new Random();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                randomIndex = random.nextInt(5);
                mCurrentCircle = new Circle();
                addList(mCurrentCircle);
                mCurrentCircle.mX = x;
                mCurrentCircle.mY = y;
                invalidate();
                break;

            case MotionEvent.ACTION_MOVE:
                mCurrentCircle = new Circle();
                mCurrentCircle.mX = x;
                mCurrentCircle.mY = y;
                addList(mCurrentCircle);
                break;

            case MotionEvent.ACTION_UP:
                mCurrentCircle = null;
                invalidate();
                break;
        }
        invalidate();
        return true;
    }

    class Circle {
        float mX;
        float mY;
        Paint mPaint;
        static final int ALPHA_STEP = 25;

        private Circle() {
            mPaint = new Paint();
            mPaint.setColor(Color.parseColor(randomColor[randomIndex]));
            mPaint.setAntiAlias(true);
            mPaint.setStrokeWidth(16);
            mPaint.setStrokeCap(Paint.Cap.BUTT);
            mPaint.setStyle(Paint.Style.FILL);
        }

        private void setAlpha(int alpha) {
            mPaint.setAlpha(alpha);
        }
    }
}