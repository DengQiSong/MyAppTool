package com.hyh.www.common.module.fragment.Test;

import android.content.Context;
import android.util.Log;

import com.dqs.http_library.callback.HttpBaseObserver;
import com.hyh.www.common.MainActivity;
import com.hyh.www.common.config.Url;
import com.hyh.www.common.model.test.TestModel;
import com.hyh.www.common.module.vo.Subject;
import com.hyh.www.common.utils.DownloadUtils;

import java.util.List;

/**
 * 作者：Denqs on 2017/3/14.
 */

public class TestPresenter implements TestContract.Presenter {
    private TestContract.View view;

    public TestPresenter(TestContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void doGet(Context context) {
        TestModel.getInstance().execute(0, 100, new HttpBaseObserver<List<Subject>>((MainActivity)context) {
            @Override
            protected void _onSuccess(List<Subject> subjects) {
                Log.e("TAG","数据总数"+subjects .size()+"，第一条数据title "+subjects.get(0).getTitle());
                view.addSuccess("数据总数"+subjects .size()+"，第一条数据title "+subjects.get(0).getTitle());
            }
        });
    }

    @Override
    public void doDownload(Context context) {
        new DownloadUtils(context).downloadAPK(Url.DOWNLOAD_URL, "com.tencent.pao_1.0.42.0_142.apk");

    }

    @Override
    public void start() {
        view.showTip("欢迎");
    }

    @Override
    public void destroy() {
        this.view = null;
    }
}
