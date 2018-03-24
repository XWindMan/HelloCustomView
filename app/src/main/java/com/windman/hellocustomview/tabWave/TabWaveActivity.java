package com.windman.hellocustomview.tabWave;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.windman.hellocustomview.R;

import java.util.ArrayList;
import java.util.List;

public class TabWaveActivity extends AppCompatActivity implements TranslateTab.onTabClickListener {
    private TranslateTab mTab;
    private FragmentTransaction mTransaction;
    private Fragment mWaveFragment, mCircleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_wave);
        mTab = (TranslateTab) findViewById(R.id.tab);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            list.add(" tab " + i);
        }
        mTab.setTextList(list);
        mTab.setOnTabClickListener(this);
        mWaveFragment = new Wave1Fragment();
        mCircleFragment = new CircleSeekbarFragment();
        switchFragment(mWaveFragment);
    }

    @Override
    public void onTabClick(int position) {
        switch (position) {
            case 0:
                switchFragment(mWaveFragment);
                break;
            case 1:
                switchFragment(mCircleFragment);
                break;
        }
    }

    private void switchFragment(Fragment fragment) {
        mTransaction = getSupportFragmentManager().beginTransaction();
        mTransaction.replace(R.id.conten, fragment);
        mTransaction.commitAllowingStateLoss();
    }
}
