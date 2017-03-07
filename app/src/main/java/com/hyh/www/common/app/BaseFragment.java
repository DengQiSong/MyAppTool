package com.hyh.www.common.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.widget.Toast;

import com.hyh.www.common.config.easyPermission.PermissionCallBackM;
import com.hyh.www.common.config.easyPermission.easyPermission.EasyPermission;

/**
 * 作者：Denqs on 2017/2/27.
 */

public class BaseFragment extends Fragment implements EasyPermission.PermissionCallback {
    //动态权限获取
    protected Context mContext;
    private int mRequestCode;
    private String[] mPermissions;
    private PermissionCallBackM mPermissionCallBack;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public void toast(String content) {
        Toast.makeText(getActivity().getApplicationContext(), content, Toast.LENGTH_SHORT).show();
    }

    //居中显示的Toast
    public void showToast(String content) {
        if (getActivity() != null && !getActivity().isFinishing()) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), content, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         /*
            从Settings界面跳转回来，标准代码，就这么写
        */
        if (requestCode == EasyPermission.SETTINGS_REQ_CODE) {
            if (EasyPermission.hasPermissions(mContext, mPermissions)) {
                //已授权，处理业务逻辑
                onEasyPermissionGranted(mRequestCode, mPermissions);
            } else {
                onEasyPermissionDenied(mRequestCode, mPermissions);
            }
        }
    }

    @Override
    public void onEasyPermissionGranted(int requestCode, String... perms) {
        if (mPermissionCallBack != null) {
            mPermissionCallBack.onPermissionGrantedM(requestCode, perms);
        }
    }

    @Override
    public void onEasyPermissionDenied(final int requestCode, final String... perms) {
        //rationale: Never Ask Again后的提示信息
        if (EasyPermission.checkDeniedPermissionsNeverAskAgain(this, "没有获取权限、无法使用此功能，请去设置里授权", android.R.string.ok,
                android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        if (mPermissionCallBack != null) {
                            mPermissionCallBack.onPermissionDeniedM(
                                    requestCode, perms);
                        }
                    }
                }, perms)) {
            return;
        }

        if (mPermissionCallBack != null) {
            mPermissionCallBack.onPermissionDeniedM(requestCode, perms);
        }
    }
}
