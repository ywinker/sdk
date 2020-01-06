package com.wink.sdk;

import com.wink.sdk.activity.BaseActivity;
import com.wink.sdk.biz.PermissionBiz;
import com.wink.sdk.manager.WinkerManager;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    深圳市华移科技股份有限公司
 * 作者：    yzy
 */
public class OtherActivity extends BaseActivity {

    @Override
    protected int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initial() {
        super.initial();
        WinkerManager.getInstance().setProjectPermission(PermissionBiz.CAMERA_PERMISSION, PermissionBiz
                .MIC_PERMISSION).applyProjectPermissions();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }
}
