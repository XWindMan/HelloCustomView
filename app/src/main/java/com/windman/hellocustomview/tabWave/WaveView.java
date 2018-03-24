package com.windman.hellocustomview.tabWave;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class WaveView extends View {
    /**
     * y=Asin(ωx+φ)叫正弦函数。
     * φ（初相位）：决定波形与X轴位置关系或横向移动距离（左加右减）
     * ω：决定周期（最小正周期T=2π/|ω|）
     * A：决定峰值（即纵向拉伸压缩的倍数）
     * h：表示波形在Y轴的位置关系或纵向移动距离（上加下减）
     */
    /**
     * +------------------------+
     * |<--wave length->        |______
     * |   /\          |   /\   |  |
     * |  /  \         |  /  \  | amplitude
     * | /    \        | /    \ |  |
     * |/      \       |/      \|__|____
     * |        \      /        |  |
     * |         \    /         |  |
     * |          \  /          |  |
     * |           \/           | water level
     * |                        |  |
     * |                        |  |
     * +------------------------+__|____
     */
    private static final float DEFAULT_AMPLITUDE_RATIO = 0.12f;
    private static final float DEFAULT_WATER_LEVEL_RATIO = 0.5f;
    private static final float DEFAULT_WAVE_LENGTH_RATIO = 1.0f;
    private static final float DEFAULT_WAVE_SHIFT_RATIO = 0.0f;
    // if true, the shader will display the wave
    private boolean mShowWave;

    // shader containing repeated waves
    private BitmapShader mWaveShader;
    // shader matrix
    private Matrix mShaderMatrix;
    // paint to draw wave
    private Paint mWavePaint;
    private Paint mProgressPaint;

    private float mDefaultAmplitude;
    private float mDefaultWaterLevel;
    private float mDefaultWaveLength;
    private double mDefaultAngularFrequency;

    private float mAmplitudeRatio = DEFAULT_AMPLITUDE_RATIO;
    private float mWaveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO;
    private float mWaterLevelRatio = DEFAULT_WATER_LEVEL_RATIO;
    private float mWaveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO;

    private int mBehindWaveColor = Color.parseColor("#2887CEFA");
    private int mFrontWaveColor = Color.parseColor("#3C7B68EE");
    private int mMiddleWaveColor = Color.parseColor("#4DB0C4DE");
    private int mProgressStartColor = Color.parseColor("#6445FB");
    private int mProgressEndColor = Color.parseColor("#0C78E4");

    private float mPadding = 80;
    private float progress = 80;
    private float mWidth;
    private float mHeight;

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mShaderMatrix = new Matrix();
        mWavePaint = new Paint();
        mWavePaint.setAntiAlias(true);
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setColor(mProgressStartColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        createShader();
    }

    /**
     * Create the shader with default waves which repeat horizontally, and clamp vertically
     */
    private void createShader() {
        mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO / getWidth();
        mDefaultAmplitude = getHeight() * DEFAULT_AMPLITUDE_RATIO;
        mDefaultWaterLevel = getHeight() * DEFAULT_WATER_LEVEL_RATIO;
        mDefaultWaveLength = getWidth();

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint wavePaint = new Paint();
        wavePaint.setStrokeWidth(2);
        wavePaint.setAntiAlias(true);

        // Draw default waves into the bitmap
        // y=Asin(ωx+φ)+h
        final int endX = getWidth() + 1;
        final int endY = getHeight() + 1;

        float[] waveY = new float[endX];

        wavePaint.setColor(mBehindWaveColor);
        for (int beginX = 0; beginX < endX; beginX++) {
            double wx = beginX * mDefaultAngularFrequency;
            float beginY = (float) (mDefaultWaterLevel +
                    mDefaultAmplitude * Math.sin(wx));
            canvas.drawLine(beginX, beginY, beginX, endY, wavePaint);

            waveY[beginX] = beginY;
        }

        wavePaint.setColor(mFrontWaveColor);
        final int wave2Shift = (int) (mDefaultWaveLength / 4);
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + wave2Shift) % endX],
                    beginX, endY, wavePaint);
        }

        wavePaint.setColor(mMiddleWaveColor);
        final int waveMiddle = (int) (mDefaultWaveLength / 2);
        for (int beginX = 0; beginX < endX; beginX++) {
            canvas.drawLine(beginX, waveY[(beginX + waveMiddle) % endX],
                    beginX, endY, wavePaint);
        }

        // use the bitamp to create the shader
        mWaveShader = new BitmapShader(bitmap, Shader.TileMode.REPEAT,
                Shader.TileMode.CLAMP);
        mWavePaint.setShader(mWaveShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // modify paint shader according to mShowWave state
        if (mShowWave && mWaveShader != null) {
            // first call after mShowWave, assign it to our paint
            if (mWavePaint.getShader() == null) {
                mWavePaint.setShader(mWaveShader);
            }

            // sacle shader according to mWaveLengthRatio and mAmplitudeRatio
            // this decides the size(mWaveLengthRatio for width, mAmplitudeRatio for height) of waves
            mShaderMatrix.setScale(
                    mWaveLengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
                    mAmplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
                    0,
                    mDefaultWaterLevel);
            // translate shader according to mWaveShiftRatio and mWaterLevelRatio
            // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
            mShaderMatrix.postTranslate(
                    -mWaveShiftRatio * getWidth(),
                    (DEFAULT_WATER_LEVEL_RATIO - mWaterLevelRatio) * getHeight());

            // assign matrix to invalidate the shader
            mWaveShader.setLocalMatrix(mShaderMatrix);

            mProgressPaint.reset();
            mProgressPaint.setAntiAlias(true);
            mProgressPaint.setStrokeWidth(1);
            mProgressPaint.setStyle(Paint.Style.FILL);
            float borderWidth = mProgressPaint == null ? 0f : mProgressPaint.getStrokeWidth();
            if (borderWidth > 0) {
                canvas.drawCircle(mWidth / 2f, mHeight / 2f,
                        (mWidth - borderWidth) / 2f - mPadding, mProgressPaint);
            }
            float radius = mWidth / 2f - borderWidth - mPadding;
            canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, mWavePaint);
            canvas.save();
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.rotate(135, 0, 0);
            drawProgress(canvas);
            canvas.restore();
        } else {
            mWavePaint.setShader(null);
        }
    }

    private void drawProgress(Canvas canvas) {
        mProgressPaint.setStrokeWidth(4);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setColor(Color.parseColor("#1F1F28"));
        mProgressPaint.setStrokeWidth(4);
        mProgressPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(new RectF(-mWidth / 2 + 10, -mHeight / 2 + 10,
                mWidth / 2 - 10, mHeight / 2 - 10), 0, 270, false, mProgressPaint);
        SweepGradient shader = new SweepGradient(mWidth / 2, mHeight / 2,
                mProgressStartColor, mProgressEndColor);
        mProgressPaint.setShader(shader);
        mProgressPaint.setStrokeWidth(20);
        canvas.drawArc(new RectF(-mWidth / 2 + 10, -mHeight / 2 + 10,
                mWidth / 2 - 10, mHeight / 2 - 10), 2, progress, false, mProgressPaint);
        float x = 0, y = 0;
        float r = (mWidth / 2 - 10 - mProgressPaint.getStrokeWidth());
        x = (float) (r * Math.cos(progress));
        y = (float) (r * Math.sin(progress));
        if (progress <= 90) {

        } else if (progress <= 180) {

        } else if (progress <= 270) {

        }
        Log.d("", "drawProgress: x=" + x + ",y=" + y + ",r=" + r);
        mProgressPaint.setStyle(Paint.Style.FILL);
        mProgressPaint.setColor(mProgressEndColor);
//        canvas.drawCircle(Math.abs(x), y, 20, mProgressPaint);
        mProgressPaint.setColor(Color.parseColor("#ffffff"));
//        canvas.drawCircle(Math.abs(x), y, 10, mProgressPaint);
    }

    public void setmProgressStartColor(int mProgressStartColor) {
        this.mProgressStartColor = mProgressStartColor;
        postInvalidate();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setmProgressEndColor(int mProgressEndColor) {
        this.mProgressEndColor = mProgressEndColor;
        postInvalidate();
    }

    public float getWaveShiftRatio() {
        return mWaveShiftRatio;
    }

    /**
     * Shift the wave horizontally according to <code>waveShiftRatio</code>.
     *
     * @param waveShiftRatio Should be 0 ~ 1. Default to be 0.
     *                       Result of waveShiftRatio multiples width of WaveView is the length to shift.
     */
    public void setWaveShiftRatio(float waveShiftRatio) {
        if (mWaveShiftRatio != waveShiftRatio) {
            mWaveShiftRatio = waveShiftRatio;
            invalidate();
        }
    }

    public float getWaterLevelRatio() {
        return mWaterLevelRatio;
    }

    /**
     * Set water level according to <code>waterLevelRatio</code>.
     *
     * @param waterLevelRatio Should be 0 ~ 1. Default to be 0.5.
     *                        Ratio of water level to WaveView height.
     */
    public void setWaterLevelRatio(float waterLevelRatio) {
        if (mWaterLevelRatio != waterLevelRatio) {
            mWaterLevelRatio = waterLevelRatio;
            invalidate();
        }
    }

    public float getAmplitudeRatio() {
        return mAmplitudeRatio;
    }

    /**
     * Set vertical size of wave according to <code>amplitudeRatio</code>
     *
     * @param amplitudeRatio Default to be 0.05. Result of amplitudeRatio + waterLevelRatio should be less than 1.
     *                       Ratio of amplitude to height of WaveView.
     */
    public void setAmplitudeRatio(float amplitudeRatio) {
        if (mAmplitudeRatio != amplitudeRatio) {
            mAmplitudeRatio = amplitudeRatio;
            invalidate();
        }
    }

    public float getWaveLengthRatio() {
        return mWaveLengthRatio;
    }

    /**
     * Set horizontal size of wave according to <code>waveLengthRatio</code>
     *
     * @param waveLengthRatio Default to be 1.
     *                        Ratio of wave length to width of WaveView.
     */
    public void setWaveLengthRatio(float waveLengthRatio) {
        mWaveLengthRatio = waveLengthRatio;
    }

    public boolean isShowWave() {
        return mShowWave;
    }

    public void setShowWave(boolean showWave) {
        mShowWave = showWave;
    }

    public void setBorder(int width, int color) {
        if (mProgressPaint == null) {
            mProgressPaint = new Paint();
            mProgressPaint.setAntiAlias(true);
            mProgressPaint.setStyle(Paint.Style.FILL);
        }
        mProgressPaint.setColor(Color.parseColor("#1F1F28"));
        mProgressPaint.setStrokeWidth(width);

        invalidate();
    }

    public void setWaveColor(int behindWaveColor, int frontWaveColor, int middleWaveColor) {
        mBehindWaveColor = behindWaveColor;
        mFrontWaveColor = frontWaveColor;
        this.mMiddleWaveColor = middleWaveColor;

        if (getWidth() > 0 && getHeight() > 0) {
            // need to recreate shader when color changed
            mWaveShader = null;
            createShader();
            invalidate();
        }
    }

}
