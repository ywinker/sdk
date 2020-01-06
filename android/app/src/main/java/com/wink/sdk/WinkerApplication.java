package com.wink.sdk;

import android.app.Application;

import com.wink.sdk.manager.WinkerManager;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    深圳市华移科技股份有限公司
 * 作者：    zxy
 */
public class WinkerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        WinkerManager.getInstance().init(getApplicationContext());
    }
}
