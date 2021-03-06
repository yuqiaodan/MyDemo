package com.yuqiaodan.mydemo.eventbus

class BusWrapper(val id: String, private val content: Any?) {

    /**
     * 验证通信内容是否是想要接受的
     */
    fun verifyMessage(msgId: String): Boolean = id == msgId

    /**
     * 根据messageTag获取通信内容实体
     */
    fun getContent(): Any? {
        return content
    }
}