package com.wink.sdk.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 类名称
 * 内容摘要：
 * 修改备注：
 * 创建时间： 2019/12/20
 * 公司：    winker
 * 作者：    yingzy
 */
public class MD5 {
    /**
     * 32位MD5加密
     * @param content -- 待加密内容
     */
    public static String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException",e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10){
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }
}
