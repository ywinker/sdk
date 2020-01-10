package com.wink.sdk.local;

import android.os.Environment;
import android.text.TextUtils;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2020/1/4
 * 公司：    winker
 * 作者：    yingzy
 */
public class LocalInfo {
    private final static String WINKER_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/winker/";
    private final static String LOGGER_FILE = "SDKLogger.log";

    public static String projectFilesPath = "";
    public static String loggerFilePath = "";

    public static String getProjectFilesPath() {
        return TextUtils.isEmpty(projectFilesPath)?WINKER_FILE_DIR:projectFilesPath;
    }

    public static String getLoggerFilePath() {
        return TextUtils.isEmpty(loggerFilePath)?WINKER_FILE_DIR + LOGGER_FILE:loggerFilePath;
    }
}
