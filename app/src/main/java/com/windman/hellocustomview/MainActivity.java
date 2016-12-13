package com.windman.hellocustomview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
    }
}
