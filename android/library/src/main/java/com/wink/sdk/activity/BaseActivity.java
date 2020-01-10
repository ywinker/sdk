package com.wink.sdk.activity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.wink.sdk.R;
import com.wink.sdk.manager.LoggerManger;
import com.wink.sdk.manager.WinkerManager;
import com.wink.sdk.util.StatusBarCompat;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    winker
 * 作者：    yingzy
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentLayout(getContentLayout()));
        mContext = getBaseContext();

        initial();
        initView();
        initRecyclerView();
        initListener();
        loadHttpData();

        StatusBarCompat.setStatusBarColorByActivity(this, false);
    }

    protected abstract int getContentLayout();

    public View getContentLayout(int id){
        mContext = getBaseContext();
        return LayoutInflater.from(mContext).inflate(id, null);
    }

    protected void initial(){
        WinkerManager.getInstance().onCreate(this);
    }

    protected abstract void initView();

    protected void initRecyclerView(){}

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
