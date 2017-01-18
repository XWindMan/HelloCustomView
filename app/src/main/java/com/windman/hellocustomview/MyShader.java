package com.windman.hellocustomview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ${WindMan} on 2017/1/18.
 */

public class MyShader extends View {
    public MyShader(Context context) {
        this(context, null);
    }

    public MyShader(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(400, 400);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int weight = getWidth();
        int radius = weight > height ? height / 2 : weight / 2;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.beauty);
        // 以目标宽高创建一个缩放过的图片
//        Bitmap result = Bitmap.createScaledBitmap(bitmap, weight, height, false);
        // 平铺模式：clamp:当绘制范围超过原图后，剩余部分会以边缘颜色延伸
//        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // mirror:镜子，对称显示（）
//        Shader shader = new BitmapShader(bitmap, Shader.TileMode.MIRROR, Shader.TileMode.MIRROR);
        // repeat：平铺
        Shader shader = new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        Paint paint = new Paint();
        paint.setShader(shader);
        paint.setTextSize(200f);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
//        canvas.drawRect(0, 0, weight, height, paint);
//        canvas.drawCircle(weight / 2, height / 2, radius, paint);
        canvas.drawText("美女",0, height / 2, paint);

    }
}
