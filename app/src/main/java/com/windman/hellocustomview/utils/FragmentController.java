package com.windman.hellocustomview.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class FragmentController {

    private static final String TAG = "FragmentController";

    public static final int Tab_ControlFragment = 0;
    public static final int Tab_FoundFragment = 1;
    public static final int Tab_SomkeSettingFragment = 2;
    public static final int Tab_SystemSettingFragment = 3;

    private int containerId;
    private ArrayList<Fragment> fragments;
    private FragmentManager fm;
    private static FragmentController controller;

    private FragmentController(int containerId, FragmentActivity activity) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    public static FragmentController getInstance(int containerId, FragmentActivity activity) {
        if (controller == null) {
            controller = new FragmentController(containerId, activity);
        }
        return controller;
    }

    private void initFragment() {
        fragments = new ArrayList<Fragment>();
//        fragments.add(new ControlFragment());
//        fragments.add(new FoundFragment());
//        fragments.add(new SomkeSettingFragment());
//        fragments.add(new SystemSettingFragment());
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragment();
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragments.get(position));
        ft.commitAllowingStateLoss();
    }

    public void hideFragment() {
        FragmentTransaction ft = fm.beginTransaction();
        for (Fragment fragment : fragments) {
            if (fragment != null)
                ft.hide(fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }

    public static void release() {
        controller = null;
    }
}
