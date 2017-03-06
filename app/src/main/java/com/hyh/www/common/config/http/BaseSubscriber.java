package com.hyh.www.common.config.http;

import android.content.Context;

import com.hyh.www.common.widget.ProgressDialog;

import rx.Subscriber;

/**
 * 作者：Denqs on 2017/3/1.
 */

public abstract class BaseSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{
    //在这实现请求对话框的展示
    private ProgressDialog dialog;
    public BaseSubscriber(Context context) {
        dialog=new ProgressDialog(context,this,true);
    }
    /**
     * 显示Dialog
     */
    public void showProgressDialog(){
        if (dialog != null) {
            dialog.show("正在加载....");
        }
    }
    //异常时调用onCompleted() 和 onError() 二者也是互斥的，即在队列中调用了其中一个，就不应该再调用另一个。
    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if(!NetUtil.isNetworkAvailable()){
            _onError("网络连接失败，请检查网络是否通畅");
        }else if(e instanceof ApiException){
            _onError(e.getMessage());
        }else{
            _onError("请求失败，请稍后再试...");
        }
        dismissProgressDialog();
    }
    //成功
    @Override
    public void onNext(T t) {
        _onSuccess(t);
    }
    //当不会再有新的 onNext() 发出时，需要触发 onCompleted() 方法作为标志。
    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }
    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog(){
        if (dialog != null) {
            dialog.dismiss();
            dialog=null;
        }
    }
    protected abstract void _onSuccess(T t);
    protected abstract void _onError(String message);
}
