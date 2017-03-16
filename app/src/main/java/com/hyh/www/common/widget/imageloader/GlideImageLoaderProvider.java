package com.hyh.www.common.widget.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;

/**
 * 作者：Denqs on 2016/12/22.
 */

public class GlideImageLoaderProvider implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        loadNormal(ctx, img);
    }



    /**
     * load image with Glide
     */
    private void loadNormal(Context ctx, ImageLoader img) {
        Glide.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).into(img.getImgView());
    }

}
