package com.hyh.www.common.module.fragment.Test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.ActivityLifeCycleEvent;
import com.hyh.www.common.app.BaseFragment;
import com.hyh.www.common.config.http.Api;
import com.hyh.www.common.config.http.BaseSubscriber;
import com.hyh.www.common.config.http.HttpUtil;
import com.hyh.www.common.utils.DownloadUtils;

import junit.framework.Test;

import rx.Observable;

/**
 * 作者：Denqs on 2017/3/7.
 */

public class TestFragment extends BaseFragment implements TestContract.View{
    private TestContract.Presenter presenter;
    private TextView tv;
    public static TestFragment newInstance(String fragConent) {
        Bundle args = new Bundle();
        args.putString(MainActivity.ARGS_NAVI_BTN_NAME, fragConent);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
         new TestPresenter(fragment);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_test, container, false);
        initView(v);
        return v;
    }

    public void initView(View view){
        view.findViewById(R.id.doGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doGet(getContext(),lifecycleSubject);
            }
        });
        view.findViewById(R.id.But_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doDownload(getContext());
            }
        });
        tv=(TextView) view.findViewById(R.id.tv_text);
        presenter.doGet(getContext(),lifecycleSubject);
    }




    @Override
    public void setPresenter(TestContract.Presenter presenter) {
        this.presenter=presenter;
    }

    @Override
    public void showTip(String message) {
        toast(message);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void addSuccess(String string) {
        tv.setText(string);
    }
}
