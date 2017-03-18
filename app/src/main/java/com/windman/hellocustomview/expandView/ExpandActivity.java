package com.windman.hellocustomview.expandView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.windman.hellocustomview.R;
import com.windman.hellocustomview.sectionProgressBar.SectionProgressBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WindMan on 2017/1/3.
 */

public class ExpandActivity extends Activity {
    private ExpandableListView mExpandView;
    private LinearLayout mLinearLayout;
    private TextView mTextView;
    private ImageView mImageView;
    private ListView mListView;
    private SectionProgressBar spb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
//        initExpandView();
        mExpandView = (ExpandableListView) findViewById(R.id.expanded_listviewL);
        mExpandView.setAdapter(new ExpandAdapter(this,getParentData(),getChildData()));
    }

    private List<ParentBean> getParentData() {
        List<ParentBean> partentList = new ArrayList<>();
        ParentBean p1 = new ParentBean();
        p1.setTitle("Java");

        ParentBean p2 = new ParentBean();
        p2.setTitle("H5");

        ParentBean p3 = new ParentBean();
        p3.setTitle("Python");

        ParentBean p4 = new ParentBean();
        p4.setTitle("Linux");

        partentList.add(p1);
        partentList.add(p2);
        partentList.add(p3);
        partentList.add(p4);
        return partentList;
    }

    private List<List<ChildBean>> getChildData() {
        List<List<ChildBean>> list = new ArrayList<>();
        List<ChildBean> list1 = new ArrayList<>();

        ChildBean c1 = new ChildBean();
        c1.setDes("java  java   java");
        c1.setImageId(R.mipmap.beauty);
        list1.add(c1);

        List<ChildBean> list2 = new ArrayList<>();
        ChildBean c2 = new ChildBean();
        c2.setDes("html  css   div");
        c2.setImageId(R.mipmap.beauty);
        list2.add(c2);

        List<ChildBean> list3 = new ArrayList<>();
        ChildBean c3 = new ChildBean();
        c3.setDes("python  python   python");
        c3.setImageId(R.mipmap.beauty);
        list3.add(c3);

        List<ChildBean> list4 = new ArrayList<>();
        ChildBean c4 = new ChildBean();
        c4.setDes("linux  linux   linux");
        c4.setImageId(R.mipmap.beauty);
        list4.add(c4);

        list.add(list1);
        list.add(list2);
        list.add(list3);
        list.add(list4);
        return list;
    }
/*
    public void initExpandView() {
        mLinearLayout = (LinearLayout) findViewById(R.id.layout_title);
        mTextView = (TextView) findViewById(R.id.textview_title);
        mImageView = (ImageView) findViewById(R.id.imageview_state);
//        mExpandView = (ExpandView) findViewById(R.id.expandView);
        spb = (SectionProgressBar) findViewById(R.id.spb);
//        mExpandView.setContentView();
        mLinearLayout.setClickable(true);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (spb.isExpand()) {
                    spb.collapse();
                    mTextView.setText("点击向下展开");
                    mImageView.setImageDrawable(getResources().getDrawable(R.mipmap.expand));
                } else {
                    spb.expand();
                    mTextView.setText("点击向上收叠");
                    mImageView.setImageDrawable(getResources().getDrawable(R.mipmap.collapse));
                }
            }
        });
    }*/
}
