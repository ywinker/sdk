package com.wink.sdk.biz;

import android.Manifest;

import java.util.List;

/**
 * 类名称    PermissionBiz
 * 内容摘要：
 * 修改备注： first version
 * 创建时间： 2020/1/4
 * 公司：    winker
 * 作者：    yingzy
 */
public abstract class PermissionBiz {

    protected static final int GPS_REQUEST_CODE = 0x001;

    protected static final int REQUEST_CODE_PERMISSION = 0x33;

    /**
     * camera permission(相机授权)
     */
    public static String CAMERA_PERMISSION = Manifest.permission.CAMERA;

    /**
     * location permission(位置授权)
     */
    public static String[] LOCATION_PERMISSION = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    /**
     * MIC permission(麦克风授权)
     */
    public static String MIC_PERMISSION = Manifest.permission.RECORD_AUDIO;

    /**
     * STORAGE permission(存储卡授权)
     */
    public static String[] STORAGE_PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission
            .WRITE_EXTERNAL_STORAGE};

    //申请获取手机信息授权
    public static String PHONE_INFO = Manifest.permission.READ_PHONE_STATE;

    /**
     * 检查相应权限，并申请未授权的部分
     */
    protected abstract boolean applyPermissions(String... permissions) throws Exception;

    /**
     * 查看是否包含权限
     */
    protected abstract boolean checkPermissions(String... permissions);

    /**
     * 获取需要申请权限的列表
     */
    protected abstract List<String> findDeniedPermissions(String[] permissions);
}
