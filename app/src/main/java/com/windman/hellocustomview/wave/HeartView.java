package com.windman.hellocustomview.wave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2018/5/12/012.
 * 描述：
 */

public class HeartView extends View {

    private float mWidth, mHeight;
    private float mUnitWidth, mUnitHeight;
    private Paint mPaint;
    private Path mPath;
    private float offsetX;
    private float offsetY;

    private List<XPoint> pointList0;
    private List<XPoint> pointList1;
    private List<XPoint> nullPoint;

    public HeartView(Context context) {
        this(context, null);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HeartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        mPath = new Path();

        pointList0 = new ArrayList<>();
        pointList1 = new ArrayList<>();
        nullPoint = new ArrayList<>();
    }

    private int first = 1;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        offsetX = mWidth / 2;
        offsetY = mHeight / 2 - 55;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (first > 1) return;
        Data data = new Data();
        for (float i = 0.5f; i < 3; i++) {
            float angle = 20;
            offsetY = mHeight / 3 * i;
            mPaint.setColor(Color.parseColor("#ff0000"));
            while (angle >= 10) {
                Point p = getHeartPoint(angle);
                canvas.drawPoint(p.x, p.y, mPaint);
                pointList0.add(new XPoint(new BigDecimal(p.x / mWidth).setScale(3, RoundingMode.HALF_UP).floatValue(),
                        new BigDecimal(p.y / mHeight).setScale(3, RoundingMode.HALF_UP).floatValue()));
                angle = angle - 0.2f;
            }
            angle = 20;
            mPaint.setColor(Color.parseColor("#00ff00"));
            while (angle < 30) {
                Point p = getHeartPoint(angle);
                canvas.drawPoint(p.x, p.y, mPaint);
                pointList1.add(new XPoint(new BigDecimal(p.x / mWidth).setScale(3, RoundingMode.HALF_UP).floatValue(),
                        new BigDecimal(p.y / mHeight).setScale(3, RoundingMode.HALF_UP).floatValue()));
                angle = angle + 0.2f;
            }
        }
        data.setChannel0(pointList0);
        data.setChannel1(pointList1);
        for (int i = 0; i < pointList0.size(); i++) {
            nullPoint.add(new XPoint(-1, -1));
        }
        data.setChannel2(nullPoint);
        data.setChannel3(nullPoint);
        data.setChannel4(nullPoint);

        Guest gesture = new Guest();
        gesture.setCreatTime("");
        gesture.setDataID("");
        gesture.setName("heart_p");
        gesture.setUuID("");
        gesture.setUserID("");
        gesture.setData(data);

        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
                File saveFile = new File(sdCardDir, "heart_p.json");
                FileOutputStream outStream = new FileOutputStream(saveFile);
                outStream.write((new Gson().toJson(gesture)).getBytes());
                outStream.close();
                Log.d("AAAAAA", "setPoints: 写入完毕");
            } else {
                Log.d("AAAAAA", "setPoints: 写入出错");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        first = 2;
    }

    public Point getHeartPoint(float angle) {
        float t = (float) (angle / Math.PI);
        float x = (float) (15 * (16 * Math.pow(Math.sin(t), 3)));
        float y = (float) (-15 * (13 * Math.cos(t) - 5 * Math.cos(2 * t) - 2 * Math.cos(3 * t) - Math.cos(4 * t)));
        return new Point(offsetX + (int) x, offsetY + (int) y);
    }

    static class Guest {
        private String creatTime;
        private String userID;
        private String name;
        private String uuID;
        private String dataID;
        private Data data;

        public void setCreatTime(String creatTime) {
            this.creatTime = creatTime;
        }

        public void setUserID(String userID) {
            this.userID = userID;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setUuID(String uuID) {
            this.uuID = uuID;
        }

        public void setDataID(String dataID) {
            this.dataID = dataID;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }

    static class XPoint {
        private float X, Y, r;

        public XPoint(float x, float y) {
            X = x;
            Y = y;
            this.r = 36;
        }

        public float getX() {
            return X;
        }

        public void setX(float x) {
            X = x;
        }

        public float getY() {
            return Y;
        }

        public void setY(float y) {
            Y = y;
        }

        public float getR() {
            return r;
        }

        public void setR(float r) {
            this.r = r;
        }
    }

    static class Data {
        @SerializedName("channel1")
        public List<XPoint> channel1;

        @SerializedName("channel0")
        public List<XPoint> channel0;

        @SerializedName("channel3")
        public List<XPoint> channel3;

        @SerializedName("channel2")
        public List<XPoint> channel2;

        @SerializedName("channel4")
        public List<XPoint> channel4;

        public List<XPoint> getChannel1() {
            return channel1;
        }

        public void setChannel1(List<XPoint> channel1) {
            this.channel1 = channel1;
        }

        public List<XPoint> getChannel0() {
            return channel0;
        }

        public void setChannel0(List<XPoint> channel0) {
            this.channel0 = channel0;
        }

        public List<XPoint> getChannel3() {
            return channel3;
        }

        public void setChannel3(List<XPoint> channel3) {
            this.channel3 = channel3;
        }

        public List<XPoint> getChannel2() {
            return channel2;
        }

        public void setChannel2(List<XPoint> channel2) {
            this.channel2 = channel2;
        }

        public List<XPoint> getChannel4() {
            return channel4;
        }

        public void setChannel4(List<XPoint> channel4) {
            this.channel4 = channel4;
        }
    }

    static class Point {
        float x;
        float y;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }
}
