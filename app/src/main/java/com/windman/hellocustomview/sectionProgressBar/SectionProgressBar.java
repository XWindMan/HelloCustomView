package com.windman.hellocustomview.sectionProgressBar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.windman.hellocustomview.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.View.MeasureSpec.EXACTLY;

/**
 * Created by WindMan on 2017/1/3.
 */

public class SectionProgressBar extends View {
    private static final String TAG = "SectionProgressBar";

    // 段数
    private int mSection = 3;
    // 是否等分
    private boolean isEqualParts = true;
    // 颜色数组
    private int[] color = new int[]
            {
                    Color.BLUE, Color.YELLOW, Color.RED, Color.GREEN, Color.GRAY
            };
    private Paint mPaint;
    private Paint mTextPaint;
    private Paint mBitmapPaint;
    private int width;
    // 每一段的中点，用来写描述文字
    private List<Float> mSectionHalf;
    // 每一段的末尾，用来写强度值
    private List<Float> mSectionEnd;
    private List<String> mDataText;
    private List<Float> mDataStrength;
    private List<Float> mSectionPercent;
    private List<Float> mSectionLength;
    private float mCurrentStrength;
    private Bitmap mBitmap;
    private float mBitmapPosition;
    private float mPadding = 25f;
    private Animation mExpandAnimation;
    private Animation mCollapseAnimation;
    private boolean mIsExpand;

    public SectionProgressBar(Context context) {
        this(context, null);
    }

