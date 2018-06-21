package com.windman.hellocustomview.base;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import com.windman.hellocustomview.ChangeAvatar.PicAlertDialog;
import com.windman.hellocustomview.R;
import com.windman.hellocustomview.RoateLayout.RotateActivity;
import com.windman.hellocustomview.blogs.ZhangHongYang.sticky_recyerview.StickyRecyclerViewActivity;
import com.windman.hellocustomview.circleView.CircleSeekBarActivity;
import com.windman.hellocustomview.editDialog.EditDialog;
import com.windman.hellocustomview.expandFragment.ExpandFragmentActivity;
import com.windman.hellocustomview.expandView.ExpandActivity;
import com.windman.hellocustomview.location.GetLocationActivity;
import com.windman.hellocustomview.moveListView.MoveListActivity;
import com.windman.hellocustomview.paintView.PaintViewActivity;
import com.windman.hellocustomview.privatePolicyTextView.PrivatePolicyActivity;
import com.windman.hellocustomview.shader.ShaderActivity;
import com.windman.hellocustomview.utils.LoadingDailog;
import com.windman.hellocustomview.wave.WaveActivity;

public class MainActivity extends AppCompatActivity {
    private PicAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.to_editdialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDialog editDialog = new EditDialog(MainActivity.this);
                editDialog.show();
                editDialog.setOnEditClickListener(new EditDialog.EditClickListener() {
                    @Override
                    public void onOKClik(String text) {
                        if (!TextUtils.isEmpty(text)) {
//                            textView.setText(text);
                        }
                    }
                });
            }
        });
        //  PicAlertDialog
        findViewById(R.id.showDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showPicDialog();
                LoadingDailog.Builder loadBuilder=new LoadingDailog.Builder(MainActivity.this)
                        .setMessage("加载中...")
                        .setCancelable(true)
                        .setCancelOutside(true);
                LoadingDailog dialog=loadBuilder.create();
                dialog.show();
            }
        });
        // expand
        findViewById(R.id.showExpand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExpandActivity.class));
            }
        });
        // paintview
        findViewById(R.id.to_paintview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PaintViewActivity.class));
            }
        });
        // movelistview
        findViewById(R.id.to_movelist).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MoveListActivity.class));
            }
        });
        // privatepolicy
        findViewById(R.id.to_privatepolicy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, PrivatePolicyActivity.class));
            }
        });
        // rotatelayout
        findViewById(R.id.to_roatelayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RotateActivity.class));
            }
        });
        // expand
        findViewById(R.id.to_shader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShaderActivity.class));
            }
        });
        // location
        findViewById(R.id.to_location).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        GetLocationActivity.class));
            }
        });
        // expand fragment
        findViewById(R.id.to_expand_fragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        ExpandFragmentActivity.class));
            }
        });

        findViewById(R.id.to_circleseekbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        WaveActivity.class));
            }
        });
        findViewById(R.id.to_stickyview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        StickyRecyclerViewActivity.class));
            }
        });
    }

    private void showPicDialog() {
        mDialog = new PicAlertDialog(this);
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(Color.parseColor("#00000000"));
        window.setBackgroundDrawable(colorDrawable);
        mDialog.show();
    }
}
