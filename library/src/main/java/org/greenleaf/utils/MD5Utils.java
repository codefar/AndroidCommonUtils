package org.greenleaf.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * MD5工具类
 */

public class MD5Utils {

    private MD5Utils() {
    }

    public static String encode(String s) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();
            return ByteUtil.bytes2HexStr(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            //ignore
        }
        return "";
    }

    public static String encode(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            byte messageDigest[] = digest.digest();
            return ByteUtil.bytes2HexStr(messageDigest);
        } catch (NoSuchAlgorithmException e) {
            //ignore
        }
        return "";
    }

}
