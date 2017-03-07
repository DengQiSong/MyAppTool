package com.hyh.www.common.module.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hyh.www.common.MainActivity;
import com.hyh.www.common.R;
import com.hyh.www.common.app.ActivityLifeCycleEvent;
import com.hyh.www.common.app.BaseFragment;
import com.hyh.www.common.config.http.Api;
import com.hyh.www.common.config.http.BaseSubscriber;
import com.hyh.www.common.config.http.HttpUtil;
import com.hyh.www.common.utils.DownloadUtils;

import rx.Observable;

/**
 * 作者：Denqs on 2017/3/7.
 */

public class TestFragment extends BaseFragment {

    public static TestFragment newInstance(String fragConent) {
        Bundle args = new Bundle();
        args.putString(MainActivity.ARGS_NAVI_BTN_NAME, fragConent);
        TestFragment fragment = new TestFragment();
        fragment.setArguments(args);
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
                doGet();
            }
        });
        view.findViewById(R.id.But_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                But_on();
            }
        });
    }

    public void But_on() {
        new DownloadUtils(getContext()).downloadAPK("http://115.231.191.149/imtt.dd.qq.com/16891/04DE82340177CDDAC468BF92FFD2BD11.apk?mkey=58b8c52d41cbeb86&f=b108&c=0&fsname=com.tencent.pao_1.0.42.0_142.apk&csr=1bbd&p=.apk", "com.tencent.pao_1.0.42.0_142.apk");
    }

    private void doGet() {
        Observable ob = Api.getDefault().getTopMovie(0, 100);
//        Observable ob = Api.getDefault().getWeathercnData("杭州");
        HttpUtil.getInstance().getData(ob, new BaseSubscriber(getContext()) {
            @Override
            protected void _onSuccess(Object o) {
                Log.e("数据：", ">>" + String.valueOf(o));
            }

            @Override
            protected void _onError(String message) {
                toast(message);
            }
        }, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }
}
