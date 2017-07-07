package com.hyh.www.common.module.fragment.Test;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.BaseFragment;

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

    @Override
    protected int layoutRes() {
        return R.layout.fragment_test;
    }

    @Override
    protected void onViewReallyCreated(View view) {
        super.onViewReallyCreated(view);
        initView();
    }

    public void initView(){
        findView(R.id.doGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doGet(getContext());
            }
        });
       findView(R.id.But_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.doDownload(getContext());
            }
        });
        tv=findView(R.id.tv_text);
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
