package com.hyh.www.common.widget.imageloader;

import android.content.Context;

import com.bumptech.glide.Glide;

/**
 * 作者：Denqs on 2016/12/22.
 */

public class GlideImageLoaderProvider implements BaseImageLoaderStrategy {
    @Override
    public void loadImage(Context ctx, ImageLoader img) {
        loadNormal(ctx,img);
       /* boolean flag= SettingUtils.getOnlyWifiLoadImg(ctx);
        //如果不是在wifi下加载图片，直接加载
        if(!flag){

            return;
        }

        int strategy =img.getStrategy();
        if(strategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI){
            int netType = AppUtils.getNetWorkType(AbsApplication.app());
            //如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
            if(netType == AppUtils.NETWORKTYPE_WIFI) {
                loadNormal(ctx, img);
            } else {
                //如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
                loadCache(ctx, img);
            }
        }else{
            //如果不是在wifi下才加载图片
            loadNormal(ctx,img);
        }*/

    }


    /**
     * load image with Glide
     */
    private void loadNormal(Context ctx, ImageLoader img) {
        Glide.with(ctx).load(img.getUrl()).placeholder(img.getPlaceHolder()).into(img.getImgView());
    }


    /**
     *load cache image with Glide
     */
   /* private void loadCache(Context ctx, ImageLoader img) {
        Glide.with(ctx).using(new StreamModelLoader<String>() {
            @Override
            public DataFetcher<InputStream> getResourceFetcher(final String model, int i, int i1) {
                return new DataFetcher<InputStream>() {
                    @Override
                    public InputStream loadData(Priority priority) throws Exception {
                        throw new IOException();
                    }

                    @Override
                    public void cleanup() {

                    }

                    @Override
                    public String getId() {
                        return model;
                    }

                    @Override
                    public void cancel() {

                    }
                };
            }
        }).load(img.getUrl()).placeholder(img.getPlaceHolder()).diskCacheStrategy(DiskCacheStrategy.ALL).into(img.getImgView());
    }*/
}
