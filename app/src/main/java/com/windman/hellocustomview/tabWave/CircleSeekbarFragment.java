package com.windman.hellocustomview.tabWave;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windman.hellocustomview.R;


public class CircleSeekbarFragment extends Fragment {
    public CircleSeekbarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_circle_seekbar, container, false);
    }

}
