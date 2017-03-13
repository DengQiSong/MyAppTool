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

/**
 * 作者：Denqs on 2017/3/7.
 */

public class WebFragment extends BaseFragment {

    public static WebFragment newInstance(String fragConent) {
        Bundle args = new Bundle();
        args.putString(MainActivity.ARGS_NAVI_BTN_NAME, fragConent);
        WebFragment fragment = new WebFragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web, container, false);
        initView(v);
        return v;
    }

    public void initView(View view) {
         view.findViewById(R.id.yam).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 startActivity(new Intent(getActivity(), Html5Activity.class));
             }
         });
    }



}
