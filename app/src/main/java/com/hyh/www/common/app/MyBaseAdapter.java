package com.hyh.www.common.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 作者：Denqs on 2017/2/27.
 */

public class MyBaseAdapter<T> extends BaseAdapter {
    public Context context;
    public List<T> mInfolist;

    public MyBaseAdapter(Context context, List<T> mInfolist) {
        this.context = context;
        this.mInfolist = mInfolist;
    }

    public List<T> getList() {
        return mInfolist;
    }

    public void setList(List<T> mInfolist) {
        this.mInfolist = mInfolist;
    }

    public void addAll(List<T> mInfolist) {
        this.mInfolist.addAll(mInfolist);
    }

    public void clear() {
        mInfolist.clear();
    }

    @Override
    public int getCount() {
        return mInfolist.size();
    }

    @Override
    public Object getItem(int i) {
        return mInfolist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
