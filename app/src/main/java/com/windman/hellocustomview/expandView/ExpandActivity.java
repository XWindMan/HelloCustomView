package com.windman.hellocustomview.expandView;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.windman.hellocustomview.R;
import com.windman.hellocustomview.sectionProgressBar.SectionProgressBar;

/**
 * Created by WindMan on 2017/1/3.
 */

public class ExpandActivity extends Activity {
    private ExpandView mExpandView;
    private LinearLayout mLinearLayout;
    private TextView mTextView;
    private ImageView mImageView;
    private ListView mListView;
    private SectionProgressBar spb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand);
        initExpandView();
    }

    public void initExpandView() {
        mLinearLayout = (LinearLayout) findViewById(R.id.layout_title);
        mTextView = (TextView) findViewById(R.id.textview_title);
        mImageView = (ImageView) findViewById(R.id.imageview_state);
//        mExpandView = (ExpandView) findViewById(R.id.expandView);
        spb=(SectionProgressBar) findViewById(R.id.spb);
//        mExpandView.setContentView();
        mLinearLayout.setClickable(true);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
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
    }

    class ExpandAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        @Override
        public int getCount() {
            return 3;
        }

        public ExpandAdapter(Context context) {
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View layout = mInflater.inflate(R.layout.item_expand, null);
            return null;
        }


    }

    class ViewHolder {

    }
}
