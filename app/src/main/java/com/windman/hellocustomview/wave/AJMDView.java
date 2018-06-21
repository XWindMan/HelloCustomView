package com.windman.hellocustomview.wave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

public class AJMDView extends View {

    private Paint mPaint;
    private float mWidth, mHeight;
    private List<XPoint> pointList;
    private List<XPoint> nullPoint;

    public AJMDView(Context context) {
        this(context, null);
    }

    public AJMDView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AJMDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public AJMDView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);

        pointList = new ArrayList<>();
        nullPoint = new ArrayList<>();
    }

    private int first = 1;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (first > 1) return;

        float t = 0;
        float x = 0;
        while (x <= mWidth && x >= 0) {
            x = (float) (t * Math.cos(3 * t * Math.PI / 180)) + mWidth / 2;
            float y = (float) (t * Math.sin(3 * t * Math.PI / 180)) + mHeight / 2;
            t += 2;
            pointList.add(new XPoint(new BigDecimal(x / mWidth).setScale(3, RoundingMode.HALF_UP).floatValue()
                    , new BigDecimal(y / mHeight).setScale(3, RoundingMode.HALF_UP).floatValue()));
            canvas.drawPoint(x, y, mPaint);
        }
        while (t>0){
            x = (float) (t * Math.cos(3 * t * Math.PI / 180)) + mWidth / 2;
            float y = (float) (t * Math.sin(3 * t * Math.PI / 180)) + mHeight / 2;
            t -= 2;
            pointList.add(new XPoint(new BigDecimal(x / mWidth).setScale(3, RoundingMode.HALF_UP).floatValue()
                    , new BigDecimal(y / mHeight).setScale(3, RoundingMode.HALF_UP).floatValue()));
            canvas.drawPoint(x, y, mPaint);
        }
        Data data = new Data();
        data.setChannel0(pointList);
        for (int i = 0; i < pointList.size(); i++) {
            nullPoint.add(new XPoint(-1, -1));
        }
        data.setChannel1(nullPoint);
        data.setChannel2(nullPoint);
        data.setChannel3(nullPoint);
        data.setChannel4(nullPoint);

        Guest gesture = new Guest();
        gesture.setCreatTime("");
        gesture.setDataID("");
        gesture.setName("agmd");
        gesture.setUuID("");
        gesture.setUserID("");
        gesture.setData(data);

        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
                File saveFile = new File(sdCardDir, "agmd.json");
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


//    public  void fuck(){
//        int nPoints = 1000;
//        double r1 = 60;
//        double r2 = 50;
//        double p = 70;
//        float x1 = (int) (r1 + r2 - p);
//        float y1 = 0;
//
//        for (int i = 0; i < nPoints; i++) {
//            double t = i * Math.PI / 90;
//            x1 = (int) ((r1 + r2) * Math.cos(t) - p * Math.cos((r1 + r2) * t / r2));
//            y1 = (int) ((r1 + r2) * Math.sin(t) - p * Math.sin((r1 + r2) * t / r2));
//            canvas.drawPoint(x1, y1, mPaint);
//        }
//    }
}
