package com.yuqiaodan.mydemo.base

import com.tencent.mmkv.MMKV

/**
 * Created by qiaodan on 2021/8/2
 * Description:
 */
object AppStorage {
    val mmkv = MMKV.defaultMMKV()


    var todayStep: Int
        set(value) {
            mmkv.encode("today_step", value)
        }
        get() = mmkv.decodeInt("today_step", 0)


    var systemStep: Int
        set(value) {
            mmkv.encode("system_step", value)
        }
        get() = mmkv.decodeInt("system_step", 0)
}