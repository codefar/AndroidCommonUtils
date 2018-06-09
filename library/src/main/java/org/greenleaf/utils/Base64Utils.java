package org.greenleaf.utils;

import android.util.Base64;

/**
 *  bese64的转换
 */

public class Base64Utils {

    public static byte[] encode(byte[] input) {
        return Base64.encode(input, Base64.DEFAULT);
    }

    public static String encode(String s) {
        return Base64.encodeToString(s.getBytes(), Base64.DEFAULT);
    }

    public static byte[] decode(byte[] input) {
        return Base64.decode(input, Base64.DEFAULT);
    }

    public static String decode(String s) {
        return new String(Base64.decode(s, Base64.DEFAULT));
    }
}
