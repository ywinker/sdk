package com.wink.sdk.manager;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.wink.sdk.R;
import com.wink.sdk.biz.ObserverBiz;

import java.lang.ref.WeakReference;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    winker
 * 作者：    yingzy
 */
public class WinkerManager {

    private static volatile WinkerManager instance = null;

    private WeakReference<Context> contextWeakReference = null;

    private ObserverBiz observerBiz = null;

    private boolean permissionEnable = true;
    private boolean measureEnable = true;
    private boolean loggerEnable = true;

    private WinkerManager(){
    }

    public static synchronized WinkerManager getInstance() {
        synchronized (WinkerManager.class) {
            if (instance == null) {
                instance = new WinkerManager();
            }
        }
        return instance;
    }

    public void init(Context context){
        observerBiz = new ObserverBiz();
        contextWeakReference = new WeakReference<>(context);

        //加载文件管理
        observerBiz.registerObserver(FileManager.getInstance());

        //加载logger管理
        LoggerManger.getInstance().init(contextWeakReference.get(), loggerEnable);
        LoggerManger.getInstance().writeSDKLoggerAddTime(R.string.init_sdk_successfully);

        //加载权限管理
        if (permissionEnable){
            observerBiz.registerObserver(PermissionManager.getInstance());
            PermissionManager.getInstance().init(contextWeakReference.get());
            LoggerManger.getInstance().writeSDKLoggerAddTime("- init PermissionManager");
        }

        //加载手机参数配置管理
        if (measureEnable){
            observerBiz.registerObserver(ParamaterManger.getInstance());
            ParamaterManger.getInstance().init(contextWeakReference.get());
            LoggerManger.getInstance().writeSDKLoggerAddTime("- init ParamaterManger");
        }
    }

    public void onCreate(Activity activity){
        LoggerManger.getInstance().writeSDKLoggerAddTime(R.string.jump_activity, activity.getLocalClassName());
        observerBiz.notifyObserver(activity);
    }

    /**
     * @param permissionEnable 授权功能开关
     */
    public void openPermissionFuction(boolean permissionEnable){
        this.permissionEnable = permissionEnable;
    }

    public WinkerManager setProjectPermission(String... permissions){
        PermissionManager.PROJECT_PERMISSION = permissions;
        return instance;
    }

    public boolean applyProjectPermissions(){
        try {
            if (PermissionManager.getInstance().applyProjectPermissions()){
                return true;
            }
        }catch (Exception e){
            LoggerManger.getInstance().writeSDKLoggerAddTime(e.getMessage());
        }

        return false;
    }

    /**
     * @param measureEnable 像素转换管理类开关
     */
    public void openScreenFuction(boolean measureEnable){
        this.measureEnable = measureEnable;
    }

    /**
     * @param loggerEnable Logger管理类开关
     */
    public void openLogger(boolean loggerEnable){
        this.loggerEnable = loggerEnable;
    }

    /**
     * Toast相关
     */
    public void Toast(String info){
        Toast.makeText(contextWeakReference.get(), info, Toast.LENGTH_SHORT).show();
    }

    public void Toast(int id){
        Toast.makeText(contextWeakReference.get(), getString(id), Toast.LENGTH_SHORT).show();
    }

    public String getString(int id){
        return contextWeakReference.get().getResources().getString(id);
    }
}
