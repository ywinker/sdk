package com.wink.sdk;

import android.content.Intent;

import com.wink.sdk.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initial() {
        super.initial();
        startActivity(new Intent(mContext, OtherActivity.class));
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }
}
