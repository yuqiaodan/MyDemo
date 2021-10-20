package com.yuqiaodan.mydemo.http.bean

/**
 * Created by qiaodan on 2021/9/13
 * Description:
 */
data class CityBean(
    val name: String?,
    val code: String?,
    val msg: CityMsg?,
    val mail: String?
)



data class CityMsg(
    val opr:String?,
    val loca:String?
)
