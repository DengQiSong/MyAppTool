package com.hyh.www.common.module.fragment.Test;

import android.content.Context;

import com.hyh.www.common.app.BasePresenter;
import com.hyh.www.common.app.BaseView;

/**
 * 作者：Denqs on 2017/3/14.
 */

public interface TestContract {
    interface View extends BaseView<Presenter> {
        void addSuccess(String string);
    }

    interface Presenter extends BasePresenter {
        void doGet(Context context);

        void doDownload(Context context);
    }

}
