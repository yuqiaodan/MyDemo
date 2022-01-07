package com.yuqiaodan.mydemo.utils.time;

import android.os.SystemClock;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 获取服务端时间
 *
 */
public class CloudTimeUtils {

    private static CloudTimeUtils mCloudTimeUtils = new CloudTimeUtils();

    private SNTPClient mSNTPClient = new SNTPClient();

    private static final String CN_NTP_URL = "cn.pool.ntp.org";

    private static final String OS_NTP_URL = "pool.ntp.org";

    private static final int TIME_OUT = 5000;

    private TimeZone china = TimeZone.getTimeZone("GMT+:08:00");

    private TimeZone os = TimeZone.getDefault();

    private static long lastNTPTimeStamp = -1;

    private static long lastBootElapsedRealtime = -1;

    private ExecutorService executors;

    private CloudTimeUtils() {
        executors = Executors.newSingleThreadExecutor();
    }

    public static CloudTimeUtils getInstance() {
        return mCloudTimeUtils;
    }

    /**
     * 重新获取NTP时间
     * @param _listener
     */
    public void reRequestAsyncCloudTime(SNTPClient.Listener _listener) {
        executors.execute(() -> {
            long ntpTime = getNtpTime();
            if (_listener == null) {
                return;
            }
            if (ntpTime > 0) {
                Pair<String, Long> conversionDate = conversionDate(china, ntpTime);
                _listener.onTimeReceived(conversionDate.first, conversionDate.second);
            } else {
                _listener.onError(new IllegalArgumentException());
            }
        });
    }

    /**
     * 获取NTP时间，第一次是重新获取，其他时候根据手机启动时间做的
     * @param _listener
     */
    public void requestAsyncCloudTime(SNTPClient.Listener _listener) {
        if (lastNTPTimeStamp == -1) {
            reRequestAsyncCloudTime(_listener);
        } else {
            if (_listener == null) {
                return;
            }
            long estimateServerTime = (SystemClock.elapsedRealtime() - lastBootElapsedRealtime) + lastNTPTimeStamp;
            Pair<String, Long> conversionDate = conversionDate(china, estimateServerTime);
            _listener.onTimeReceived(conversionDate.first, conversionDate.second);
            if (estimateServerTime - lastNTPTimeStamp >= 3600 * 1000) {
                executors.execute(this::getNtpTime);
            }
        }
    }

    /**
     * 获取服务器事情，同步线程，非精准
     * @return
     */
    public long getCloudTimeNoAccurate(){
        if (lastNTPTimeStamp == -1) {
            executors.execute(this::getNtpTime);
            return lastNTPTimeStamp;
        }
        long estimateServerTime = (SystemClock.elapsedRealtime() - lastBootElapsedRealtime) + lastNTPTimeStamp;
        Pair<String, Long> conversionDate = conversionDate(china, estimateServerTime);
        if (estimateServerTime - lastNTPTimeStamp >= 3600 * 1000) {
            executors.execute(this::getNtpTime);
        }
        return conversionDate.second;
    }

    private long getNtpTime() {
        if (mSNTPClient.requestTime(CN_NTP_URL, TIME_OUT)) {
            long nowAsPerDeviceTimeZone = mSNTPClient.getNtpTime();
            lastNTPTimeStamp = nowAsPerDeviceTimeZone;
            lastBootElapsedRealtime = SystemClock.elapsedRealtime();
            return lastNTPTimeStamp;
        }
        return -1;
    }

    private Pair<String, Long> conversionDate(TimeZone _timeZone, long nowServerTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        sdf.setTimeZone(_timeZone);
        String rawDate = sdf.format(nowServerTime);
        Pair<String, Long> pair = new Pair<>(rawDate, nowServerTime);
        return pair;
    }

}
