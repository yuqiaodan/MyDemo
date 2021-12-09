package com.yuqiaodan.mydemo.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log

/**
 * Created by qiaodan on 2021/11/17
 * Description:电池工具类 获取各种电池信息
 * 参考博客：https://www.jianshu.com/p/0ad72190eac6
 */
object BatteryUtils {
    val TAG="BatteryUtils"
    /**
     * 获取电池容量
     * @return 电池容量 单位mAh
     * 源头文件:frameworks/base/core/res\res/xml/power_profile.xml
     * Java 反射文件：frameworks\base\core\java\com\android\internal\os\PowerProfile.java
     * */
    fun getBatteryCapacity(context: Context):Double{
        val mPowerProfile: Any
        var batteryCapacity = 0.0
        val POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile"
        try {
            mPowerProfile = Class.forName(POWER_PROFILE_CLASS)
                .getConstructor(Context::class.java)
                .newInstance(context)
            batteryCapacity = Class
                .forName(POWER_PROFILE_CLASS)
                .getMethod("getBatteryCapacity")
                .invoke(mPowerProfile) as Double
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return batteryCapacity
    }


    private var batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.action) {
                //电源接通
                Intent.ACTION_POWER_CONNECTED -> {
                    Log.d(TAG, "电源接通")

                }
                //电源断开
                Intent.ACTION_POWER_DISCONNECTED -> {
                    Log.d(TAG, "电源断开")

                }

                //电量满
                Intent.ACTION_BATTERY_OKAY -> {
                    Log.d(TAG, "电量满")

                }
                //电量过低
                Intent.ACTION_BATTERY_LOW -> {
                    Log.d(TAG, "电量过低")
                }
                //电池电量变化
                Intent.ACTION_BATTERY_CHANGED -> {
                    val power = intent.getIntExtra("level", 0)
                    val tem = intent.getIntExtra("temperature", 0) / 10
                    Log.d(TAG, "电池电量变化  电量:${power}  温度${tem}")
                }
            }
        }
    }

    /**
     * 打印电池相关信息
     * */
    private fun logBatteryInfo(context: Context) {
        //注册广播 被动获取电池信息
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW)
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY)
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        val intent = context.registerReceiver(batteryReceiver, intentFilter)
        val isCharging = intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0) != 0
        Log.d(TAG, "判断电源是否接通 $isCharging")

        //通过注册广播得到的intent 主动获取当前电池信息

        //当前电量
        val level = intent?.getIntExtra("level", 0)
        //总电量
        val scale = intent?.getIntExtra("scale", 0)
        //电池技术支持
        val technology = intent?.getStringExtra("technology")
        //电池状态
        val status = intent?.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN)
        //充电方式
        val plugged = intent?.getIntExtra("plugged", 0)
        //电池健康程度
        val health = intent?.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN)
        //电池电压
        val voltage = intent?.getIntExtra("voltage", 0)
        //电池温度
        val temperature = (intent?.getIntExtra("temperature", 0) ?: 0) / 10

        //电池总容量 单位mAh
        val capacity = getBatteryCapacity(context)

        val logStr =
            "当前电量百分比:$level %\n" +
                    "总电量百分比:$scale %\n" +
                    "电池技术支持:$technology \n" +
                    "电池状态:$status \n" +
                    "充电方式:$plugged \n" +
                    "电池健康程度:$health \n" +
                    "电池电压:$voltage mV \n" +
                    "电池温度:$temperature \n" +
                    "电池总容量:${capacity}\n"

        Log.d(TAG, logStr)

    }
}