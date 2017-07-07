package com.dqs.http_library;

import io.reactivex.functions.Function;

/**
 * 变换: 将服务器返回的原始数据剥离成我们最终想要的数据
 * BaseResult<T> 转换成 T
 */

public class HttpFunction<T> implements Function<BaseResult<T>, T> {

    @Override
    public T apply(BaseResult<T> response) throws Exception {
        if (!response.isRequestSuccess()) {
            throw new ApiException(response.getCount());
        }
        return response.getSubjects();
    }
}