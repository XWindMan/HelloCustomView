package com.windman.hellocustomview;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by WindMan on 2016/12/13.
 */
public class PicAlertDialog extends AlertDialog implements View.OnClickListener {

    private TakePhotoListener listener;

    public interface TakePhotoListener {
        void takePicFromCamera();

        void takePicFromAlbum();
    }

    public void setInterfaceTakePic(TakePhotoListener listener) {
        this.listener = listener;
    }

    public PicAlertDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pic_dialog);

        // 相机取
        Button fromCamera = (Button) this.findViewById(R.id.fromCamera);
        fromCamera.setOnClickListener(this);
        // 相册选取
        Button fromAlbum = (Button) this.findViewById(R.id.fromAlbum);
        fromAlbum.setOnClickListener(this);
        // 取消
        Button cancer = (Button) this.findViewById(R.id.cancer);
        cancer.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancer:
                break;
            case R.id.fromAlbum:
                listener.takePicFromAlbum();
                break;
            case R.id.fromCamera:
                listener.takePicFromCamera();
                break;
        }
        dismiss();
    }
}