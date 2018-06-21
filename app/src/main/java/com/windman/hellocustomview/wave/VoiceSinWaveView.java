package com.windman.hellocustomview.wave;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.windman.hellocustomview.R;
import com.windman.hellocustomview.utils.Cvd;
import com.windman.hellocustomview.utils.Utils;

import java.lang.ref.WeakReference;

public class VoiceSinWaveView extends View {

    private static final String TAG = "VoiceSinWaveView";
    private LinearInterpolator mInterpolator;
    private ValueAnimator WJ;
    private ValueAnimator diF;
    private ViewGroup mParent;
    private Paint cut;
    private Paint mPrimaryPaint;
    private Paint mSecondPaint;
    private Paint mCenterPaint;
    private int lineWidth1;
    private int lineWidht2;
    private float alphaScale;
    private float diA;
    private float diB;
    private float diC;
    private float diD;
    private float diE;
    private long diG;
    private long diH;
    private boolean play;
    private boolean diJ;
    private IFade callback;
    private MyThread mThread;
    private MyHandler mHandler;
    private Bitmap mBitmap;
    private Canvas nCanvas;
    private int diP;
    private PorterDuffXfermode mpdf;
    private boolean isAddTo;
    private float diq;
    private float dir;
    private long dis;
    private Path dit;
    private Path diu;
    private Path div;
    private float diw;
    private float dix;
    private int diy;
    private float diz;
    private int mHeight;
    private int mWidth;

    public interface IFade {
        void fadeOut();

        void fadeToQuarter();
    }

    class My2AnimatorUpdateListener implements AnimatorUpdateListener {
        final VoiceSinWaveView wave;

        My2AnimatorUpdateListener(VoiceSinWaveView voiceSinWaveView) {
            this.wave = voiceSinWaveView;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.wave.dir = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        }
    }

    class MyAnimatorListenerAdapter extends AnimatorListenerAdapter {
        final VoiceSinWaveView wave;

        MyAnimatorListenerAdapter(VoiceSinWaveView voiceSinWaveView) {
            this.wave = voiceSinWaveView;
        }

        public void onAnimationEnd(Animator animator) {
            super.onAnimationEnd(animator);
            if (this.wave.callback != null) {
                this.wave.callback.fadeOut();
            }
        }
    }

    class MyAnimatorUpdateListener implements AnimatorUpdateListener {
        final VoiceSinWaveView wave;

        MyAnimatorUpdateListener(VoiceSinWaveView voiceSinWaveView) {
            this.wave = voiceSinWaveView;
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.wave.dir = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            this.wave.diq = this.wave.dir;
        }
    }

    static class MyHandler extends Handler {
        private WeakReference<VoiceSinWaveView> waves;

        public MyHandler(VoiceSinWaveView voiceSinWaveView) {
            this.waves = new WeakReference(voiceSinWaveView);
        }

        public void handleMessage(Message message) {
            if (message.what == 4097) {
                VoiceSinWaveView voiceSinWaveView = this.waves.get();
                if (voiceSinWaveView != null) {
                    voiceSinWaveView.avq();
                    voiceSinWaveView.invalidate();
                    voiceSinWaveView.avp();
                }
            }
        }
    }

    static class MyThread extends Thread {
        private WeakReference<VoiceSinWaveView> waves;
        private volatile boolean diW = false;

        public MyThread(VoiceSinWaveView voiceSinWaveView) {
            this.waves = new WeakReference(voiceSinWaveView);
        }

        public synchronized void fe(boolean z) {
            this.diW = z;
        }

        public synchronized boolean avr() {
            return this.diW;
        }

        public void run() {
            while (!avr()) {
                VoiceSinWaveView voiceSinWaveView = this.waves.get();
                if (voiceSinWaveView != null) {
                    voiceSinWaveView.mHandler.sendEmptyMessage(4097);
                    try {
                        Thread.sleep(16);
                    } catch (InterruptedException e) {
                    }
                }
            }
        }
    }

    public VoiceSinWaveView(Context context) {
        this(context, null, 0);
    }

