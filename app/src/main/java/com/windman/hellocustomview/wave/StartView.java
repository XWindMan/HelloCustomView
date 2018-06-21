package com.windman.hellocustomview.wave;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created  on 2018/5/10/010.
 * 描述：
 */

public class StartView extends View {

    private StartHelper helper;
    private float radius = 20;
    private Path path;
    private Paint paint, paintText;
    private float marginLeft, marginTop;
    private List<StartHelper.XPoint> pointList;
    private int justOne = 1;
    private float mWidth, mHeight;

    public StartView(Context context) {
        this(context, null);
    }

    public StartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        helper = new StartHelper();
        path = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ff0000"));

        paintText = new Paint();
        paintText.setTextSize(30);
        paintText.setColor(Color.parseColor("#00ff00"));
        pointList = new ArrayList<>();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        float cx = w / 2f, cy = h / 2f;
        marginLeft = 20;
        radius = (w - marginLeft * 2) / 2f;
        marginTop = h / 2f - radius;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radian = (float) Math.toRadians(36);// 36为五角星的角度
        float radius_in = (float) (radius * Math.sin(radian / 2) / Math
                .cos(radian)); // 中间五边形的半径

        /*// A
        path.moveTo((float) (radius * Math.cos(radian / 2)), 0);// 此点为多边形的起点
        // A1
        path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in
                        * Math.sin(radian)),
                (float) (radius - radius * Math.sin(radian / 2)));
        // B
        path.lineTo((float) (radius * Math.cos(radian / 2) * 2),
                (float) (radius - radius * Math.sin(radian / 2)));
        //B1
        path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in
                        * Math.cos(radian / 2)),
                (float) (radius + radius_in * Math.sin(radian / 2)));
        // C
        path.lineTo(
                (float) (radius * Math.cos(radian / 2) + radius
                        * Math.sin(radian)), (float) (radius + radius
                        * Math.cos(radian)));
        // C1
        path.lineTo((float) (radius * Math.cos(radian / 2)),
                (float) (radius + radius_in));
        path.lineTo(
                (float) (radius * Math.cos(radian / 2) - radius
                        * Math.sin(radian)), (float) (radius + radius
                        * Math.cos(radian)));
        // D
        path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in
                        * Math.cos(radian / 2)),
                (float) (radius + radius_in * Math.sin(radian / 2)));
        // D1
        path.lineTo(0, (float) (radius - radius * Math.sin(radian / 2)));
        // E
        path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in
                        * Math.sin(radian)),
                (float) (radius - radius * Math.sin(radian / 2))); */

        // A
        float ax = (float) (radius * Math.cos(radian / 2)) + marginLeft;
        float ay = 0 + marginTop;
        path.moveTo(ax, ay);// 此点为多边形的起点
        canvas.drawText("A", ax, ay, paintText);

        // C
        float cx = (float) (radius * Math.cos(radian / 2) + radius
                * Math.sin(radian)) + marginLeft;
        float cy = (float) (radius + radius
                * Math.cos(radian)) + marginTop;
        path.lineTo(cx, cy);
        canvas.drawText("C", cx, cy, paintText);

        //E
        float ex = 0 + marginLeft;
        float ey = (float) (radius - radius * Math.sin(radian / 2)) + marginTop;
        path.lineTo(ex, ey);
        canvas.drawText("E", ex, ey, paintText);

        // B
        float bx = (float) (radius * Math.cos(radian / 2) * 2) + marginLeft;
        float by = (float) (radius - radius * Math.sin(radian / 2)) + marginTop;
        path.lineTo(bx, by);
        canvas.drawText("B", bx, by, paintText);

        // D
        float dx = (float) (radius * Math.cos(radian / 2) - radius
                * Math.sin(radian)) + marginLeft;
        float dy = (float) (radius + radius
                * Math.cos(radian)) + marginTop;
        if (justOne == 1) {
            pointList.add(new StartHelper.XPoint(ex, ey));//2
            pointList.add(new StartHelper.XPoint(bx, by));//3
            pointList.add(new StartHelper.XPoint(dx, dy));//4
            pointList.add(new StartHelper.XPoint(ax, ay));//0
            pointList.add(new StartHelper.XPoint(cx, cy));//1
            helper.setPoints(pointList, mWidth, mHeight);
            justOne = 9;
        }


        path.lineTo(dx, dy);
        canvas.drawText("D", dx, dy, paintText);
        path.close();// 使这些点构成封闭的多边形
        canvas.drawPath(path, paint);
    }


}
