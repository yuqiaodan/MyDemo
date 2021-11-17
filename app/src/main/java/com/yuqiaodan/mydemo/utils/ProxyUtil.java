package com.yuqiaodan.mydemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

import java.net.NetworkInterface;
import java.util.Collections;
import java.util.Enumeration;

/**
 * Created by qiaodan on 2021/11/11
 * Description:
 */
public class ProxyUtil {

    /**
     * 获取电池容量 mAh
     *
     * 源头文件:frameworks/base/core/res\res/xml/power_profile.xml
     *
     * Java 反射文件：frameworks\base\core\java\com\android\internal\os\PowerProfile.java
     */
    public static String getBatteryCapacity(Context context) {
        Object mPowerProfile;
        double batteryCapacity = 0;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";

        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class)
                    .newInstance(context);

            batteryCapacity = (double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getBatteryCapacity")
                    .invoke(mPowerProfile);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return String.valueOf(batteryCapacity + " mAh");
    }


    /*
     * 判断设备 是否使用代理上网
     * */
    public static boolean isWifiProxy(Context context) {
        final boolean IS_ICS_OR_LATER = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        String proxyAddress;
        int proxyPort;
        if (IS_ICS_OR_LATER) {
            //设备大于android4.0
            proxyAddress = System.getProperty("http.proxyHost");
            String portStr = System.getProperty("http.proxyPort");
            proxyPort = Integer.parseInt((portStr != null ? portStr : "-1"));
        } else {
            //设备小于android4.0
            proxyAddress = android.net.Proxy.getHost(context);
            proxyPort = android.net.Proxy.getPort(context);
        }
        return (!TextUtils.isEmpty(proxyAddress)) && (proxyPort != -1);
    }


    /*判断VPN方法*/
    public static boolean isVpnUsed(Context context) {
        boolean isVpn = false;
        //检查网络是否链接
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        //判断时候有网络
        if (networkInfo != null) {
            try {
                Enumeration<NetworkInterface> niList = NetworkInterface.getNetworkInterfaces();
                if (niList != null) {
                    for (NetworkInterface intf : Collections.list(niList)) {
                        if (!intf.isUp() || intf.getInterfaceAddresses().size() == 0) {
                            continue;
                        }
                        if ("tun0".equals(intf.getName()) || "ppp0".equals(intf.getName())) {
                            isVpn = true;
                        }
                    }
                    if (!isVpn) {
                        networkInfo.getTypeName();

                    }
                }
            } catch (Throwable ignored) {
            }
        }
        return isVpn;
    }


}
