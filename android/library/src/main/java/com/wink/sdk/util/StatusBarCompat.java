package com.wink.sdk.util;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.util.Objects;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2019/12/20
 * 公司：    winker
 * 作者：    yingzy
 */
public class StatusBarCompat {

    public static void setStatusBarColorByActivity(Activity activity, boolean isBlack) {
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        activityWeakReference.get().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activityWeakReference.get().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && isBlack){
            activityWeakReference.get().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public static void setStatusBarColorByDialog(AlertDialog dialog, boolean isBlack) {

        Objects.requireNonNull(dialog.getWindow()).addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = dialog.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M && isBlack){
            dialog.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
