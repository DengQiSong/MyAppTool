package com.hyh.www.common.widget.imageloader;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

/**
 * 作者：Denqs on 2016/12/26.
 */

public class ImageTool {
    /**
     * 通过url加载图片
     *
     * @param activity
     * @param imageView
     * @param url
     */
    public static void loadUrl(Activity activity, ImageView imageView, String url) {
        ImageLoader loader = new ImageLoader.Builder()
                .url(url)
                .imgView(imageView).build();
        ImageLoaderUtil.getInstance().loadImage(activity, loader);
    }

    public static void loadUrl(Context context, ImageView imageView, String url) {
        ImageLoader loader = new ImageLoader.Builder()
                .url(url)
                .imgView(imageView).build();
        ImageLoaderUtil.getInstance().loadImage(context, loader);
    }

    /**
     * 通过url加载图片
     *
     * @param placeError 加载失败的图片
     */
    public static void loadUrl(Activity activity, ImageView imageView, String url, int placeError) {
        ImageLoader loader = new ImageLoader.Builder()
                .url(url)
                .placeHolder(placeError)
                .imgView(imageView).build();
        ImageLoaderUtil.getInstance().loadImage(activity, loader);
    }
}
