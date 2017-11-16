package com.windman.hellocustomview.moveListView;


import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.windman.hellocustomview.R;

import java.util.ArrayList;
import java.util.List;

public class MoveListActivity extends Activity {
    private static final String TAG = "MoveListActivity";
    private static final int move = 0x01;
    private static final int add = 0x02;
    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter=new MyAdapter();
    private List<Integer> mDataList = new ArrayList<>();
    private LinearLayoutManager manager;
    private int dx = 4;
    private boolean isMoving;
    private int index=0;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == move) {
                if (mRecyclerView != null) {
                    if (mRecyclerView!=null) {
                        mRecyclerView.scrollBy(dx, 0);
                    }
                }

                /*if (manager.findLastVisibleItemPosition() + 4 == mDataList.size()
                        || manager.findFirstVisibleItemPosition() <= 5) {
                    dx = -dx;
                }*/
                mHandler.sendEmptyMessageDelayed(move, 16);
            }
            if (msg.what==add) {
                mDataList.add((int) (Math.random() * 100));
                if (!isMoving) {
                    myAdapter.notifyDataSetChanged();
                } else {
                    myAdapter.notifyItemChanged(index);
                }
                mHandler.sendEmptyMessageDelayed(add,16);
                if (mDataList.size()>30&&!isMoving) {
                    isMoving=true;
                    mHandler.sendEmptyMessage(move);
                }
                index++;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.strenth_list);

        for (int i = 0; i < 5; i++) {
            mDataList.add((int) (Math.random() * 100));
        }
        manager = new LinearLayoutManager(MoveListActivity.this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.scrollToPosition(20);
        mHandler.sendEmptyMessage(add);
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.VH> {

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(MoveListActivity.this)
                    .inflate(R.layout.item1, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            int resId = R.mipmap.strength_lv1;
            if (mDataList.get(position) < 20) {
                resId = R.mipmap.strength_lv1;
            } else if (mDataList.get(position) < 40) {
                resId = R.mipmap.strength_lv2;
            } else if (mDataList.get(position) < 60) {
                resId = R.mipmap.strength_lv3;
            } else if (mDataList.get(position) < 80) {
                resId = R.mipmap.strength_lv4;
            } else if (mDataList.get(position) < 100) {
                resId = R.mipmap.strength_lv5;
            }
            holder.itemImage.setImageResource(resId);
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        class VH extends RecyclerView.ViewHolder {
            ImageView itemImage;

            public VH(View itemView) {
                super(itemView);
                itemImage = (ImageView) itemView.findViewById(R.id.item1_img);
            }
        }
    }
}
