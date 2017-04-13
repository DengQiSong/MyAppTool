package com.hyh.www.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hyh.www.common.app.BaseActivity;
import com.hyh.www.common.module.fragment.MainFragment;
import com.hyh.www.common.module.fragment.MyFragment;
import com.hyh.www.common.module.fragment.Test.TestFragment;
import com.hyh.www.common.module.fragment.WebFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener{
    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();//底部菜单对应fragment
    public static final String ARGS_NAVI_BTN_NAME = "ARGS_NAVI_BTN_NAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.home_02, R.string.home)
                .setInactiveIconResource(R.mipmap.home_01))
                .addItem(new BottomNavigationItem(R.mipmap.lock_02, R.string.web)
                        .setInactiveIconResource(R.mipmap.lock_01))
                .addItem(new BottomNavigationItem(R.mipmap.monitoring_02, R.string.test)
                        .setInactiveIconResource(R.mipmap.monitoring_01))
                .addItem(new BottomNavigationItem(R.mipmap.my_02, R.string.my)
                        .setInactiveIconResource(R.mipmap.my_01))
                .setFirstSelectedPosition(0)
                .initialise();
        setDefaultFragment();
        bottomNavigationBar.setTabSelectedListener(this);
    }

    /**
     * 设置默认显示的fragment
     */
    private void setDefaultFragment() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment fragment = MainFragment.newInstance(getString(R.string.home));
        mFragmentMap.put(0, fragment);
        transaction.add(R.id.layFrame, mFragmentMap.get(0));
        transaction.commit();
    }


    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment curFragment = null;
        switch (position) {
            case 0:
                if (!mFragmentMap.containsKey(position)) {
                    curFragment = MainFragment.newInstance(getString(R.string.home));
                    mFragmentMap.put(position, curFragment);
                }
                break;
            case 1:
                if (!mFragmentMap.containsKey(position)) {
                    curFragment = WebFragment.newInstance(getString(R.string.web));
                    mFragmentMap.put(position, curFragment);
                }
                break;
            case 2:
                if (!mFragmentMap.containsKey(position)) {
                    curFragment = TestFragment.newInstance(getString(R.string.test));
                    mFragmentMap.put(position, curFragment);
                }
                break;
            case 3:
                if (!mFragmentMap.containsKey(position)) {
                    curFragment = MyFragment.newInstance(getString(R.string.my));
                    mFragmentMap.put(position, curFragment);
                }
                break;
            default:
                break;
        }
        if (!mFragmentMap.containsKey(position)) {
            return;
        }
        //已经存在
        if (curFragment == null) {
            transaction.show(mFragmentMap.get(position));
        } else {
            transaction.add(R.id.layFrame, mFragmentMap.get(position));
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (mFragmentMap.containsKey(position)) {
            transaction.hide(mFragmentMap.get(position));
        }
        transaction.commit();
    }

    @Override
    public void onTabReselected(int position) {

    }

    public boolean isAc(){
        //判断是否还存在
        return isFinishing();
    }
}
