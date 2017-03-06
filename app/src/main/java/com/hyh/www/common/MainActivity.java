package com.hyh.www.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hyh.www.common.app.ActivityLifeCycleEvent;
import com.hyh.www.common.app.BaseActivity;
import com.hyh.www.common.config.http.Api;
import com.hyh.www.common.config.http.BaseSubscriber;
import com.hyh.www.common.config.http.HttpUtil;
import com.hyh.www.common.module.MainFragment;
import com.hyh.www.common.utils.DownloadUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

public class MainActivity extends BaseActivity {
    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();//底部菜单对应fragment
    public static final String ARGS_NAVI_BTN_NAME = "ARGS_NAVI_BTN_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefaultFragment();
    }

    /**
     * 设置默认显示的fragment
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment fragment = MainFragment.newInstance(getString(R.string.bottom_navi_htn_home));
        mFragmentMap.put(0, fragment);
        transaction.add(R.id.layFrame, mFragmentMap.get(0));
        transaction.commit();
    }

    public void But_on(View view){
        new DownloadUtils(this).downloadAPK("http://115.231.191.149/imtt.dd.qq.com/16891/04DE82340177CDDAC468BF92FFD2BD11.apk?mkey=58b8c52d41cbeb86&f=b108&c=0&fsname=com.tencent.pao_1.0.42.0_142.apk&csr=1bbd&p=.apk","com.tencent.pao_1.0.42.0_142.apk");
    }
    private void doGet() {
        Observable ob = Api.getDefault().getTopMovie(0, 100);
//        Observable ob = Api.getDefault().getWeathercnData("杭州");
        HttpUtil.getInstance().getData(ob, new BaseSubscriber(this) {
            @Override
            protected void _onSuccess(Object o) {
                Log.e("数据：",">>"+String.valueOf(o));
            }

            @Override
            protected void _onError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        }, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
    }
}
