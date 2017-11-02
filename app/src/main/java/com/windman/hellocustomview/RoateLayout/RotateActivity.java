package com.windman.hellocustomview.RoateLayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.windman.hellocustomview.R;

public class RotateActivity extends Activity {
    private static final String TAG = "RotateActivity";
    //布局1
    private TextView tvOne;
    private Button btnRotateNext;
    //布局2
    private TextView tvTwo;
    private Button btnRotateBack;


    //页面翻转容器FrameLayout
    private FrameLayout flContainer;
    //布局1界面RelativeLayout
    private RelativeLayout relativeLayout1;
    //布局2界面RelativeLayout
    private RelativeLayout relativeLayout2;
    //初始化界面索引（1位布局1,2位布局2）
    private int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_rotate);
        initView();
        registerClickListener();
    }

    private void initView() {
        tvOne = (TextView) findViewById(R.id.tv_one);
        tvTwo = (TextView) findViewById(R.id.tv_two);
        flContainer =
                (FrameLayout) findViewById(R.id.frame_layout);
        relativeLayout1 = (RelativeLayout) findViewById(R.id.relative_layout_one);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relative_layout_two);

        btnRotateNext = (Button) findViewById(R.id.rotate_next);
        btnRotateBack = (Button) findViewById(R.id.rotate_back);
    }

    private void registerClickListener() {
        tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RotateActivity.this, "one", Toast.LENGTH_SHORT).show();
                ;
            }
        });

        tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(RotateActivity.this, "two", Toast.LENGTH_SHORT).show();
                ;
            }
        });
        btnRotateNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: index=" + index);
                if (index == 1) {
                    //第一阶段翻转
                    applyRotation(1, 0, 90);
                    index = 2;
                } else {
                    //第一阶段翻转
//                    applyRotation(0, 0, -90);
//                    index = 1;
                }
            }
        });
        btnRotateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: index=" + index);
                if (index == 1) {
                    //第一阶段翻转
//                    applyRotation(1, 0, 90);
//                    index = 2;
                } else {
                    //第一阶段翻转
                    applyRotation(0, 0, -90);
                    index = 1;
                }
            }
        });
    }


    /**
     * 执行翻转第一阶段翻转动画
     *
     * @param tag   view索引
     * @param start 起始角度
     * @param end   结束角度
     */
    private void applyRotation(int tag, float start, float end) {
        // 得到中心点(以中心翻转)
        //X轴中心点
        final float centerX = flContainer.getWidth() / 2.0f;
        //Y轴中心点
        final float centerY = flContainer.getHeight() / 2.0f;
        //Z轴中心点
        final float depthZ = 500.0f;
        // 根据参数创建一个新的三维动画,并且监听触发下一个动画
        final Rotate3D rotation = new Rotate3D(start, end, centerX, centerY, depthZ, true);

        rotation.setAnimationListener(new DisplayNextView(tag));//设置第一阶段动画监听器
        flContainer.startAnimation(rotation);


    }

    /**
     * 第一阶段动画监听器
     */
    private final class DisplayNextView implements Animation.AnimationListener {
        private final int tag;

        private DisplayNextView(int tag) {
            this.tag = tag;
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            //第一阶段动画结束时，也就是整个Activity垂直于手机屏幕，
            //执行第二阶段动画
            flContainer.post(new SwapViews(tag));
            //调整两个界面各自的visibility
            adjustVisiable();
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    /**
     * 执行翻转第二个阶段动画
     */
    private final class SwapViews implements Runnable {
        private final int tag;

        public SwapViews(int position) {
            tag = position;
        }

        public void run() {
            if (tag == 0) {
                //首页页面以90~0度翻转
                showView(relativeLayout1, relativeLayout2, 90, 0);
            } else if (tag == 1) {
                //音乐页面以-90~0度翻转
                showView(relativeLayout2, relativeLayout1, -90, 0);
            }
        }
    }

    /**
     * 显示第二个视图动画
     *
     * @param showView    要显示的视图
     * @param hiddenView  要隐藏的视图
     * @param startDegree 开始角度
     * @param endDegree   目标角度
     */
    private void showView(RelativeLayout showView, RelativeLayout hiddenView, int startDegree, int endDegree) {
        //同样以中心点进行翻转
        float centerX = showView.getWidth() / 2.0f;
        float centerY = showView.getHeight() / 2.0f;
        float centerZ = 500.0f;
        if (centerX == 0 || centerY == 0) {
            //调用该方法getMeasuredWidth()，必须先执行measure()方法，否则会出异常。
            showView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            //获取该view在父容器里面占的大小
            centerX = showView.getMeasuredWidth() / 2.0f;
            centerY = showView.getMeasuredHeight() / 2.0f;
        }
//        Log.e("centerX",centerX + "");
//        Log.e("centerY",centerY + "");
        hiddenView.setVisibility(View.GONE);
        showView.setVisibility(View.VISIBLE);
        Rotate3D rotation = new Rotate3D(startDegree, endDegree, centerX, centerY, centerZ, false);
        rotation.setDuration(300);//设置动画持续时间
        rotation.setInterpolator(new DecelerateInterpolator());//设置动画变化速度
        flContainer.startAnimation(rotation);
    }

    /**
     * 两个布局的visibility调节
     */

    private void adjustVisiable() {
        if (tvOne.getVisibility() == View.VISIBLE) {
            tvOne.setVisibility(View.GONE);
        } else {
            tvOne.setVisibility(View.VISIBLE);
        }
        if (tvTwo.getVisibility() == View.VISIBLE) {
            tvTwo.setVisibility(View.GONE);
        } else {
            tvTwo.setVisibility(View.VISIBLE);
        }
    }
}