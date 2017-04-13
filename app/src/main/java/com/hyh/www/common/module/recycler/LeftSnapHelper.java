package com.hyh.www.common.module.recycler;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 作者：Denqs on 2017/4/13.
 */

public class LeftSnapHelper extends LinearSnapHelper {

    private OrientationHelper mHorizontalHelper;

    /**
     * 当拖拽或滑动结束时会回调该方法,该方法返回的是一个长度为2的数组,out[0]表示横轴,x[1]表示纵轴,这两个值就是你需要修正的位置的偏移量
     *
     * @param layoutManager
     * @param targetView
     * @return
     */
    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        //注:由于是横向滚动,在这里我们只考虑横轴的值
        int[] out = new int[2];
        //检查是否支持水平滚动
        if (layoutManager.canScrollHorizontally()) {
            out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager));
        } else {
            out[0] = 0;
        }
        return out;
    }

    //这个方法用来获取特定的视图，当返回null时，表示没有获取到任何视图
    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        return findStartView(layoutManager, getHorizontalHelper(layoutManager));
    }

    /**
     * 这个方法是计算偏移量
     *
     * @param targetView
     * @param helper
     * @return
     */
    private int distanceToStart(View targetView, OrientationHelper helper) {
        return helper.getDecoratedStart(targetView) - helper.getStartAfterPadding();
    }

    /**
     * 找到第一个显示的view
     *
     * @param layoutManager
     * @param helper
     * @return
     */
    private View findStartView(RecyclerView.LayoutManager layoutManager,
                               OrientationHelper helper) {
        if (layoutManager instanceof LinearLayoutManager) {
            int firstChild = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
            int lastChild = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            if (firstChild == RecyclerView.NO_POSITION) {
                return null;
            }

            //这是为了解决当翻到最后一页的时候，最后一个Item不能完整显示的问题
            if (lastChild == layoutManager.getItemCount() - 1) {
                return layoutManager.findViewByPosition(lastChild);
            }
            View child = layoutManager.findViewByPosition(firstChild);

            //得到此时需要左对齐显示的条目
            if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2
                    && helper.getDecoratedEnd(child) > 0) {
                return child;
            } else {
                return layoutManager.findViewByPosition(firstChild + 1);
            }
        }
        return super.findSnapView(layoutManager);
    }

    /**
     * 获取视图的方向
     *
     * @param layoutManager
     * @return
     */
    private OrientationHelper getHorizontalHelper(@NonNull RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }
}
