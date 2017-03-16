package com.hyh.www.common.module.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseFragment;
import com.hyh.www.common.config.Url;
import com.hyh.www.common.utils.IOUtil;
import com.hyh.www.common.utils.ImageUtils;
import com.hyh.www.common.widget.CircularImageView;
import com.hyh.www.common.widget.ImageSelectView;
import com.hyh.www.common.widget.imageloader.ImageTool;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * 作者：Denqs on 2017/3/7.
 */

public class MyFragment extends BaseFragment {

    CircularImageView imageView;
    TextView textView;

    public static MyFragment newInstance(String fragConent) {
        Bundle args = new Bundle();
        args.putString(MainActivity.ARGS_NAVI_BTN_NAME, fragConent);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_my, container, false);
        initView(v);
        return v;
    }

    public void initView(View v) {
        imageView = (CircularImageView) v.findViewById(R.id.circular);
        imageView.setRect_adius(120);
        textView = (TextView) v.findViewById(R.id.textView);
        ImageTool.loadUrl(getActivity(), imageView, Url.Image_Url);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), ImageSelectView.class), 10002);
                getActivity().overridePendingTransition(R.anim.out_to_down, R.anim.exit_anim);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        switch (resultCode) {
            case 10002:
                Observable.just(data.getData()).map(new Func1<Uri, String>() {

                    @Override
                    public String call(Uri uri) {
//                        Bitmap bitmap= ImageUtils.getBitmap(IOUtil.getRealFilePath(getContext(),uri));
//                        imageView.setImageBitmap(bitmap);
                        return IOUtil.getRealFilePath(getContext(), uri);
                    }
                }).subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("MyFragment","图片路径"+s);
                        ImageTool.loadUrl(getActivity(), imageView, s);
                    }
                });
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
