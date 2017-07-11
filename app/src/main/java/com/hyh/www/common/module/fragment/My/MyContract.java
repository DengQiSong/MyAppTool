package com.hyh.www.common.module.fragment.My;

import android.content.Context;

import com.hyh.www.common.app.BasePresenter;
import com.hyh.www.common.app.BaseView;
import com.hyh.www.common.module.vo.User;

/**
 * 作者：Denqs on 2017/7/11.
 */

public class MyContract {
    interface View extends BaseView<MyContract.Presenter> {
        void onLoadSuccess(User user);
    }

    interface Presenter extends BasePresenter {
        void loadAccount(Context context);
    }
}