    public SectionProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SectionProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SectionProgressBar, defStyleAttr, 0);
        try {
            isEqualParts = ta.getBoolean(R.styleable.SectionProgressBar_isEqualParts, false);
        } finally {
            ta.recycle();
        }

        initPaint();
        mSectionHalf = new ArrayList<>();
        mSectionEnd = new ArrayList<>();
        mSectionLength = new ArrayList<>();
        initDefault();
        mExpandAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.expand);
        mCollapseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.collapse);
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStrokeWidth(20);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.BUTT);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(25);
        mTextPaint.setAntiAlias(true);

        mBitmapPaint = new Paint();
    }

    private void initDefault() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tip_low);

        mDataStrength = new ArrayList<>();
        mDataStrength.add(0f);
        mDataStrength.add(10f);
        mDataStrength.add(30f);
        mDataStrength.add(60f);
        mDataStrength.add(100f);

        mDataText = new ArrayList<>();
        mDataText.add("兽兽");
        mDataText.add("禽兽");
        mDataText.add("野兽");
        mDataText.add("教兽");

        mCurrentStrength = 32.5f;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);
        int w, h;
        if (wMode == EXACTLY) {
            w = wSize;
        } else {
            w = 800;
        }
        if (hMode == EXACTLY) {
            h = hSize;
        } else {
            h = 200;
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w - (int) mPadding * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawSection(canvas);
        Log.d(TAG, "onDraw: mSection=" + mSection);
        drawText(canvas);
        drawCurrentStrength(canvas);
    }

    /**
     * 画线段
     *
     * @param canvas
     */
    private void drawSection(Canvas canvas) {
        canvas.translate(mPadding, getHeight() / 2);
        canvas.save();
        float startX = 0, startY = 0;
        float stopX = 0, stopY = 0;
        mSection = mDataStrength.size() - 1;
        if (isEqualParts) {
            // 等分
            for (int i = 0; i < mSection; i++) {
                mPaint.setColor(color[i]);
                startX = width * i * 1.0f / mSection;
                stopX = width * (i + 1) * 1.0f / mSection;
                mSectionHalf.add((startX + stopX) / 2);
                mSectionEnd.add(stopX);
                mSectionLength.add(stopX - startX);
                canvas.drawLine(startX, startY, stopX, stopY, mPaint);
            }
            canvas.restore();
        } else {
            // 不等分
            calculateSectionPercent();
            for (int i = 0; i < mSectionPercent.size(); i++) {
                mPaint.setColor(color[i]);
                stopX += mSectionPercent.get(i) * width;
                mSectionHalf.add((startX + stopX) / 2);
                mSectionEnd.add(stopX);
                mSectionLength.add(stopX - startX);
                canvas.drawLine(startX, startY, stopX, stopY, mPaint);
                startX += mSectionPercent.get(i) * width;
            }
            canvas.restore();
        }
    }

    /**
     * 描述和强度间隔
     *
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        canvas.save();
        float textW;
        for (int i = 0; i < mSectionHalf.size(); i++) {
            if (i <= mDataText.size() - 1) {
                textW = (int) mTextPaint.measureText(mDataText.get(i));
                canvas.drawText(mDataText.get(i), mSectionHalf.get(i) - textW / 2, 40, mTextPaint);
                Log.d(TAG, "drawText: text" + mDataText.get(i));
            }
        }
        for (int i = 0; i < mSectionEnd.size() - 1; i++) {
            if (i <= mDataText.size() - 1) {
                textW = (int) mTextPaint.measureText(mDataText.get(i));
                canvas.drawText(String.valueOf(mDataStrength.get(i + 1)), mSectionEnd.get(i) - textW / 2, 40, mTextPaint);
            }
        }
        canvas.restore();
    }

    /**
     * 指示图片和文字
     *
     * @param canvas
     */
    private void drawCurrentStrength(Canvas canvas) {
        canvas.save();
        calculateBitmapPostion();
        int textW = (int) mTextPaint.measureText(String.valueOf(mCurrentStrength));
        canvas.drawBitmap(mBitmap, mBitmapPosition - textW / 2 - 5, -70, mBitmapPaint);
        mTextPaint.setTextSize(20);
        canvas.drawText(String.valueOf(mCurrentStrength), mBitmapPosition - textW / 2, -35, mTextPaint);
        canvas.restore();
    }

    /**
     * 计算每一段所占总值的百分比
     */
    private void calculateSectionPercent() {
        mSectionPercent = new ArrayList<>();
        for (int i = 0; i < mDataStrength.size() - 1; i++) {
            mSectionPercent.add((mDataStrength.get(i + 1) - mDataStrength.get(i)) / mDataStrength.get(mDataStrength.size() - 1));
            Log.d(TAG, "calculateSectionPercent: " + mDataStrength.get(i));
        }
    }

    /**
     * 计算指示器的位置
     */
    private void calculateBitmapPostion() {
        int position = 0;// 计算当前强度在哪一段
        for (int i = 0; i < mDataStrength.size() - 1; i++) {
            if (mCurrentStrength >= mDataStrength.get(i)) {
                position = i + 1;
            }
        }
        // 当前强度与所在段最小值的差值
        float diffPercent = (mCurrentStrength - mDataStrength.get(position - 1))
                / (mDataStrength.get(position) - mDataStrength.get(position - 1));
        mBitmapPosition = diffPercent * mSectionLength.get(position - 1);
        for (int i = 0; i < position - 1; i++) {
            mBitmapPosition += mSectionLength.get(i);
        }
        int resId = 0;
        switch (position) {
            case 1:
                resId = R.mipmap.tip_low;
                break;
            case 2:
                resId = R.mipmap.tip_normal;
                break;
            case 3:
                resId = R.mipmap.tip_hight;
                break;
            case 4:
                resId = R.mipmap.tip_slight_hight;
                break;
            case 5:
                resId = R.mipmap.tip_very_hight;
                break;
        }
        mBitmap = BitmapFactory.decodeResource(getResources(), resId);
    }

    public SectionProgressBar setDataText(String... list) {
        this.mDataText = Arrays.asList(list);
        return this;
    }

    public SectionProgressBar setDataStrength(Float... list) {
        mDataStrength = Arrays.asList(list);
        for (int i = 0; i < mDataStrength.size(); i++) {
            Log.d(TAG, "setDataStrength: " + mDataStrength.get(i));
        }
        return this;
    }

    public int getmSection() {
        return mSection;
    }

    public void setmSection(int section) {
        this.mSection = section;
        Log.d(TAG, "setmSection: mSection=" + mSection);
    }

    public void refresh() {
        postInvalidate();
    }

    public boolean isExpand() {
        return mIsExpand;
    }

    public void collapse() {
        if (mIsExpand) {
            mIsExpand = false;
            clearAnimation();
            startAnimation(mCollapseAnimation);
            setVisibility(View.INVISIBLE);
        }
    }

    public void expand() {
        if (!mIsExpand) {
            mIsExpand = true;
            clearAnimation();
            startAnimation(mExpandAnimation);
            setVisibility(View.VISIBLE);
        }
    }
}
