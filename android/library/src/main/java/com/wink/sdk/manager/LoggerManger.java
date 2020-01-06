package com.wink.sdk.manager;

import android.content.Context;
import android.os.Handler;

import com.wink.sdk.R;
import com.wink.sdk.local.LocalInfo;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 类名称    LoggerManger
 * 内容摘要： 日志管理类
 * 修改备注： first version
 * 创建时间： 2020/1/4
 * 公司：    winker
 * 作者：    yingzy
 */
public class LoggerManger {
    public static LoggerManger instance = null;

    private String path = LocalInfo.WINKER_FILE_DIR;

    private WeakReference<Context> contextWeakReference = null;

    private boolean writeLoggerEnable = true;
    private boolean isFirstLogger = false;

    private String logger = "";
    private RandomAccessFile raf = null;

    private LoggerManger(){}

    private MainHandler mainHandler = new MainHandler();

    public static synchronized LoggerManger getInstance(){
        synchronized (LoggerManger.class){
            if (instance == null){
                instance = new LoggerManger();
            }
        }

        return instance;
    }

    public void init(Context context, boolean writeLoggerEnable){
        contextWeakReference = new WeakReference<>(context);
        this.writeLoggerEnable = writeLoggerEnable;
    }

    public void writeSDKLoggerAddTime(int id, String logger){
        writeSDKLoggerAddTime(contextWeakReference.get().getString(id) + logger);
    }

    public void writeSDKLoggerAddTime(int id){
        writeSDKLoggerAddTime(contextWeakReference.get().getString(id));
    }

    public void writeSDKLoggerAddTime(String log){
        this.logger = "";

        SimpleDateFormat time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss  ", Locale.CHINA);
        String timeStr = time.format(new Date());
        this.logger = "\r\n " + timeStr + log;

        mainHandler.removeCallbacks(writeLoggerRunn);
        mainHandler.post(writeLoggerRunn);
    }

    private void writeSDKLogger(String logger){
        try {
            if (!createFile()) return;

            if (!createLogger()) return;

            File file = new File(path + LocalInfo.LOGGER_FILE);

            if (isFirstLogger){
                logger ="\r\n" + R.string.init_sdk_successfully + contextWeakReference.get().getString(R.string
                        .init_sdk_successfully);
            }

            raf = new RandomAccessFile(file, "rw");
            raf.seek(file.length());
            raf.write(logger.getBytes());

            if (raf != null)raf.close();

        }catch (Exception e){
            e.printStackTrace();
            logger = "";
        }

        logger = "";
    }

    private boolean createLogger() throws Exception{
        File loggerFile = new File(path + LocalInfo.LOGGER_FILE);

        if (loggerFile.exists()){
            return true;
        }

        return loggerFile.createNewFile();
    }

    private boolean createFile() throws Exception{
        File baseFile = new File(path);

        if (baseFile.exists()){

            return true;
        }else {
            isFirstLogger = true;
        }

       return baseFile.mkdir();
    }

    public void setPath(String path) {
        this.path = path;
    }

    private Runnable writeLoggerRunn = new Runnable() {
        @Override
        public void run() {
            writeSDKLogger(logger);
        }
    };

    private static class MainHandler extends Handler{}
}
