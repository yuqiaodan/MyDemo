package com.yuqiaodan.mydemo.base;

import android.app.Activity;
import android.content.Context;
import android.provider.Settings;

import com.bytedance.msdk.api.UserInfoForSegment;
import com.bytedance.msdk.api.v2.GMAdConfig;
import com.bytedance.msdk.api.v2.GMAdConstant;
import com.bytedance.msdk.api.v2.GMConfigUserInfoForSegment;
import com.bytedance.msdk.api.v2.GMMediationAdSdk;
import com.bytedance.msdk.api.v2.GMPangleOption;
import com.bytedance.msdk.api.v2.GMPrivacyConfig;

import java.util.HashMap;
import java.util.Map;


/**
 * 可以用一个单例来保存TTAdManager实例，在需要初始化sdk的时候调用
 */
public class GMAdManagerHolder {

    private static boolean sInit;

    public static void init(Context context) {
        doInit(context);
    }

    public static void initUnitySdkBanner(Activity activity) {
        GMMediationAdSdk.initUnityForBanner(activity);
    }


    //step1:接入网盟广告sdk的初始化操作，详情见接入文档和穿山甲平台说明
    private static void doInit(Context context) {
        if (!sInit) {
            GMMediationAdSdk.initialize(context, buildV2Config(context));
            sInit = true;
        }
    }

    public static GMAdConfig buildV2Config(Context context) {
        GMConfigUserInfoForSegment userInfo = new GMConfigUserInfoForSegment();
        userInfo.setUserId("msdk-demo");
        userInfo.setGender(UserInfoForSegment.GENDER_MALE);
        userInfo.setChannel("msdk-channel");
        userInfo.setSubChannel("msdk-sub-channel");
        userInfo.setAge(999);
        userInfo.setUserValueGroup("msdk-demo-user-value-group");

        Map<String, String> customInfos = new HashMap<>();
        customInfos.put("aaaa", "test111");
        customInfos.put("bbbb", "test222");
        userInfo.setCustomInfos(customInfos);
        return new GMAdConfig.Builder()
                .setAppId("5220047")
                .setAppName("APP测试媒体")
                .setDebug(true)
                .setPublisherDid(getAndroidId(context))
                .setOpenAdnTest(false)
                .setConfigUserInfoForSegment(userInfo)
                .setPangleOption(new GMPangleOption.Builder()
                        .setIsPaid(false)
                        .setTitleBarTheme(GMAdConstant.TITLE_BAR_THEME_DARK)
                        .setAllowShowNotify(true)
                        .setAllowShowPageWhenScreenLock(true)
                        .setDirectDownloadNetworkType(GMAdConstant.NETWORK_STATE_WIFI, GMAdConstant.NETWORK_STATE_3G)
                        .setIsUseTextureView(true)
                        .setNeedClearTaskReset()
                        .setKeywords("")
                        .build())
                .setPrivacyConfig(new GMPrivacyConfig() {
                    @Override
                    public boolean isCanUseLocation() {
                        return true;
                    }

                    @Override
                    public boolean isCanUsePhoneState() {
                        return true;
                    }

                    @Override
                    public boolean isCanUseWriteExternal() {
                        return true;
                    }
                })
                .build();
    }

    public static String getAndroidId(Context context) {
        String androidId = null;
        try {
            androidId = Settings.System.getString(context.getContentResolver(), Settings.System.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return androidId;
    }

}
