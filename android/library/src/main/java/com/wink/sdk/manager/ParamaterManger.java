package com.wink.sdk.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.wink.sdk.listener.Observer;

import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.UUID;

/**
 * 类名称    ParamaterManger
 * 内容摘要： 像素相关功能管理类
 * 修改备注： first version
 * 创建时间： 2019/12/5
 * 公司：    深圳市华移科技股份有限公司
 * 作者：    yingzy
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

    public int[] obtainViewWindowsParamter(View view){
        int[] paramter = new int[2];
        view.getLocationInWindow(paramter);
        return paramter;
    }

    private void initDisplayMetrics(){
        if (dm == null){
            dm = new DisplayMetrics();
            activityWeakReference.get().getWindowManager().getDefaultDisplay().getMetrics(dm);
        }
    }

    /**
     * 获取设备的uuid
     */
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public UUID getDeviceUUID() {
        TelephonyManager tm = null;

        if ((tm = (TelephonyManager) contextWeakReference.get().getSystemService(Context
                .TELEPHONY_SERVICE)) == null) return null;

        String tmDevice = "" + tm.getDeviceId();
        String tmSerial = "" + tm.getSimSerialNumber();
        String androidId = "" + android.provider.Settings.
                Secure.getString(contextWeakReference.get().getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);

        return new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
    }

    /**
     * 获取app版本
     */
    public String getVersionCode() {

        //获取包管理器
        PackageManager packageManager = contextWeakReference.get().getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(contextWeakReference.get().getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String[] getYears(int firstYear){
        int lastYear = Calendar.getInstance().get(Calendar.YEAR);
        String[] yearArr = new String[lastYear - firstYear + 1];

        for (int year = lastYear; year >= firstYear; year--){
            yearArr[lastYear - year] = String.valueOf(year);
        }

        return yearArr;
    }

    public String[] getMonths(){
        int lastMonth = Calendar.getInstance().get(Calendar.MONTH);
        String[] monthArr = new String[12];
        for (int position = 0; position < 12; position++){
            if (lastMonth - position + 1 > 0){
                monthArr[position] = String.valueOf(lastMonth - position + 1);
            }else {
                monthArr[position] = String.valueOf(position - lastMonth + 1);
            }
        }
        return monthArr;
    }

    @Override
    public void update(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
}
