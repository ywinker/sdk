package com.wink.sdk.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.wink.sdk.R;
import com.wink.sdk.manager.LoggerManger;
import com.wink.sdk.manager.WinkerManager;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    winker
 * 作者：    yzy
 */
public abstract class BaseActivity extends Activity {

    protected Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout());

        initial();
        initView();
        recyclerView();
        initListener();
        loadHttpData();
    }

    protected abstract int getContentLayout();

    protected void initial(){
        mContext = getBaseContext();
        WinkerManager.getInstance().onCreate(this);
    }

    protected abstract void initView();

    protected void recyclerView(){}

    protected abstract void initListener();

    protected void loadHttpData(){}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int position = 0; position < permissions.length; position++){
            String result = (grantResults[position] == PackageManager.PERMISSION_GRANTED)?" - successfully":" - deny";

            LoggerManger.getInstance().writeSDKLoggerAddTime(R.string.callback_permissions_about,
                    permissions[position] + result);

        }
    }
}
