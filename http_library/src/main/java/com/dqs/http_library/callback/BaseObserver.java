package com.dqs.http_library.callback;

import android.util.Log;

import com.dqs.http_library.progress.ProgressCancelListener;
import com.dqs.http_library.progress.ProgressDialogHandler;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * 作者：Denqs on 2017/7/6.
 */

public abstract class BaseObserver<T> implements Observer<T>, ProgressCancelListener {
    private static final String TAG = "BaseObserver";
    //在这实现请求对话框的展示
    private ProgressDialogHandler dialog;
    private BaseImpl mBaseImpl;
    private Disposable disposable;

    protected abstract void _onSuccess(T t);

    protected abstract void _onError(Throwable t);

    protected abstract boolean isNeedProgressDialog();

    protected abstract String getTitleMsg();

    public BaseObserver(BaseImpl baseImpl) {
        mBaseImpl = baseImpl;
        if (null != mBaseImpl) {
            if (null == dialog) {
                this.dialog = new ProgressDialogHandler(baseImpl.getContext(), this, true);
            }
        }
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        //显示进度条
        if (isNeedProgressDialog()) {
            showProgressDialog();
        }
        if (null != mBaseImpl) {
            if (null != d) {
                mBaseImpl.addDisposable(d);
            }
        }
        disposable = d;
    }

    @Override
    public void onNext(@NonNull T value) {
        //成功
        Log.d(TAG, "http is onNext");
        if (null != value) {
            _onSuccess(value);
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        //关闭进度条
        Log.e(TAG, "http is onError");
        if (isNeedProgressDialog()) {
            dismissProgressDialog();
        }
        _onError(e);
    }

    @Override
    public void onComplete() {
        //关闭进度条
        if (isNeedProgressDialog()) {
            dismissProgressDialog();
        }
    }

    @Override
    public void onCancelProgress() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (dialog != null) {
            dialog.show(getTitleMsg());
        }
    }

    /**
     * 隐藏Dialog
     */
    private void dismissProgressDialog() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
}
