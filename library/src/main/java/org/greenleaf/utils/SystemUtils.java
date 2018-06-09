package org.greenleaf.utils;

import android.os.Build;

/**
 * 获取系统信息工具类
 */
public class SystemUtils {

    /**
     * ART
     */
    public static boolean isART() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("2");
    }

    /**
     * DALVIK
     */
    public static boolean isDalvik() {
        final String vmVersion = System.getProperty("java.vm.version");
        return vmVersion != null && vmVersion.startsWith("1");
    }

    /**
     * The brand
     */
    public static String getBrand() {
        return Build.BRAND;
    }

    /**
     * The board
     */
    public static String getBoard() {
        return Build.BOARD;
    }

    /**
     * The end-user-visible name for the end product, like "MI-ONE Plus".
     */
    public static String getModel() {
        return Build.MODEL;
    }

    /**
     * Either a changelist number, or a label like "JZO54K".
     */
    public static String getID() {
        return Build.ID;
    }

    /**
     * The user-visible version string, like "4.1.2".
     */
    public static String getVersionRelease() {
        return Build.VERSION.RELEASE;
    }

    /**
     * The user-visible SDK version of the framework.
     */
    public static int getVersionSDK() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * A string that uniquely identifies this build. Do not attempt to parse this value.
     */
    public static String getFingerPrint() {
        return Build.FINGERPRINT;
    }

    /**
     * The name of the overall product, like "mione_plus".
     */
    public static String getProduct() {
        return Build.PRODUCT;
    }

    /**
     * The manufacturer of the product/hardware, like "Xiaomi".
     */
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    /**
     * The name of the industrial design, like "mione_plus".
     */
    public static String getDevice() {
        return Build.DEVICE;
    }

    /**
     * The name of the instruction set (CPU type + ABI convention) of native code, like "armeabi-v7a".
     */
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }

    /**
     * The name of the second instruction set (CPU type + ABI convention) of native code, like "armeabi".
     */
    public static String getCpuAbi2() {
        return Build.CPU_ABI2;
    }

    /**
     * A build ID string meant for displaying to the user, like "JZO54K".
     */
    public static String getDisplay() {
        return Build.DISPLAY;
    }
}
