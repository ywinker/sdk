package com.wink.sdk.manager;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.wink.sdk.R;
import com.wink.sdk.biz.PermissionBiz;
import com.wink.sdk.listener.Observer;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 类名称:    PermissionManager
 * 内容摘要：  support risk authorization
 * 修改备注：  first version
 * 创建时间：  2020/1/4
 * 公司：     winker
 * 作者：     yingzy
 */
public class PermissionManager extends PermissionBiz implements Observer{

    private static volatile PermissionManager instance = null;

    private WeakReference<Activity> activityWeakReference = null;
    private WeakReference<Context> contextWeakReference = null;

    /**
     * permission with project relation
     */
    public static String[] PROJECT_PERMISSION = {};

    private PermissionManager(){

    }

    public static synchronized PermissionManager getInstance() {
        synchronized (WinkerManager.class) {
            if (instance == null) {
                instance = new PermissionManager();
            }
        }
        return instance;
    }

    /**
     * init PermissionManager class
     */
    public void init(Context context){
        contextWeakReference = new WeakReference<>(context);
    }

    /**
     * 项目相关权限
     */
    public boolean applyProjectPermissions() {
        return applyPermissions(PROJECT_PERMISSION);
    }

    /**
     * 检查相应权限，并申请未授权的部分
     */
    @Override
    public boolean applyPermissions(String... permissions)  {
        //如果没有全部授权，获取未授权的权限
        List<String> needRequestPermissionList = findDeniedPermissions(permissions);

        if (null != needRequestPermissionList && needRequestPermissionList.size() > 0){
            LoggerManger.getInstance().writeSDKLoggerAddTime(R.string.apply_permission,
                    needRequestPermissionList.toString());

            ActivityCompat.requestPermissions(activityWeakReference.get(), needRequestPermissionList.toArray(new
                    String[needRequestPermissionList.size()]), REQUEST_CODE_PERMISSION);
            return true;
        }

        return false;
    }

    /**
     * 查看是否包含权限
     */
    @Override
    public boolean checkPermissions(String... permissions){
        //如果android版本低于Android6.0，默认为开启权限（无危险权限）
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

        PackageManager pm = activityWeakReference.get().getPackageManager();

        for (String permission : permissions) {
            if (PackageManager.PERMISSION_GRANTED !=
                    pm.checkPermission(permission, activityWeakReference.get().getPackageName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取需要申请权限的列表
     */
    @Override
    protected List<String> findDeniedPermissions(String[] permissions){
        List<String> needRequestPermissionList = new ArrayList<>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(contextWeakReference.get(), perm) != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissionList.add(perm);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activityWeakReference.get(), perm)) {
                    needRequestPermissionList.add(perm);
                }
            }
        }
        return needRequestPermissionList;
    }

    /**
     * 观察者模式， 切换Activity上下文
     */
    @Override
    public void update(Activity activity) {
        this.activityWeakReference= new WeakReference<>(activity);
    }
}
