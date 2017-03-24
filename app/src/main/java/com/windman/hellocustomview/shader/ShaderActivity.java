package com.windman.hellocustomview.shader;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.windman.hellocustomview.R;

/**
 * Created by ${WindMan} on 2017/1/18.
 */

public class ShaderActivity extends Activity {
    private static final int MSG_UPDATE = 0;

    SweepGradientView sweepGradientView;
    private int degerees = 0;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_UPDATE && degerees < 250) {
                degerees++;
                sweepGradientView.setDegrees(degerees);
                handler.sendEmptyMessageDelayed(MSG_UPDATE, 10);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shader);
        sweepGradientView = (SweepGradientView) findViewById(R.id.sgv);
        handler.sendEmptyMessage(MSG_UPDATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler.hasMessages(MSG_UPDATE)) {
            handler.removeMessages(MSG_UPDATE);
        }
    }
}
