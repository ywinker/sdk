package com.wink.sdk.util;

import android.content.Context;
import android.text.TextUtils;

import com.wink.sdk.R;

import java.lang.ref.WeakReference;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2019/12/20
 * 公司：    winker
 * 作者：    yingzy
 */
public class StringUtil {

    /**
     * 填入数据的格式（用于检测） 1:手机号码 2：密码 3：验证码 4：图片验证码
     */
    public final static int FORMAT_PHONE = 1;
    public final static int FORMAT_PASSWORD = 2;
    public final static int FORMAT_VERIFY_CODE= 3;
    public final static int FORMAT_GRAPHIC_VERIFY_CODE= 4;

    private static WeakReference<Context> contextWeakReference = null;

    public static String txtformatDetection(Context context, int format, String txt){
        contextWeakReference = new WeakReference<>(context.getApplicationContext());
        String log = "";
        switch (format){
            case FORMAT_PHONE:
                if (TextUtils.isEmpty(txt)){
                    log = contextWeakReference.get().getString(R.string.phone_is_empty);
                }else if (!isMobileNO(txt)){
                    log = contextWeakReference.get().getString(R.string.phone_error);
                }
                break;
            case FORMAT_PASSWORD:
                if (TextUtils.isEmpty(txt)){
                    log = contextWeakReference.get().getString(R.string.password_is_empty);
                }else if (txt.length() < 5 || txt.length() > 16){
                    log = contextWeakReference.get().getString(R.string.password_length_error);
                }
                break;
            case FORMAT_VERIFY_CODE:
                if (TextUtils.isEmpty(txt)){
                    log = contextWeakReference.get().getString(R.string.code_is_empty);
                }
            case FORMAT_GRAPHIC_VERIFY_CODE:
                if (TextUtils.isEmpty(txt)){
                    log = contextWeakReference.get().getString(R.string.code_is_empty);
                }
                break;
        }

        return log;
    }

    /**
     * 验证手机格式
     */
    private static boolean isMobileNO(String mobiles) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return mobiles.matches(telRegex);
    }


}
