package com.hyh.www.common.config.http;

/**
 * 作者：Denqs on 2017/2/28.
 * 返回参数的处理 根据自己业务修改
 */

public class HttpResult<T> {
   /* private int status;
    private String bizCode;
    private String msg;
    private T data;
    private int total;
    private int pageNum;
    private int pageSize;
    private int pages;
    private T options;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }*/
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
}
