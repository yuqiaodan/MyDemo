@file:Suppress("MemberVisibilityCanPrivate", "unused")

package com.yuqiaodan.mydemo.utils

import android.text.TextUtils
import java.security.MessageDigest

/**
 * Note: MD5检验工具类
 *
 * @author kachem [2016/7/21]
 */
object MD5Utils {

    private val HEX_DIGITS = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f')

    /**
     * 取得字符串的md5值(32位)
     */
    fun md5(str: String?): String {
        if (TextUtils.isEmpty(str) || str == null) {
            return ""
        }
        return toHexString(md5(str.toByteArray()))
    }

    fun md5ToString(data: ByteArray) = toHexString(md5(data))

    /**
     * 取得data的MD5值(32位)
     */
    fun md5(data: ByteArray) = try {
        MessageDigest.getInstance("MD5")?.run {
            update(data)
            digest()
        }
    } catch (e: Exception) {
        null
    }


    private fun toHexString(data: ByteArray?): String {
        if (data == null || data.isEmpty()) {
            return ""
        }

        val sb = StringBuilder(data.size * 2)
        for (b in data) {
            sb.append(HEX_DIGITS[(b.toInt() and 0xff).shr(4)])
            sb.append(HEX_DIGITS[(b.toInt() and 0x0f)])
        }
        return sb.toString()
    }

    fun getSHA1(value: String): String {
        return try {
            val digest = MessageDigest.getInstance("SHA-1")
            digest.update(value.toByteArray())
            val data = digest.digest()
            val sb = StringBuilder()
            var shaHex: String
            for (b in data) {
                shaHex = Integer.toHexString(b.toInt() and 0xFF)
                if (shaHex.length < 2) {
                    sb.append(0)
                }
                sb.append(shaHex)
            }
            sb.toString()
        } catch (e: Exception) {
            ""
        }
    }

}
