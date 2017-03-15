package com.hyh.www.common.config.http;

import com.hyh.www.common.module.vo.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 作者：Denqs on 2017/2/28.
 */

public interface ApiService {
    @POST("weather/getByCity")
    Observable<HttpResult> getWeathercnData(@Query("cityName") String city);

    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);
}
