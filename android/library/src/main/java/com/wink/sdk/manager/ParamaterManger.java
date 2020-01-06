package com.wink.sdk.manager;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.wink.sdk.listener.Observer;

import java.lang.ref.WeakReference;

/**
 * 类名称    ParamaterManger
 * 内容摘要： 像素相关功能管理类
 * 修改备注： first version
 * 创建时间： 2019/12/5
 * 公司：    深圳市华移科技股份有限公司
 * 作者：    yzy
 */
public class ParamaterManger implements Observer{

    private static ParamaterManger instance = null;

    private WeakReference<Activity> activityWeakReference = null;
    private WeakReference<Context> contextWeakReference = null;

    private static DisplayMetrics dm = null;

    private ParamaterManger(){}

    public static synchronized ParamaterManger getInstance(){
        synchronized (ParamaterManger.class){
            if (instance == null){
                instance = new ParamaterManger();
            }
        }

        return instance;
    }


    public void init(Context context){
        contextWeakReference = new WeakReference<>(context);
    }

    public int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, contextWeakReference.get().getResources().getDisplayMetrics());
    }

    public int[] obtainScreenParamter(){
        initDisplayMetrics();

        return new int[]{dm.heightPixels, dm.widthPixels};
    }

    private void initDisplayMetrics(){
        if (dm == null){
            dm = new DisplayMetrics();
            activityWeakReference.get().getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
    }

    @Override
    public void update(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
}
