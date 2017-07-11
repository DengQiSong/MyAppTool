package com.hyh.www.common.model;

import com.dqs.http_library.BaseResult;
import com.hyh.www.common.module.vo.Subject;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 作者：Denqs on 2017/7/7.
 */

public interface ModelApi {

    @GET("top250")
    Observable<BaseResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);

    @POST("file/fileUpload")
    @Multipart
    Observable<BaseResult> fileUpload(@Part MultipartBody.Part part);
}
