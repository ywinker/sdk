package com.wink.sdk.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2019/12/20
 * 公司：    winker
 * 作者：    yingzy
 */
public class BitmapUtil {

    public static Bitmap decodedBase64(String base64Url){
        byte[] base64Bitmap = Base64.decode(base64Url.split(",")[1], Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(base64Bitmap, 0, base64Bitmap.length);
    }
}
