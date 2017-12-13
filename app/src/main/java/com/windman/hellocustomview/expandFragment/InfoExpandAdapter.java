package com.windman.hellocustomview.expandFragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.windman.hellocustomview.R;
import com.windman.hellocustomview.sectionProgressBar.SectionProgressBar;

import java.util.List;

import butterknife.internal.Utils;

public class InfoExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "InfoExpandAdapter";

    private List<MineParent> partentList;
    private List<List<MineChild>> childList;
    private LayoutInflater inflater;

    public InfoExpandAdapter(Context context, List<MineParent> infoPartentList, List<List<MineChild>> infoChildList) {
        this.partentList = infoPartentList;
        this.childList = infoChildList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getGroupCount() {
        return partentList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return partentList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parent) {
        PartentHolder holder;
        if (convertView == null) {
            holder = new PartentHolder();
            convertView = inflater.inflate(R.layout.mine_item_parent, null);
            holder.pDes = (TextView) convertView.findViewById(R.id.p_des);
            holder.pHeader = (ImageView) convertView.findViewById(R.id.p_header);
            holder.pScore = (ImageView) convertView.findViewById(R.id.p_score);
            holder.pExpand = (ImageView) convertView.findViewById(R.id.p_expand);
            convertView.setTag(holder);
        } else {
            holder = (PartentHolder) convertView.getTag();
        }
        MineParent p = partentList.get(position);
        holder.pDes.setText(p.getDes());
        holder.pHeader.setImageResource(p.getHeadImgResId());
        holder.pScore.setImageResource(p.getScoreImgResId());

        if (isExpanded) {
            holder.pExpand.setImageResource(R.mipmap.collapse);
        } else {
            holder.pExpand.setImageResource(R.mipmap.expand);
        }
        return convertView;
    }

    @Override
    public View getChildView(int partent, int child, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.mine_item_child, null);
            holder.cVender = (TextView) convertView.findViewById(R.id.c_vender);
            holder.cPhone = (TextView) convertView.findViewById(R.id.c_phone);
            holder.cUrl = (TextView) convertView.findViewById(R.id.c_url);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        MineChild c = childList.get(partent).get(child);
        holder.cVender.setText(c.getVenderInfo());
        holder.cPhone.setText(c.getPhone());
        holder.cUrl.setText(c.getUrl());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class PartentHolder {
        TextView pDes;
        ImageView pHeader, pScore, pExpand;
    }

    class ChildHolder {
        TextView cVender, cUrl, cPhone;
    }
}
