package com.hyh.www.common.module.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyh.www.common.R;

/**
 * 作者：Denqs on 2017/4/13.
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    public ImageView ivImage;
    public TextView tvName;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        ivImage = (ImageView) itemView.findViewById(R.id.adapter_iv_icon);
        tvName = (TextView) itemView.findViewById(R.id.adapter_tv_name);
    }
}
