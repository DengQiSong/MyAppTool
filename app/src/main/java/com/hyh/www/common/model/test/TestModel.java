package com.hyh.www.common.model.test;

import com.dqs.http_library.HttpFunction;
import com.hyh.www.common.app.BaseModel;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * 作者：Denqs on 2017/7/7.
 */

public class TestModel extends BaseModel {
    public static TestModel getInstance() {
        return getPresent(TestModel.class);
    }

    public void execute(int i, int x, Observer observer) {
        Observable observable = mServletApi.getTopMovie(i, x).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
