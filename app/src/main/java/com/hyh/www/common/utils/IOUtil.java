package com.hyh.www.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.hyh.www.common.app.BaseApplication;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

/**
 * 作者：Denqs on 2017/3/15.
 */

public class IOUtil {
    private static File cacheDir = null;

    public static void init() {
        cacheDir = !hasSDCard() ?
                BaseApplication.getInstances().getFilesDir()
                : BaseApplication.getInstances().getExternalCacheDir();
    }

    /**
     * sd卡是否存在
     *
     * @return
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据日期，随机数生成文件名
     *
     * @return
     */
    public static String generateRandomFilename() {
        String RandomFilename = "";
        Random rand = new Random();//生成随机数
        int random = rand.nextInt();

        Calendar calCurrent = Calendar.getInstance();
        int intDay = calCurrent.get(Calendar.DATE);
        int intMonth = calCurrent.get(Calendar.MONTH) + 1;
        int intYear = calCurrent.get(Calendar.YEAR);
        String now = String.valueOf(intYear) + "_" + String.valueOf(intMonth) + "_" +
                String.valueOf(intDay) + "_";
        RandomFilename = now + String.valueOf(random > 0 ? random : (-1) * random) + "";
        return RandomFilename;
    }

    public static String makeLocalImageFilePath(String destPicPath) throws IOException {
        if (cacheDir == null) {
            init();
        }

        File dirFile = new File(cacheDir.getPath() + File.separator
                + destPicPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        File localFile = new File(dirFile.getPath() + File.separator + generateRandomFilename() + ".jpg");

        if (!localFile.exists()) {
            localFile.createNewFile();
        }

        return localFile.getPath();
    }

    public static File makeLocalImageFile(String destPicPath) {
        if (cacheDir == null) {
            init();
        }

        File dirFile = new File(cacheDir.getPath() + File.separator
                + destPicPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        File localFile = new File(dirFile.getPath() + File.separator + generateRandomFilename() + ".jpg");

        if (!localFile.exists()) {
            try {
                localFile.createNewFile();
            } catch (IOException e) {
                Log.e("IOUtil","IOException:"+e.toString());
                e.printStackTrace();

            }
        }

        return localFile;
    }
    //删除应用缓存文件下的某文件
    public static void deleteAllTempFile(String fileDir) throws IOException {
        if (cacheDir == null) {
            init();
        }

        File deleteFileDir = new File(cacheDir.getPath() + File.separator + fileDir);

        if (deleteFileDir.exists()) {
            deleteFileDir.delete();
        }
    }
    public static void deleteAllFile(String fileDir) throws IOException {
        File deleteFileDir = new File(fileDir);

        if (deleteFileDir.exists()) {
            deleteFileDir.delete();
        }
    }

    /**
     * 得到图片角度
     */
    public static int rotateBitmapDegreeIfNeed(String path) {
        int degree = 0;

        if (TextUtils.isEmpty(path)) {
            return degree;
        }

        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    public static Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
        } finally {
            if (returnBm == null) {
                returnBm = bm;
            } else {
                if (bm != returnBm) {
                    bm.recycle();
                    bm = null;
                }
            }
        }

        return returnBm;
    }

    //  根据Uri获得其在文件系统中的路径
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) {
            return null;
        }

        final String scheme = uri.getScheme();
        String data = null;

        if (scheme == null) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri,
                        new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
                if (null != cursor) {
                    if (cursor.moveToFirst()) {
                        int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                        if (index > -1) {
                            data = cursor.getString(index);
                        }
                    }
                }
            } finally {
                if (null != cursor) {
                    cursor.close();
                }
            }
        }

        return data;
    }
}
