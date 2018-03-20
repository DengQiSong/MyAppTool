package com.hyh.www.common.utils;

/**
 * Created by Dengqs on 2017/7/31
 * 单例模式例子
 * 1.实现了懒汉模式，在需要的时候才创建
 * 2.避免了多线程并发访问导致的问题
 * 3.解决了代码重拍优化导致的问题
 */

public class ClearViewHelper {
    private void ClearViewHelper() {
    }

    public static ClearViewHelper getInstance() {
        return ClearViewHelperHolder.clearViewHelper;
    }

    private static class ClearViewHelperHolder {
        public static ClearViewHelper clearViewHelper = new ClearViewHelper();
    }
}
