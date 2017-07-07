package com.hyh.www.common.module.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseFragment;
import com.hyh.www.common.config.Url;
import com.hyh.www.common.utils.IOUtil;
import com.hyh.www.common.widget.CircularImageView;
import com.hyh.www.common.widget.ImageSelectView;
import com.hyh.www.common.widget.imageloader.ImageTool;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * 作者：Denqs on 2017/3/7.
 */

public class MyFragment extends BaseFragment {

    CircularImageView imageView;

    public static MyFragment newInstance(String fragConent) {
        Bundle args = new Bundle();
        args.putString(MainActivity.ARGS_NAVI_BTN_NAME, fragConent);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        super.onViewReallyCreated(view);
        initView();
    }

    public void initView() {
        imageView = (CircularImageView) findView(R.id.circular);
        imageView.setRect_adius(120);
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
                Observable.just(data.getData()).map(new Function<Uri, String>() {

                    @Override
                    public String apply(@NonNull Uri uri) throws Exception {
//                        Bitmap bitmap= ImageUtils.getBitmap(IOUtil.getRealFilePath(getContext(),uri));
//                        imageView.setImageBitmap(bitmap);
                        return IOUtil.getRealFilePath(getContext(), uri);
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        Log.e("MyFragment", "图片路径" + s);
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
