package com.dqs.http_library;

import java.io.Serializable;

/**
 * 作者：Denqs on 2017/7/7.
 */

public class BaseResult<T> implements Serializable {
    //用来模仿resultCode和resultMessage
    private int count;
    //用来模仿Data
    private T subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }

    private int start;
    private int total;
    private String title;

    /**
     * API是否请求成功
     *
     * @return 成功返回true, 失败返回false
     */
    public boolean isRequestSuccess() {
        return count != ApiException.REQUEST_FAILURE;
    }
}
