package com.wink.sdk.local;

import android.os.Environment;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    winker
 * 作者：    yingzy
 */
public interface LocalInfo {
    String WINKER_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/winker/";

    String LOGGER_FILE = "SDKLogger.log";
}
