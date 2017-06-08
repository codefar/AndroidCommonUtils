package org.davy.commons;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;

import java.io.IOException;

/**
 * Created by davy on 2017/5/23.
 */

public class NetworkUtil {

    private static final String TAG = "NetworkUtil";

    private static final String ETHERNET_USE_STATIC_IP = "ethernet_use_static_ip";
    private static final String ETHERNET_STATIC_IP = "ethernet_static_ip";
    private static final String ETHERNET_STATIC_GATEWAY = "ethernet_static_gateway";
    private static final String ETHERNET_STATIC_NETMASK = "ethernet_static_netmask";
    private static final String ETHERNET_STATIC_DNS1 = "ethernet_static_dns1";
    private static final String ETHERNET_STATIC_DNS2 = "ethernet_static_dns2";
    private static final String ETH0 = "eth0";

    public static boolean isConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    public static boolean isEthernetConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_ETHERNET);
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    @WorkerThread
    public static boolean pingToken(@NonNull String urlNoHttpOrIp) {
        Runtime runtime = Runtime.getRuntime();

        try {
            Process e = runtime.exec("/system/bin/ping -c 1 " + urlNoHttpOrIp);
            int exitValue = e.waitFor();
            return exitValue == 0;
        } catch (InterruptedException | IOException var4) {
            //ignore
            return false;
        }
    }

    public static String getCurrentIp(Context context) {
        String ip = "";
        StringBuilder stringBuilder = new StringBuilder();
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) {
                if (Settings.System.getInt(context.getContentResolver(), ETHERNET_USE_STATIC_IP, 0) == 1) {
                    return getStaticEthInfo(context);
                } else {
                    return getEthInfoFromDhcp(context);
                }
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                if (dhcpInfo != null) {
                    stringBuilder.append(dhcpInfo.ipAddress).append(" ").append(Formatter.formatIpAddress(dhcpInfo.ipAddress)).append(" \n");
                    stringBuilder.append(dhcpInfo.netmask).append(" ").append(Formatter.formatIpAddress(dhcpInfo.netmask)).append(" \n");
                    stringBuilder.append(dhcpInfo.gateway).append(" ").append(Formatter.formatIpAddress(dhcpInfo.gateway)).append(" \n");
                    stringBuilder.append(dhcpInfo.ipAddress & dhcpInfo.netmask).append(" ").append(Formatter.formatIpAddress(dhcpInfo.ipAddress & dhcpInfo.netmask)).append(" \n");
                    stringBuilder.append(dhcpInfo.dns1).append(" ").append(Formatter.formatIpAddress(dhcpInfo.dns1)).append(" \n");
                    stringBuilder.append(dhcpInfo.dns2).append(" ").append(Formatter.formatIpAddress(dhcpInfo.dns2)).append(" \n");
                    ip = Formatter.formatIpAddress(dhcpInfo.ipAddress);
                } else {
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    if (wifiInfo != null) {
                        ip = Formatter.formatIpAddress(wifiInfo.getIpAddress());
                    }
                }
            } else {
                stringBuilder.append("未知连接类型" + networkInfo.getTypeName() + "\n\n");
            }
        } else {
            stringBuilder.append("无网络连接" + "\n\n");
        }
        Log.d("TAG", stringBuilder.toString());
        return ip;
    }

    public static String getStaticEthInfo(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        ContentResolver contentResolver = context.getContentResolver();
        stringBuilder.append(Settings.System.getString(contentResolver, ETHERNET_USE_STATIC_IP)).append(" \n");
        stringBuilder.append(Settings.System.getString(contentResolver, ETHERNET_STATIC_IP)).append(" \n");
        stringBuilder.append(Settings.System.getString(contentResolver, ETHERNET_STATIC_GATEWAY)).append(" \n");
        stringBuilder.append(Settings.System.getString(contentResolver, ETHERNET_STATIC_NETMASK)).append(" \n");
        stringBuilder.append(Settings.System.getString(contentResolver, ETHERNET_STATIC_DNS1)).append(" \n");
        stringBuilder.append(Settings.System.getString(contentResolver, ETHERNET_STATIC_DNS2)).append(" \n");
        Log.d("TAG", stringBuilder.toString());
        return Settings.System.getString(contentResolver, ETHERNET_STATIC_IP);
    }

    public static String getEthInfoFromDhcp(Context context) {
        String dhcpIp = "";
        StringBuilder stringBuilder = new StringBuilder();
        dhcpIp = SystemPropertiesHelper.get("dhcp." + ETH0 + ".ipaddress");
        stringBuilder.append("ipaddr:").append(dhcpIp).append(" \n");
        stringBuilder.append("gateway:").append(SystemPropertiesHelper.get("dhcp." + ETH0 + ".gateway")).append(" \n");
        stringBuilder.append("mask:").append(SystemPropertiesHelper.get("dhcp." + ETH0 + ".mask")).append(" \n");
        stringBuilder.append("dns1:").append(SystemPropertiesHelper.get("dhcp." + ETH0 + ".dns1")).append(" \n");
        stringBuilder.append("dns2:").append(SystemPropertiesHelper.get("dhcp." + ETH0 + ".dns2")).append(" \n");
        Log.d("TAG", stringBuilder.toString());
        return dhcpIp;
    }
}
