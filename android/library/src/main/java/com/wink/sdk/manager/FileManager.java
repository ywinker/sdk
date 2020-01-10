package com.wink.sdk.manager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;

import com.wink.sdk.R;
import com.wink.sdk.listener.Observer;
import com.wink.sdk.local.LocalInfo;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Objects;

/**
 * 类名称     FileManager
 * 内容摘要：  文件生成和管理
 * 修改备注：  first version
 * 创建时间：  2020/1/9
 * 公司：     winker
 * 作者：     yingzy
 */
public class FileManager implements Observer {

    public final static int CAMERA_REQUEST_CODE = 0x0101;  //打开相机
    public final static int ALBUM_REQUEST_CODE = 0x0102;   //打开本地图片
    public final static int CROP_REQUEST_CODE = UCrop.REQUEST_CROP; //裁剪图片

    private static FileManager instance = null;

    private WeakReference<Activity> activityWeakReference = null;

    private Bitmap ShootScreenBitmap = null;

    private Uri imageUri = null;

    private FileManager(){}

    public static synchronized FileManager getInstance(){
        synchronized (FileManager.class){
            if (instance == null){
                instance = new FileManager();
            }
        }

        return instance;
    }

    /**
     * 截取控件相关页面
     * @param view 所要截取的控件
     * @param path 保存的文件名
     */
    public boolean shootViewScreen(View view , String path){
        LoggerManger.getInstance().writeSDKLoggerAddTime(R.string.shoot_screen);

        view.setDrawingCacheEnabled(true);
        ShootScreenBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return saveLocation(path);
    }

    /**
     * 截取app全屏
     * @param path 保存的文件名
     */
    public boolean shootFullScreen(String path){
        LoggerManger.getInstance().writeSDKLoggerAddTime(R.string.shoot_screen);

        View decorView = activityWeakReference.get().getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        ShootScreenBitmap = Bitmap.createBitmap(decorView.getDrawingCache());
        decorView.setDrawingCacheEnabled(false);

        return saveLocation(path);
    }

    /**
     * 保存文件
     * @param path 保存的文件名
     */
    private boolean saveLocation(String path) {
        try {
            if (createProjectFile()){
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ShootScreenBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

                byte[] bytes = byteArrayOutputStream.toByteArray();

                OutputStream outputStream = new FileOutputStream(new File(LocalInfo.getProjectFilesPath() + path));
                outputStream.write(bytes, 0, bytes.length);
                outputStream.flush();
                outputStream.close();

                ShootScreenBitmap.recycle();
            }
        }catch (Exception e){
            ShootScreenBitmap.recycle();
            LoggerManger.getInstance().writeSDKLoggerAddTime(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     *  打开本地图片
     */
    public void openLocalPicture(Activity activity){
        activityWeakReference = new WeakReference<>(activity);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activityWeakReference.get().startActivityForResult(intent, ALBUM_REQUEST_CODE);
        LoggerManger.getInstance().writeSDKLoggerAddTime(R.string.open_local_picture);
    }

    /**
     * 打开相机功能
     * 待补充
     */

    /**
     * 裁剪图片
     */
    public void toCropHandler(Uri uri, String path){
        UCrop.of(uri, Uri.fromFile(new File(path)))
                .withAspectRatio(1, 1).withMaxResultSize(150, 150).start(activityWeakReference.get());
    }

    /**
     * 裁剪相机图片
     */
    public void toCropHandler(String path){
        if (imageUri != null) UCrop.of(imageUri, Uri.fromFile(new File(path)))
                .withAspectRatio(1, 1).withMaxResultSize(150, 150).start(activityWeakReference.get());
    }

    /**
     * 获取Urcp裁剪后的图片
     */
    public Bitmap getCropPicture(Intent data, Bitmap defaultBitmap){
        String imagePath  ;

        if (null != UCrop.getOutput(data) && null != (imagePath = Objects.requireNonNull(UCrop.getOutput(data)).toString())){
            try {
                InputStream is = new URL(imagePath).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }else {

            return defaultBitmap;
        }

        return defaultBitmap;
    }

    /**
     * 判断是否生成项目文件夹目录
     * @throws Exception
     */
    public boolean createProjectFile() throws Exception{
        File baseFile = new File(LocalInfo.getProjectFilesPath());

        if (baseFile.exists()){
            return true;
        }

        return baseFile.mkdir();
    }

    @Override
    public void update(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
    }
}
