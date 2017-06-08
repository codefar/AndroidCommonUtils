package org.davy.commons;

import java.lang.reflect.Method;

/**
 * 反射调用SystemProperties
 * Created by davy on 2017/6/7.
 */

public class SystemPropertiesHelper {
    private static Class<?> mClassType = null;
    private static Method mGetMethod = null;
    private static Method mGetIntMethod = null;

    private static void init() {
        try {
            if (mClassType == null) {
                mClassType = Class.forName("android.os.SystemProperties");
                mGetMethod = mClassType.getDeclaredMethod("get", String.class);
                mGetIntMethod = mClassType.getDeclaredMethod("getInt", String.class, int.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        init();
        String value = null;
        try {
            value = (String) mGetMethod.invoke(mClassType, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;
    }
}
