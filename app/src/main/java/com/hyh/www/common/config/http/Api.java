package com.hyh.www.common.config.http;

import com.hyh.www.common.config.Url;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：Denqs on 2017/2/28.
 */

public class Api {
    private static ApiService SERVICE;
    /**
     * 请求超时时间
     */
    public final static int CONNECT_TIMEOUT = 60*1000;
    public final static int READ_TIMEOUT = 100*1000;
    public final static int WRITE_TIMEOUT = 60*1000;

    public static ApiService getDefault() {
        if (SERVICE == null) {
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);//设置读取超时时间
            httpClientBuilder.writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);//设置写的超时时间
            httpClientBuilder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS);//设置连接超时时间

            /**
             *  拦截器
             */
            httpClientBuilder.addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl.Builder authorizedUrlBuilder = request.url()
                            .newBuilder()
                            //添加统一参数 如手机唯一标识符,token等
                            .addQueryParameter("key1","value1")
                            .addQueryParameter("key2", "value2");
                    Request newRequest = request.newBuilder()
                            //对所有请求添加请求头
                            .header("mobileFlag", "adfsaeefe").addHeader("type", "4")
                            .method(request.method(), request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    return  chain.proceed(newRequest);
                }
            });

            SERVICE = new Retrofit.Builder()
                    .client(httpClientBuilder.build())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(Url.BASE_URL)
                    .build().create(ApiService.class);
        }
        return SERVICE;
    }
}
