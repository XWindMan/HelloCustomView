package com.windman.hellocustomview.privatePolicyTextView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.windman.hellocustomview.R;

public class PrivatePolicyActivity extends AppCompatActivity {

    private PrivatePolicyTextView tv;

    private static final String myGithub = "https://github.com/XWindMan";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private_policy);
        tv = (PrivatePolicyTextView) findViewById(R.id.ppt);
        tv.setOnPrivatePolocyClickListener(new PrivatePolicyTextView.onPrivatePolicyClickListener() {
            @Override
            public void onClick() {
                Uri uri = Uri.parse(myGithub);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }
}
