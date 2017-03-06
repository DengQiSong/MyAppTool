package com.hyh.www.common.widget;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.hyh.www.common.R;
import com.hyh.www.common.utils.SDCardUtils;

import java.io.File;

/**
 * 作者：Denqs on 2017/2/27.
 * 照片选择
 */

public class ImageSelectView extends Activity {
    public static final String TEMP_PHOTO_FILE_NAME = "picker_hyh_temp.jpg";
    String ImageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_select_view);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        this.getWindow().setLayout(dm.widthPixels, dm.heightPixels);
        createCameraFile();
        findViewById(R.id.img_camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    Uri mImageCaptureUri;
                    String state = Environment.getExternalStorageState();
                    if (Environment.MEDIA_MOUNTED.equals(state)) {
                        mImageCaptureUri = Uri.fromFile(new File(ImageName));
                    } else {
                        Toast("SD卡缺失，无法拍照！");
                        return;
                    }
                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, 102);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        findViewById(R.id.img_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 相册
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 103);
            }
        });
        findViewById(R.id.img_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void createCameraFile() {
        if (SDCardUtils.isSDCardEnable()) {
            ImageName = SDCardUtils.getSDCardPath() + TEMP_PHOTO_FILE_NAME;
        } else {
            ImageName = getFilesDir() + TEMP_PHOTO_FILE_NAME;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        overridePendingTransition(R.anim.in_anim, R.anim.in_from_down);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.in_anim, R.anim.in_from_down);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != 102)
            if (data == null)
                return;

        Intent intent = new Intent();
        switch (requestCode) {
            case 102:
                // 设置文件保存路径这里放在跟目录下
                if (Activity.RESULT_OK != resultCode) {
                    Toast("拍照出错，请重新再试！");
                    return;
                }
                if (ImageName == null || ImageName.equals("")) {
                    Toast("拍照出错，请重新再试！");
                    return;
                }
                Log.e("EditDataActivity", "相机：ImageName:" + ImageName);
                intent.setAction("10011");
                intent.setType(ImageName);
                this.setResult(10002, intent);
                break;
            case 103:
                if (null == data || null == data.getData()) {
                    return;
                }
                intent.setAction("10022");
                intent.setData(data.getData());
                this.setResult(10002, intent);
                break;
        }
        finish();
        overridePendingTransition(R.anim.in_anim, R.anim.in_from_down);
    }

    //线程安全
    public void Toast(CharSequence text) {
        if (null != text) {
            Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show();
        }
    }
}
