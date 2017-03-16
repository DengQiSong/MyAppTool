package com.hyh.www.common.widget;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.hyh.www.common.utils.IOUtil;
import com.hyh.www.common.utils.SDCardUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

/**
 * 作者：Denqs on 2017/2/27.
 * 照片选择
 */

public class ImageSelectView extends Activity {
    public static final String TEMP_PHOTO_FILE_NAME = "picker_hyh_temp.png";
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

    //线程安全
    public void Toast(CharSequence text) {
        if (null != text) {
            Toast.makeText(getApplication(), text, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 图片裁剪功能
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片的保存格式
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);
        //设置裁剪图片的图片质量
        options.setCompressionQuality(90);
        UCrop.of(uri, Uri.fromFile(IOUtil.makeLocalImageFile(TEMP_PHOTO_FILE_NAME)))
                .withOptions(options)
                .withAspectRatio(8, 8)
                .withMaxResultSize(800, 800)
                .start(ImageSelectView.this);
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
        Intent intent = new Intent();
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            intent.setData(resultUri);
            this.setResult(10002, intent);
            finish();
            overridePendingTransition(R.anim.in_anim, R.anim.in_from_down);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
            Log.e("ImageSelectView", cropError.toString());
            Toast(cropError.toString());
        }
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
                startPhotoZoom(Uri.fromFile(new File(ImageName)));
                break;
            case 103:
                if (null == data || null == data.getData()) {
                    return;
                }
                startPhotoZoom(data.getData());
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        try {
            IOUtil.deleteAllFile(ImageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
