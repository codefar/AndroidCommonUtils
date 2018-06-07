package org.davy.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;

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
}
