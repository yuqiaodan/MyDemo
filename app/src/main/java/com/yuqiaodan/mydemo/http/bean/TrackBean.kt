package com.yuqiaodan.mydemo.http.bean

/**
 * Created by qiaodan on 2021/11/10
 * Description:
 */
data class TrackBean(
    val event: String?,
    val param: Map<String, String>,
    val ts: Long?
)