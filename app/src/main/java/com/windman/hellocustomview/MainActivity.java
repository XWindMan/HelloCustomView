package com.windman.hellocustomview;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.windman.hellocustomview.ChangeAvatar.PicAlertDialog;
import com.windman.hellocustomview.editDialog.EditDialog;
import com.windman.hellocustomview.expandView.ExpandActivity;

public class MainActivity extends AppCompatActivity {
    private PicAlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView textView = (TextView) findViewById(R.id.textview);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditDialog editDialog = new EditDialog(MainActivity.this);
                editDialog.show();
                editDialog.setOnEditClickListener(new EditDialog.EditClickListener() {
                    @Override
                    public void onOKClik(String text) {
                        if (!TextUtils.isEmpty(text)) {
                            textView.setText(text);
                        }
                    }
                });
            }
        });
        //  PicAlertDialog
        CardView cardView = (CardView) findViewById(R.id.showDialog);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPicDialog();
            }
        });
        // expand
        CardView expandView = (CardView) findViewById(R.id.showExpand);
        expandView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExpandActivity.class));
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
