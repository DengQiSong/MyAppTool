package com.hyh.www.common.app;

import com.dqs.http_library.BaseRetrofit;
import com.hyh.www.common.model.ModelApi;

/**
 * 作者：Denqs on 2017/7/7.
 */

public class BaseModel extends BaseRetrofit {
    private static final String TAG = "BaseModel";
    protected ModelApi mServletApi;

    public BaseModel() {
        super();
        mServletApi = mRetrofit.create(ModelApi.class);
    }

}
