package com.wink.sdk.listener;

import android.app.Activity;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    深圳市华移科技股份有限公司
 * 作者：    yingzy
 */
public interface Observerable {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObserver(Activity activity);
}
