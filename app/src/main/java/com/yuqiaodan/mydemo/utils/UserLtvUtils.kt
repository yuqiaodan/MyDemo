package com.yuqiaodan.mydemo.utils

import android.util.Log
import com.yuqiaodan.mydemo.base.AppStorage
import java.text.DecimalFormat

/**
 * Created by qiaodan on 2021/11/29
 * Description:用户Ltv计算
 */
object UserLtvUtils {

    private const val TAG = "UserLtvUtils"

    /**
     *
    - 累积值，到达一个间隔门槛，向Gromore发送一次
    - 算法(单位：元)：使用pre_eCPM计算求和累加，∑(pre_ecpm)/100000
    - 门槛 间隔（单位：元）：0.3，0.5，0.7，1.0，1.5，2.0，2.5，3.0，3.0+
     * @param preEcpm 当前广告展示预估ecpm（每千次展示收入 单位分）
     */

    private val sendStep = listOf(0.3f, 0.5f, 0.7f, 1.0f, 1.5f, 2.0f, 2.5f, 3.0f)

    val format=DecimalFormat("0.00")

    fun flushLtv(preEcpm: String) {
        if (preEcpm in listOf("-1", "-2", "-3")) {
            return
        }
        val ecpm = preEcpm.toFloat()

        val value = ecpm / 100000

        //用户价值 单位 ：元
        val userLtv = AppStorage.userLtv + value

        AppStorage.userLtv = userLtv

        Log.d(TAG, "flushLtv 本次用户价值增加${format.format(value)} 用户总价值${format.format(AppStorage.userLtv)}")


        if (AppStorage.lastSendStep != sendStep.size - 1) {
            sendStep.forEachIndexed { index, fl ->
                if (fl <= userLtv) {
                    if (index != sendStep.size - 1) {
                        if (sendStep[index + 1] > userLtv) {
                            if (index != AppStorage.lastSendStep) {
                                AppStorage.lastSendStep = index
                                sendLtv(format.format(fl))
                            }
                        }
                    } else {
                        if (index != AppStorage.lastSendStep) {
                            AppStorage.lastSendStep = index
                            sendLtv(format.format(fl))
                        }
                    }
                }
            }
        } else {
            Log.d(TAG, "3.00 已经发送过了 不再上报")
        }
    }


    private fun sendLtv(ltv: String) {
        Log.d(TAG, "sendLtv: 发送一次 ltv ---> $ltv 当前上报门槛:${AppStorage.lastSendStep}")
    }


}