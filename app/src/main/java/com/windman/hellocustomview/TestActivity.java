package com.windman.hellocustomview;

import android.app.Activity;
import android.os.Bundle;

import com.windman.hellocustomview.sectionProgressBar.SectionProgressBar;

/**
 * Created by WindMan on 2017/1/5.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        SectionProgressBar spb = (SectionProgressBar) findViewById(R.id.test_spb1);
        spb.setDataStrength(new Float[]{0f, 18.5f, 25f, 30f, 35f, 45f})
                .setDataText(new String[]{"偏瘦", "正常", "偏胖", "肥胖", "极度肥胖"})
                .refresh();
    }
}
