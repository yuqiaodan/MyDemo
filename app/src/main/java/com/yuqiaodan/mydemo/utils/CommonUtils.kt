package com.yuqiaodan.mydemo.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * Created by qiaodan on 2021/11/17
 * Description:公用工具方法 存储一下 作为一个记录
 */
class CommonUtils {



    fun deleteApp(pkg: String, context: Context) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$pkg")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}