package com.hyh.www.common.config.easyPermission;

/**
 * 作者：Denqs on 2016/12/30.
 */

public interface PermissionCallBackM {
    void onPermissionGrantedM(int requestCode, String... perms);

    void onPermissionDeniedM(int requestCode, String... perms);
}
