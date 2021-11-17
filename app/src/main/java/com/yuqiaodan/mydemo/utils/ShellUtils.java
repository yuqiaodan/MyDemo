package com.yuqiaodan.mydemo.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Create by Payne on 2021/8/24
 * Description:
 **/
public class ShellUtils {
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

    public static boolean enableAdb(Context context){
        int enable = Settings.Secure.getInt(context.getContentResolver(), Settings.Global.ADB_ENABLED, 0);
        return enable > 0;
    }

    public static boolean isRoot(){
        String binPath = "/system/bin/su";
        String xBinPath = "/system/xbin/su";
        if (new File(binPath).exists() && isExecutable(binPath))
            return true;
        return new File(xBinPath).exists() && isExecutable(xBinPath);
    }

    public static boolean hasSimCard(Context context){
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int simState = manager.getSimState();
        boolean result = true;
        if (simState == TelephonyManager.SIM_STATE_ABSENT){
            result = false;
        }else if (simState == TelephonyManager.SIM_STATE_UNKNOWN){
            result = false;
        }
        return result;
    }

    private static boolean isExecutable(String filePath) {
        Process p = null;
        try {
            p = Runtime.getRuntime().exec("ls -l " + filePath);
            // 获取返回内容
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = in.readLine();
            if (str != null && str.length() >= 4) {
                char flag = str.charAt(3);
                if (flag == 's' || flag == 'x')
                    return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return false;
    }

}
