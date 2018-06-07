package org.greenleaf.utils;

import android.util.Base64;

/**
 * @desc: bese64的转换
 */

public class Base64Utils {
    /**
     * base64编码
     *
     * @param input
     * @return
     */
    public static byte[] encode(byte[] input) {
        return Base64.encode(input, Base64.DEFAULT);
    }

    /**
     * base64编码
     *
     * @param s
     * @return
     */
    public static String encode(String s) {
        return Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
    }

    /**
     * base64解码
     *
     * @param input
     * @return
     */
    public static byte[] decode(byte[] input) {
        return Base64.decode(input, Base64.DEFAULT);
    }

    /**
     * base64解码
     *
     * @param s
     * @return
     */
    public static String decode(String s) {
        return new String(Base64.decode(s, Base64.DEFAULT));
    }
}
