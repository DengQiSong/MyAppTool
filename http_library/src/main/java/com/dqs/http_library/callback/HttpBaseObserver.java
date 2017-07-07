package com.dqs.http_library.callback;

import android.accounts.NetworkErrorException;
import android.net.ParseException;
import android.text.TextUtils;
import android.util.Log;

import com.dqs.http_library.ApiException;
import com.dqs.http_library.utils.FRToast;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

/**
 * 作者：Denqs on 2017/7/6.
 */

public abstract class HttpBaseObserver<T> extends BaseObserver<T> {

    private static final String TAG = "HttpBaseObserver";
    private boolean isNeedProgress;
    private String titleMsg;

    public HttpBaseObserver() {
        this(null, null);
    }

    public HttpBaseObserver(BaseImpl baseImpl) {
        this(baseImpl, "正在加载....");
    }

    public HttpBaseObserver(BaseImpl base, String titleMsg) {
        super(base);
        this.titleMsg = titleMsg;
        if (TextUtils.isEmpty(titleMsg)) {
            this.isNeedProgress = false;
        } else {
            this.isNeedProgress = true;
        }
    }

    @Override
    protected boolean isNeedProgressDialog() {
        return isNeedProgress;
    }

    @Override
    public String getTitleMsg() {
        return titleMsg;
    }

    @Override
    protected void _onError(Throwable t) {
        t.printStackTrace();
        StringBuffer sb = new StringBuffer();
        sb.append("请求失败：");
        if (t instanceof NetworkErrorException || t instanceof UnknownHostException || t instanceof ConnectException) {
            sb.append("网络异常,请检查网络是否通畅");
        } else if (t instanceof SocketTimeoutException || t instanceof InterruptedIOException || t instanceof TimeoutException) {
            sb.append("请求超时，请稍后再试");
        } else if (t instanceof JsonSyntaxException) {
            sb.append("请求不合法");
        } else if (t instanceof JsonParseException
                || t instanceof JSONException
                || t instanceof ParseException) {   //  解析错误
            sb.append("解析错误");
        } else if (t instanceof ApiException) {
            sb.append(t.getMessage());
        } else {
            FRToast.showToastSafe(t.getMessage());
            return;
        }
        Log.e(TAG, "onBaseError: " + sb.toString());
        FRToast.showToastSafe(sb.toString());
    }
}
