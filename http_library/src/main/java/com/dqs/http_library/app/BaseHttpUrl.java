package com.dqs.http_library.app;

import android.text.TextUtils;

/**
 * 作者：Denqs on 2017/7/7.
 */

public class BaseHttpUrl {

    private String debugAddress;
    private String releaseAddress;

    private static final class SingletonHolder {
        private static final BaseHttpUrl INSTANCE = new BaseHttpUrl();
    }

    public static BaseHttpUrl getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private BaseHttpUrl() {
    }

    public String getServerAddress() {
        return TextUtils.isEmpty(releaseAddress) ? debugAddress : releaseAddress;
    }

    public String getDebugAddress() {
        return debugAddress;
    }

    public void setDebugAddress(String debugAddress) {
        this.debugAddress = debugAddress;
    }

    public String getReleaseAddress() {
        return releaseAddress;
    }

    public void setReleaseAddress(String releaseAddress) {
        this.releaseAddress = releaseAddress;
    }
}