    public VoiceSinWaveView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.diq = 0.0f;
        this.dir = 0.0f;
        this.dis = 0;
        this.lineWidth1 = 10;
        this.lineWidht2 = 10;
        this.diw = 0.0f;
        this.dix = 0.0f;
        this.alphaScale = 0.5f;
        this.diy = 2;
        this.diz = 2.0f;
        this.diA = 1.6f;
        this.diB = -0.2f;
        this.diC = -0.1994f;
        this.diD = 0.0f;
        this.diE = 3.5f;
        this.diG = 200;
        this.diH = 250;
        this.play = false;
        this.diJ = false;
        this.diP = 0;
        this.isAddTo = true;
        this.mHandler = new MyHandler(this);
        init();
    }

    public void setCallBack(IFade iFade) {
        this.callback = iFade;
    }

    private void init() {
        if (getContext().getResources().getDisplayMetrics().density <=
                Cvd.RES_XHDPI_MINSCALE) {
            this.lineWidth1 = 1;
            this.lineWidht2 = 1;
        }
        this.mPrimaryPaint = new Paint();
        this.mPrimaryPaint.setAntiAlias(true);
        this.mPrimaryPaint.setStyle(Style.STROKE);
        this.mPrimaryPaint.setStrokeWidth((float) this.lineWidth1);

        this.mSecondPaint = new Paint();
        this.mSecondPaint.setAntiAlias(true);
        this.mSecondPaint.setStyle(Style.STROKE);
        this.mSecondPaint.setStrokeWidth((float) this.lineWidht2);
        this.mSecondPaint.setAlpha((int) (this.alphaScale * 255.0f));

        this.mCenterPaint = new Paint();
        this.cut = new Paint();
        this.dit = new Path();
        this.diu = new Path();
        this.div = new Path();
        this.mpdf = new PorterDuffXfermode(Mode.SRC_IN);
        this.mInterpolator = new LinearInterpolator();
    }

    private void setPaintShader() {
        if (this.mHeight > 0 && this.mWidth > 0) {
            this.diw = (((float) this.mHeight) - Cvd.RES_XXXHDPI_MINISCALE) * 0.5f;
            float f = 0.0f;
            float f2 = 0.0f;
            this.mPrimaryPaint.setShader(new LinearGradient(0.0f, f, (float) this.mWidth, f2,
                    getResources().getColor(R.color.mms_voice_primary_start),
                    getResources().getColor(R.color.mms_voice_primary_end),
                    TileMode.MIRROR));
            f = 0.0f;
            f2 = 0.0f;
            this.mSecondPaint.setShader(new LinearGradient(0.0f, f, (float) this.mWidth, f2,
                    getResources().getColor(R.color.mms_voice_secondary_start),
                    getResources().getColor(R.color.mms_voice_secondary_end),
                    TileMode.MIRROR));
            f = ((float) (this.mHeight / 2)) - this.diw;
            float f3 = 0.0f;
            this.mCenterPaint.setShader(new LinearGradient(0.0f, f, f3, this.diw + ((float) (this.mHeight / 2)),
                    getResources().getColor(R.color.mms_voice_fill_top),
                    getResources().getColor(R.color.mms_voice_fill_bottom),
                    TileMode.MIRROR));
        }
    }

    public void addToParent(ViewGroup viewGroup) {
        if (viewGroup != null && this.mParent == null) {
            this.isAddTo = true;
            viewGroup.addView(this, new LayoutParams(-1, -1));
            this.mParent = viewGroup;
        }
    }

    public void release() {
        if (this.mParent != null) {
            this.mParent.removeView(this);
            this.mParent = null;
            cancel();
        }
    }

    private void cancel() {
        if (this.WJ != null) {
            this.WJ.cancel();
            this.WJ = null;
        }
        if (this.diF != null) {
            this.diF.cancel();
            this.diF = null;
        }
    }

    public void start() {
        if (!this.play) {
            this.play = true;
            this.diJ = false;
            if (!(this.mThread == null || this.mThread.avr())) {
                this.mThread.fe(true);
            }
            this.mThread = new MyThread(this);
            this.mThread.start();
        }
    }

    public void stop() {
        if (this.play) {
            this.play = false;
            if (this.WJ != null) {
                this.WJ.cancel();
                this.WJ = null;
            }
            if (this.dir > 10.0f) {
                this.WJ = ValueAnimator.ofFloat(new float[]{this.dir, 10.0f});
                this.WJ.setDuration(this.diG);
                this.WJ.setInterpolator(this.mInterpolator);
                this.WJ.addUpdateListener(new My2AnimatorUpdateListener(this));
                this.WJ.start();
            }
            final int[] iArr = new int[]{0, -16777216, -16777216, -16777216, 0};
            final float[] fArr = new float[]{0.0f, 0.2f, 0.5f, 0.8f, 1.0f};
            this.diF = ValueAnimator.ofInt(new int[]{0, this.mWidth / 2});
            this.diF.setDuration(this.diH);
            this.diF.setInterpolator(new AccelerateInterpolator());
            this.diF.addUpdateListener(new AnimatorUpdateListener() {
                final VoiceSinWaveView diS = null;

                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    this.diS.diP = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    if (!this.diS.diJ && this.diS.diP > (this.diS.mWidth * 5) / 14) {
                        if (this.diS.callback != null) {
                            this.diS.callback.fadeToQuarter();
                        }
                        this.diS.diJ = true;
                    }
                    this.diS.cut.setShader(new LinearGradient((float) this.diS.diP, 0.0f, (float) (this.diS.mWidth - this.diS.diP), 0.0f, iArr, fArr, TileMode.MIRROR));
                }
            });
            this.diF.addListener(new MyAnimatorListenerAdapter(this));
            this.diF.start();
        }
    }

    public void updateStrength(float f) {
        long j = 100;
        if (this.play && f >= 0.0f && f <= 100.0f) {
            long currentTimeMillis = System.currentTimeMillis();
            if (0 == this.dis) {
                this.dis = currentTimeMillis - 100;
            }
            long j2 = currentTimeMillis - this.dis;
            if (j2 > 0) {
                j = j2;
            }
            if (this.WJ != null) {
                this.WJ.cancel();
                this.WJ = null;
            }
            Log.d(TAG, "updateStrength: " + f);
            this.WJ = ValueAnimator.ofFloat(new float[]{this.diq, f});
            this.WJ.setDuration(j);
            this.WJ.setInterpolator(this.mInterpolator);
            this.WJ.addUpdateListener(new MyAnimatorUpdateListener(this));
            this.WJ.start();
            this.dis = currentTimeMillis;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isAddTo) {
            if (this.mParent != null) {
                this.mWidth = this.mParent.getWidth();
                this.mHeight = this.mParent.getHeight();
            }
            if (this.mWidth > 0 && this.mHeight > 0) {
                if (this.mBitmap == null) {
                    this.mBitmap = Bitmap.createBitmap(this.mWidth, this.mHeight, Config.ARGB_8888);
//                    cur.a(this.mBitmap, new Throwable());
                    this.nCanvas = new Canvas(this.mBitmap);
                }
                setPaintShader();
                this.isAddTo = false;
            } else {
                return;
            }
        }
        if (this.play) {
            canvas.drawColor(0);
            canvas.drawPath(this.div, this.mCenterPaint);
            canvas.drawPath(this.diu, this.mSecondPaint);
            canvas.drawPath(this.dit, this.mPrimaryPaint);
            return;
        }
        int saveLayer = canvas.saveLayer(0.0f, 0.0f, (float) this.mWidth, (float) this.mHeight, null, Canvas.ALL_SAVE_FLAG);
        canvas.drawRect((float) this.diP, 0.0f, (float) (this.mWidth - this.diP), (float) this.mHeight, this.cut);
        this.cut.setXfermode(this.mpdf);
        if (this.mBitmap != null) {
            this.nCanvas.drawColor(0, Mode.CLEAR);
            this.nCanvas.drawPath(this.div, this.mCenterPaint);
            this.nCanvas.drawPath(this.diu, this.mSecondPaint);
            this.nCanvas.drawPath(this.dit, this.mPrimaryPaint);
            canvas.drawBitmap(this.mBitmap, 0.0f, 0.0f, this.cut);
        }
        this.cut.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }

    private void avp() {
        this.diD += this.diB;
        this.diE += this.diC;
        if (this.diD < -3.4028235E38f) {
            this.diD = 0.0f;
            this.diE = Cvd.RES_XXXDPI_MINISCALE;
        }
    }

    private void avq() {
        if (!this.isAddTo) {
            this.dix = (this.dir / 100.0f) * 0.8f;
            this.dix = Math.max(0.05f, this.dix);
            this.dit.rewind();
            this.diu.rewind();
            this.div.rewind();
            this.dit.moveTo(0.0f, m14206a(0, (float) this.mWidth, (float) this.mHeight, this.diw, this.dix, this.diz, this.diD));
            int i = 1;
            while (i <= this.mWidth) {
                this.dit.lineTo((float) i, m14206a(i, (float) this.mWidth, (float) this.mHeight, this.diw, this.dix, this.diz, this.diD));
                i += Utils.dip2px(getContext(), (float) this.diy);
            }
            this.dit.lineTo((float) this.mWidth, m14206a(this.mWidth, (float) this.mWidth, (float) this.mHeight, this.diw, this.dix, this.diz, this.diD));
            this.diu.moveTo((float) this.mWidth, m14206a(this.mWidth, (float) this.mWidth, (float) this.mHeight, this.diw, this.dix * 0.8f, this.diA, this.diE));
            i = this.mWidth - 1;
            while (i >= 0) {
                this.diu.lineTo((float) i, m14206a(i, (float) this.mWidth, (float) this.mHeight, this.diw, this.dix * 0.8f, this.diA, this.diE));
                i -= Utils.dip2px(getContext(), (float) this.diy);
            }
            this.diu.lineTo(0.0f, m14206a(0, (float) this.mWidth, (float) this.mHeight, this.diw, this.dix * 0.8f, this.diA, this.diE));
            this.div.addPath(this.dit);
            this.div.addPath(this.diu);
        }
    }

    private float m14206a(int i, float f, float f2, float f3, float f4, float f5, float f6) {
        return ((((1.0f - ((float) Math.pow((double) ((((float) (i * 2)) / f) - 1.0f), 2.0d))) * f3) * f4) * ((float) Math.sin(((6.283185307179586d * ((double) (((float) i) / f))) * ((double) f5)) + ((double) f6)))) + (0.5f * f2);
    }

    private void reset() {
        this.dir = 0.0f;
        this.diq = 0.0f;
        this.dis = 0;
        this.diD = 0.0f;
        this.diE = Cvd.RES_XXXDPI_MINISCALE;
        this.play = false;
        this.diJ = false;
        this.isAddTo = true;
        this.diP = 0;
        if (this.mBitmap != null) {
            this.mBitmap.recycle();
            this.mBitmap = null;
            this.nCanvas = null;
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mThread != null) {
            this.mThread.fe(true);
        }
        if (this.mHandler != null) {
            this.mHandler.removeMessages(4097);
        }
        reset();
    }
}