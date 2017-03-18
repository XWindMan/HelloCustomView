package com.windman.hellocustomview.expandView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import com.windman.hellocustomview.R;

/**
 * Created by WindMan on 2017/1/3.
 */

public class ExpandView extends FrameLayout {

    private Animation mExpandAnimation;
    private Animation mCollapseAnimation;
    private boolean mIsExpand;

    public ExpandView(Context context) {
        this(context, null);
    }

    public ExpandView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initExpandView();
    }

    private void initExpandView() {
        mExpandAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.expand);
        mCollapseAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.collapse);
    }

    public void collapse() {
        if (mIsExpand) {
            mIsExpand = false;
            clearAnimation();
            startAnimation(mCollapseAnimation);
            setVisibility(View.INVISIBLE);

        }
    }

    public void expand() {
        if (!mIsExpand) {
            mIsExpand = true;
            clearAnimation();
            startAnimation(mExpandAnimation);
            setVisibility(View.VISIBLE);
        }
    }

    public boolean isExpand() {
        return mIsExpand;
    }

    public void setContentView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_expand, null);
        removeAllViews();
        addView(view);
    }
}
