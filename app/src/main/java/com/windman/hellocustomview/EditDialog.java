package com.windman.hellocustomview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vtrump on 2016/12/13.
 */

public class EditDialog extends Dialog implements View.OnClickListener {
    private Context mContext;

    private EditText mEditText;
    private ImageView mCLear;
    private TextView mCancel;
    private TextView mOk;
    private EditClickListener listener;

    public interface EditClickListener {
        void onOKClik(String text);
    }

    public void setOnEditClickListener(EditClickListener listener) {
        this.listener = listener;
    }

    public EditDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        initView();
        setCanceledOnTouchOutside(false);
        setWindowSize();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.dia_ed_edit);
        mCLear = (ImageView) findViewById(R.id.dia_ed_clear);
        mCancel = (TextView) findViewById(R.id.dia_ed_cancel);
        mOk = (TextView) findViewById(R.id.dia_ed_ok);

        mCLear.setOnClickListener(this);
        mCancel.setOnClickListener(this);
        mOk.setOnClickListener(this);
    }

    private void setWindowSize() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.8);
//        lp.height = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dia_ed_cancel:
                dismiss();
                break;

            case R.id.dia_ed_ok:
                listener.onOKClik(mEditText.getText().toString().trim());
                dismiss();
                break;

            case R.id.dia_ed_clear:
                mEditText.setText("");
                break;
        }
    }
}
