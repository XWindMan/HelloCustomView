package com.windman.hellocustomview.expandView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.windman.hellocustomview.R;

import java.util.List;

public class ExpandAdapter extends BaseExpandableListAdapter {
    private static final String TAG = "ExpandAdapter";

    private List<ParentBean> partentList;
    private List<List<ChildBean>> childList;
    private LayoutInflater inflater;

    public ExpandAdapter(Context context, List<ParentBean> PartentList, List<List<ChildBean>> ChildList) {
        this.partentList = PartentList;
        this.childList = ChildList;
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
            convertView = inflater.inflate(R.layout.item_parent, null);
            holder.pTitle = (TextView) convertView.findViewById(R.id.p_title);
            convertView.setTag(holder);
        } else {
            holder = (PartentHolder) convertView.getTag();
        }
        holder.pTitle.setText(partentList.get(position).getTitle());

        return convertView;
    }

    @Override
    public View getChildView(int partent, int child, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder holder;
        if (convertView == null) {
            holder = new ChildHolder();
            convertView = inflater.inflate(R.layout.item_child, null);
            holder.cImg = (ImageView) convertView.findViewById(R.id.c_img);
            holder.cdes = (TextView) convertView.findViewById(R.id.c_des);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.cdes.setText(childList.get(partent).get(child).getDes());
        holder.cImg.setBackgroundResource(childList.get(partent).get(child).getImageId());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class PartentHolder {
        TextView pTitle;
    }

    class ChildHolder {
        TextView cdes;
        ImageView cImg;
    }
}
