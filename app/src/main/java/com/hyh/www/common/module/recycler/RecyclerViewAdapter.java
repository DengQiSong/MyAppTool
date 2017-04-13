package com.hyh.www.common.module.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hyh.www.common.R;
import com.hyh.www.common.module.vo.RecyclerViewBean;
import com.hyh.www.common.widget.imageloader.ImageTool;

import java.util.List;

/**
 * 作者：Denqs on 2017/4/13.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private List<RecyclerViewBean> mList;
    private LayoutInflater mInflater;
    private Context mContext;

    public RecyclerViewAdapter(List<RecyclerViewBean> mList, Context mContext) {
        this.mContext = mContext;
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = mList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(mInflater.inflate(R.layout.item_recyclerview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        ImageTool.loadUrl(mContext,holder.ivImage,mList.get(position % mList.size()).getImageUrl());
        holder.tvName.setText(mList.get(position % mList.size()).getName());
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }
}
