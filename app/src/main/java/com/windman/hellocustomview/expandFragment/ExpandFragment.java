package com.windman.hellocustomview.expandFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.windman.hellocustomview.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExpandFragment extends Fragment {


    public ExpandFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.info_listview)
    ExpandableListView expandableListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expand, container,
                false);
        ButterKnife.bind(this,view);
        expandableListView.setAdapter(new InfoExpandAdapter(getActivity(),
                getParentData(), getChildData()));
        return view;
    }

    private List<MineParent> getParentData() {
        List<MineParent> partentList = new ArrayList<>();

        MineParent p1 = new MineParent();
        p1.setHeadImgResId(R.mipmap.ic_launcher);
        p1.setScoreImgResId(R.mipmap.ic_launcher);
        p1.setDes("hello");

        MineParent p2 = new MineParent();
        p2.setHeadImgResId(R.mipmap.ic_launcher);
        p2.setScoreImgResId(R.mipmap.ic_launcher);
        p2.setDes("hello");

        MineParent p3 = new MineParent();
        p3.setHeadImgResId(R.mipmap.ic_launcher);
        p3.setScoreImgResId(R.mipmap.ic_launcher);
        p3.setDes("hello");

        partentList.add(p1);
        partentList.add(p2);
        partentList.add(p3);
        return partentList;
    }

    private List<List<MineChild>> getChildData() {
        List<List<MineChild>> list = new ArrayList<>();

        List<MineChild> list1 = new ArrayList<>();
        MineChild c1 = new MineChild();
        c1.setPhone("400583900");
        c1.setUrl("http//:baidu.com");
        c1.setVenderInfo("亚美爹");
        list1.add(c1);

        List<MineChild> list2 = new ArrayList<>();
        MineChild c2 = new MineChild();
        c2.setPhone("400583900");
        c2.setUrl("http//:meituan.com");
        c2.setVenderInfo("亚美爹");
        list2.add(c2);

        List<MineChild> list3 = new ArrayList<>();
        MineChild c3 = new MineChild();
        c3.setPhone("400583900");
        c3.setUrl("http//:elemo.com");
        c3.setVenderInfo("亚美爹");
        list3.add(c3);

        list.add(list1);
        list.add(list2);
        list.add(list3);

        return list;
    }
}
