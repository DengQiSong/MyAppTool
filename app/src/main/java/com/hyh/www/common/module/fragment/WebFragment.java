package com.hyh.www.common.module.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseFragment;
import com.hyh.www.common.widget.myview.ScalePanel;
import com.dqs.example.widgetlibrary.DownMenuView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：Denqs on 2017/3/7.
 */

public class WebFragment extends BaseFragment {
    @BindView(R.id.sp_panel)
    ScalePanel scalePanel;

    @BindView(R.id.dm_title)
    DownMenuView downdMenu;

    private List<String> menuData=new ArrayList<>();

    public static WebFragment newInstance(String fragConent) {
        Bundle args = new Bundle();
        args.putString(MainActivity.ARGS_NAVI_BTN_NAME, fragConent);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_web;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        super.onViewReallyCreated(view);
        getData();
        initView();
    }

    public void initView() {
        findView(R.id.yam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("com.dqs.www.ui.activity.htmlactivity"));
            }
        });
        scalePanel.setTotal(444);
        downdMenu.setData(menuData);
        downdMenu.setOnClickDownMenuItem(new DownMenuView.OnClickItemDownMenu() {
            @Override
            public void onItemClick(String currentProduct) {
                downdMenu.setTitle(currentProduct);
            }
        });
    }

    private void getData() {
        String[] menuStr1 = new String[]{"全部", "逗逼斌的志愿", "逗逼斌的志向", "逗逼斌的日记", "逗逼斌的二货日子",
                "逗逼斌的饮料", "逗逼斌的水果"};
        for (int i = 0, len = menuStr1.length; i < len; ++i) {
            menuData.add(menuStr1[i]);
        }
    }

}
