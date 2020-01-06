package com.wink.sdk.manager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import com.wink.sdk.local.LocalInfo;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.Objects;

/**
 * 类名称   UploadImgManager
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2019/12/26
 * 公司：    winker
 * 作者：    yingzy
 */
public class UploadImgManager {

    public final static int CAMERA_REQUEST_CODE = 0x0101;  //打开相机
    public final static int ALBUM_REQUEST_CODE = 0x0102;   //打开本地图片
    public final static int CROP_REQUEST_CODE = UCrop.REQUEST_CROP; //裁剪图片

    private WeakReference<Context> contextWeakReference = null;
    private WeakReference<Activity> activityWeakReference = null;

    private Uri imageUri = null;

    private static UploadImgManager intance;

    private UploadImgManager(){}

    public static synchronized UploadImgManager getIntance() {
        synchronized (UploadImgManager.class){
            if (intance == null) intance = new UploadImgManager();
        }

        return intance;
    }

    public void init(Context mContext){
        contextWeakReference = new WeakReference<>(mContext);
    }

    /**
     *  打开本地图片
     */
    public void openLocalPicture(Activity activity){
        activityWeakReference = new WeakReference<>(activity);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activityWeakReference.get().startActivityForResult(intent, ALBUM_REQUEST_CODE);
    }

    /**
     * 打开相机功能
     */
    public void opneCamera(Activity activity){
        activityWeakReference = new WeakReference<>(activity);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        String mTempPhotoPath = LocalInfo.WINKER_FILE_DIR + "temp.jpg";
        imageUri = FileProvider.getUriForFile(contextWeakReference.get(), contextWeakReference.get().getApplicationContext()
                .getPackageName() + ".my" + ".provider", new File(mTempPhotoPath));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activityWeakReference.get().startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

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
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
//            HYKJManager.getInstance().Toast("获取图片失败");
            return defaultBitmap;
        }

        return defaultBitmap;
    }
}
