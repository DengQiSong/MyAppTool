package com.hyh.www.common.module.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseFragment;
import com.hyh.www.common.module.Html5Activity;
import com.hyh.www.common.widget.myview.ScalePanel;

/**
 * 作者：Denqs on 2017/3/7.
 */

public class WebFragment extends BaseFragment {
    ScalePanel scalePanel;

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
        initView();
    }

    public void initView() {
        findView(R.id.yam).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent("com.dqs.www.ui.activity.htmlactivity"));
            }
        });
        scalePanel = findView(R.id.sp_panel);
    }


}
