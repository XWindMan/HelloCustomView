package com.windman.hellocustomview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class EditDialog extends Dialog implements View.OnClickListener {
    private Context mContext;

    public EditDialog(Context context) {
        super(context, R.style.MyDialogStyle);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        setCanceledOnTouchOutside(false);
        setWindowSize();
    }

    private void setWindowSize() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.9);
//        lp.height = (int) (d.widthPixels * 0.8);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dia_ed_cancel:
                break;
            case R.id.dia_ed_ok:
                break;
        }
    }
}
