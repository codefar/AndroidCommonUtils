package org.davy.utils;

import java.util.Locale;

public class ByteUtil {

    private final static byte[] EMPTY_BYTES = new byte[]{};
    private final static char[] mChars = "0123456789ABCDEF".toCharArray();

    public static String bytes2HexStr(byte[] bytes, int start, int length) {

        if (bytes == null || bytes.length == 0 || start > bytes.length || start + length > bytes.length) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for (int i = start; i < start + length; i++) {
            builder.append(mChars[(bytes[i] & 0xFF) >> 4]);
            builder.append(mChars[bytes[i] & 0x0F]);
            builder.append(" ");
        }
        return builder.toString();
    }

    public static String bytes2HexStr(byte[] bytes) {

        if (bytes == null || bytes.length == 0) {
            return "";
        }

        return bytes2HexStr(bytes, 0, bytes.length);
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static byte[] str2Bytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return EMPTY_BYTES;
        }

        hexString = hexString.toUpperCase(Locale.ENGLISH);
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    public static boolean isBitSet(byte number, int bit) {
        if (bit < 0 || bit > 7) {
            throw new IllegalArgumentException("bit " + bit + " not in range of  0 - 7");
        }
        return (number >> bit & 0x01) == 0x01;
    }
}
