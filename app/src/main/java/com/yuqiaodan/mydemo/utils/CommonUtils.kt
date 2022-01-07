package com.yuqiaodan.mydemo.utils

import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.util.Log
import com.yuqiaodan.mydemo.base.App
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by qiaodan on 2021/11/17
 * Description:公用工具方法 存储一下 作为一个记录
 */
class CommonUtils {

    val TAG = "CommonUtils"


    fun deleteApp(pkg: String, context: Context) {
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$pkg")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }


    private fun getFilePath() {
        val name = javaClass.name.split(".")


        val path = "${name[0]}.${name[1]}.${name[2]}"
        Log.d(TAG, "path  $path  ${name.size}")


        Log.d(TAG, "MainActivity 移除黏性事件")
    }


    private fun receiveScreen(){
        val pm = App.context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val keyguardManager = App.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        GlobalScope.launch(Dispatchers.Main) {
            while (true) {
                delay(5000L)
                Log.d(TAG, "屏幕是否点亮 --> ${pm.isInteractive}")
                Log.d(TAG, "用户是否上锁 --> ${keyguardManager.isKeyguardLocked}")
            }

        }
    }
}