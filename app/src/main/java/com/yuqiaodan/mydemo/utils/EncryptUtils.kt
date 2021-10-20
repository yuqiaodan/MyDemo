package com.yuqiaodan.mydemo.utils

import android.text.TextUtils
import android.util.Base64
import android.util.Log

/**
 * Created by qiaodan on 2021/9/16
 * Description:
 */
object EncryptUtils {

    const val COMMON_KEY = "7b0035f6702308f0"

    /**
     * 加密字符串信息
     *
     * */
     fun encryptString(key: String, string: String): String {



        if (TextUtils.isEmpty(string)) {
            return ""
        }
        val stringBuilder = StringBuilder()
        string.forEachIndexed { index, char ->
            val code = char.code xor key.get(index % key.length).code
            stringBuilder.append(Char(code))
        }
        val result = Base64.encodeToString(stringBuilder.toString().toByteArray(), Base64.NO_WRAP)


        Log.d("EncryptUtils","加密原值：$string \n加密结果：$result")
        return result
    }

    /**
     * 解密字符串信息
     * */
     fun decodeString(key: String, string: String): String {
        if (TextUtils.isEmpty(string)) {
            return ""
        }
        val base64 = String(Base64.decode(string, Base64.NO_WRAP))
        val stringBuilder = StringBuilder()
        base64.forEachIndexed { index, char ->
            val code = char.code xor key.get(index % key.length).code
            stringBuilder.append(Char(code))
        }
        Log.d("EncryptUtils","解密原值：$string \n解密结果：$stringBuilder")
        return stringBuilder.toString()
    }
}