package com.hyh.www.common.module.fragment.My;

import android.content.Context;

import com.hyh.www.common.module.vo.User;

/**
 * 作者：Denqs on 2017/7/11.
 */

public class MyPresenter implements MyContract.Presenter {
    private MyContract.View view;

    public MyPresenter(MyContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void loadAccount(Context context) {
        User user=new User();
        user.setName("雷思雨");
        user.setAge(18);
        user.setCareers("洛阳花魁");
        view.onLoadSuccess(user);
    }

    @Override
    public void start() {
        view.showTip("欢迎大侠");
    }

    @Override
    public void destroy() {

    }
}
