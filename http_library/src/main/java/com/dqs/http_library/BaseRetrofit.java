package com.dqs.http_library;

import com.dqs.http_library.app.BaseHttpUrl;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：Denqs on 2017/7/6.
 */

public abstract class BaseRetrofit {
    protected Retrofit mRetrofit;
    /**
     * 请求超时时间
     */
    public final static int CONNECT_TIMEOUT = 60 * 1000;
    public final static int READ_TIMEOUT = 100 * 1000;
    public final static int WRITE_TIMEOUT = 60 * 1000;

    private static final int DEFAULT_TIME = 60;    //默认超时时间
    private final long RETRY_TIMES = 1;   //重订阅次数

    public BaseRetrofit() {
        if (null == mRetrofit) {
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
                            .addQueryParameter("key1", "value1")
                            .addQueryParameter("key2", "value2");
                    Request newRequest = request.newBuilder()
                            //对所有请求添加请求头
                            .header("mobileFlag", "adfsaeefe").addHeader("type", "4")
                            .method(request.method(), request.body())
                            .url(authorizedUrlBuilder.build())
                            .build();
                    return chain.proceed(newRequest);
                }
            });
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseHttpUrl.getInstance().getServerAddress())
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    protected <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())    // 指定subscribe()发生在IO线程
                .observeOn(AndroidSchedulers.mainThread())  // 指定Subscriber的回调发生在io线程
                .timeout(DEFAULT_TIME, TimeUnit.SECONDS)    //重连间隔时间
                .retry(RETRY_TIMES)
                .subscribe(observer);   //订阅
    }

    protected static <T> T getPresent(Class<T> cls) {
        T instance = null;
        try {
            instance = cls.newInstance();
            if (instance == null) {
                return null;
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
