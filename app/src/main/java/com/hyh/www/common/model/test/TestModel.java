package com.hyh.www.common.model.test;

import com.dqs.http_library.HttpFunction;
import com.hyh.www.common.app.BaseModel;
import com.hyh.www.common.module.vo.Subject;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    //文件上传
    public void fileUpload(File file, Observer observer) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("fileUpload", file.getName(), requestFile);
        Observable observable = mServletApi.fileUpload(body).map(new HttpFunction());
        toSubscribe(observable, observer);
    }
}
