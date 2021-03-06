package com.dqs.http_library.progress;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.dqs.http_library.R;

import java.lang.ref.WeakReference;

/**
 * 作者：Denqs on 2017/3/1.
 * 网络加载等待窗口
 */

public class ProgressDialogHandler extends Handler {
    public Dialog mDialog;
    private Context context;
    private boolean cancelable = false;
    private ProgressCancelListener mProgressCancelListener;
    private final WeakReference<Context> reference;
    private AnimationDrawable animationDrawable = null;


    public ProgressDialogHandler(Context context, ProgressCancelListener mProgressCancelListener,
                                 boolean cancelable) {
        super();
        this.reference = new WeakReference<Context>(context);
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void create(String message) {
        TextView text = null;
        if (mDialog == null) {
            context = reference.get();
            mDialog = new Dialog(context, R.style.common_dialog);
            View dialogView = LayoutInflater.from(context).inflate(
                    R.layout.progress_view, null);
            text = (TextView) dialogView.findViewById(R.id.progress_message);
            ImageView loadingImage = (ImageView) dialogView.findViewById(R.id.progress_view);
            loadingImage.setImageResource(R.drawable.progress_dialog_loding);
            animationDrawable = (AnimationDrawable) loadingImage.getDrawable();
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setCancelable(cancelable);
            mDialog.setContentView(dialogView);

            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mProgressCancelListener != null)
                        mProgressCancelListener.onCancelProgress();
                }
            });
            Window dialogWindow = mDialog.getWindow();
            dialogWindow.setGravity(Gravity.CENTER_VERTICAL
                    | Gravity.CENTER_HORIZONTAL);
        }
        if (text != null) {
            if (TextUtils.isEmpty(message)) {
                text.setVisibility(View.GONE);
            } else {
                text.setText(message);
            }
        }
        if (!mDialog.isShowing() && context != null) {
            mDialog.show();
            if (animationDrawable != null) {
                animationDrawable.setOneShot(false);
                animationDrawable.start();
            }
        }
    }

    public void show(String msg) {
        create(msg);
    }

    public void setCanceledOnTouchOutside(boolean cancel) {
        mDialog.setCanceledOnTouchOutside(cancel);
    }

    public void setCancelable(boolean cancel) {
        mDialog.setCancelable(cancel);
    }

    public void dismiss() {
        context = reference.get();
        if (mDialog != null && mDialog.isShowing() && !((Activity) context).isFinishing()) {
            String name = Thread.currentThread().getName();
            mDialog.dismiss();
            animationDrawable.stop();
            mDialog = null;
        }
    }
}
