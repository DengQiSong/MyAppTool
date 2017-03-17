package com.hyh.www.common.module.fragment.Test;

import android.content.Context;

import com.hyh.www.common.app.ActivityLifeCycleEvent;
import com.hyh.www.common.config.Url;
import com.hyh.www.common.config.http.Api;
import com.hyh.www.common.config.http.BaseSubscriber;
import com.hyh.www.common.config.http.HttpUtil;
import com.hyh.www.common.module.vo.Subject;
import com.hyh.www.common.utils.DownloadUtils;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;

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
    public void doGet(Context context,PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        Observable ob = Api.getDefault().getTopMovie(0, 100);
        HttpUtil.getInstance().getData(ob, new BaseSubscriber<List<Subject>>(context){

            @Override
            protected void _onSuccess(List<Subject> subjects) {
                if (!view.isActive()) {
                    return;
                }
                StringBuilder sb=new StringBuilder();
                for (int i=0;i<subjects.size();i++){
                    sb.append("电影名：" + subjects.get(i).getTitle() + "\n");
                }
                view.addSuccess(sb.toString());
            }

            @Override
            protected void _onError(String message) {
                     view.showTip(message);
            }
        }, ActivityLifeCycleEvent.DESTROY, lifecycleSubject);
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
