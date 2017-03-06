package com.hyh.www.common.widget.banner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.hyh.www.common.R;
import com.hyh.www.common.widget.imageloader.ImageTool;

/**
 * 作者：Denqs on 2017/3/6.
 */

public class BannerHolderView implements Holder<BannerBean> {
    ImageView bannerImage;
    TextView bannerFlag;
    @Override
    public View createView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_item, null);
        bannerImage=(ImageView) view.findViewById(R.id.bannerImage);
        bannerFlag=(TextView) view.findViewById(R.id.bannerFlag);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, BannerBean data) {
        ImageTool.loadUrl(context,bannerImage,data.getImageUrl());
        bannerFlag.setText(data.getImageName());
    }
}
