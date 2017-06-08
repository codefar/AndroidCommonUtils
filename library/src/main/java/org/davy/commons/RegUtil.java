package org.davy.commons;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by davy on 2017/5/18.
 */

public class RegUtil {
    public static boolean isIp(String str) {
        if (str == null || str.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static String getMac(String source) {
        Pattern pattern = Pattern.compile("[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}-[A-Fa-f0-9]{2}");
        Matcher matcher = pattern.matcher(source);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
