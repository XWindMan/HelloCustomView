package com.windman.hellocustomview.tabWave;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.windman.hellocustomview.R;

public class Wave1Fragment extends Fragment {
    private int mBorderColor = Color.parseColor("#44FFFFFF");
    private int mBorderWidth = 10;
    private WaveHelper mWaveHelper;
    private WaveView mWaveView;

    public Wave1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wave1, container, false);
        mWaveView = (WaveView) view.findViewById(R.id.wave_view);
        mWaveHelper = new WaveHelper(mWaveView);
        mWaveView.setWaveColor(
                Color.parseColor("#2887CEFA"),
                Color.parseColor("#3C7B68EE"),
                Color.parseColor("#4DB0C4DE"));
        mWaveView.setProgress(125);
        mBorderColor = Color.parseColor("#B0b7d28d");
        mWaveView.setBorder(mBorderWidth, mBorderColor);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mWaveHelper.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWaveHelper.cancel();
    }
}
