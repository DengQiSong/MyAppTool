package com.hyh.www.common.config.http;

import com.hyh.www.common.app.ActivityLifeCycleEvent;

import rx.Observable;
import rx.functions.Action0;
import rx.subjects.PublishSubject;

/**
 * 作者：Denqs on 2017/2/28.
 */

public class HttpUtil {
    private static class Instance {
        static HttpUtil instance = new HttpUtil();
    }

    public static HttpUtil getInstance() {
        return Instance.instance;
    }

    public void getData(Observable ob, final BaseSubscriber subscriber, final ActivityLifeCycleEvent event, final PublishSubject<ActivityLifeCycleEvent> lifecycleSubject) {
        //数据预处理
        Observable.Transformer<HttpResult<Object>, Object> result = RxHelper.handleResult(event,lifecycleSubject);
        Observable observable = ob.compose(result)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        //显示Dialog和一些其他操作
                        subscriber.showProgressDialog();
                    }
                });
        observable.subscribe(subscriber);
    }


}
