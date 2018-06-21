package com.windman.hellocustomview.wave;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.windman.hellocustomview.R;

public class WaveActivity extends AppCompatActivity implements
        SeekBar.OnSeekBarChangeListener, VoiceSinWaveView.IFade {
    private WaveView waveView;
    private WaveView2 waveView2;
    private LinearLayout conten;
    private VoiceSinWaveView voiceSinWaveView;

    private int[] data = new int[]{20, 0, 60, 0, 40, 0, 80, 40, 20, 90, 0, 10, 0, 60, 0, 50, 0, 50,
            40, 20, 100, 0};
    private int index = 0;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 2) {
                handler.sendEmptyMessageDelayed(2, 16);
                waveView2.start();
            }
            if (msg.what == 3) {
                handler.sendEmptyMessageDelayed(3, 250);
                if (index == data.length - 1) {
                    index = 0;
                }
                for (int i = 0; i < data.length; i++) {
                    voiceSinWaveView.updateStrength(data[i]);
                    index++;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave);
        SeekBar seekBar = (SeekBar) findViewById(R.id.seekbar);
        waveView = (WaveView) findViewById(R.id.wave_view);
        waveView2 = (WaveView2) findViewById(R.id.wave_view2);
        seekBar.setOnSeekBarChangeListener(this);
        conten = (LinearLayout) findViewById(R.id.conten);
        voiceSinWaveView = new VoiceSinWaveView(this);
        voiceSinWaveView.addToParent(conten);
        voiceSinWaveView.setCallBack(this);
        voiceSinWaveView.start();
        handler.sendEmptyMessage(2);
        handler.sendEmptyMessage(3);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        waveView.setFactor(progress / 100f);
        waveView2.setFactor(progress / 100f);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void fadeOut() {
        if (voiceSinWaveView != null) {
            voiceSinWaveView.release();
        }
    }

    @Override
    public void fadeToQuarter() {

    }
}
