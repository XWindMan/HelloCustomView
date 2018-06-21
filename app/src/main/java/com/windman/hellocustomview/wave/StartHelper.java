package com.windman.hellocustomview.wave;

import android.gesture.Gesture;
import android.os.Environment;
import android.util.Log;

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
 * Created  on 2018/5/10/010.
 * 描述：
 */

public class StartHelper {


    private List<List<XPoint>> dataList = new ArrayList<>();
    private int index = 0;
    private List<XPoint> pointList = new ArrayList<>();

    public void setPoints(List<XPoint> points, float w, float h) {
        XPoint p1, p2;
        for (int i = 0; i < points.size(); i++) {
            p1 = points.get(i);
            if (i == 4) {
                p2 = points.get(0);
            } else {
                p2 = points.get(i + 1);
            }
            float k = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
            float b = p2.getY() - k * p2.getX();
//            float fx = k * x + b;
            float minX = Math.min(p1.getX(), p2.getX());
            float maxX = Math.max(p1.getX(), p2.getX());
            float x1 = p1.getX();
            float x2 = p2.getX();
            float y1 = p1.getY();
            float y2 = p2.getY();
            float x = x1;
            while (x >= minX && x <= maxX) {
                float fx = k * x + b;
                pointList.add(new XPoint(new BigDecimal(x / w).setScale(3, RoundingMode.HALF_UP).floatValue(),
                        new BigDecimal(fx / h).setScale(3, RoundingMode.HALF_UP).floatValue()));
                x += (x2 - x1) / 30;
            }
        }
        List<XPoint> nullPoint = new ArrayList<>();
        for (int i = 0; i < pointList.size(); i++) {
            nullPoint.add(new XPoint(-1, -1));
        }
        Data data = new Data();
        data.setChannel0(pointList);
        data.setChannel1(nullPoint);
        data.setChannel2(nullPoint);
        data.setChannel3(nullPoint);
        data.setChannel4(nullPoint);
        Guest gesture = new Guest();
        gesture.setCreatTime("");
        gesture.setDataID("");
        gesture.setName("star");
        gesture.setUuID("");
        gesture.setUserID("");
        gesture.setData(data);
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录
                File saveFile = new File(sdCardDir, "star4.json");
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
        Log.d("AAAAAA", "setPoints: " + (new Gson().toJson(data)));
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
}
