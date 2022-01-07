package com.yuqiaodan.mydemo.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.eventbus.BusEventId
import com.yuqiaodan.mydemo.eventbus.BusWrapper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SecondActivity : AppCompatActivity() {
    val TAG="Second"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        EventBus.getDefault().register(this)
        findViewById<View>(R.id.btn_send_event).setOnClickListener {
            EventBus.getDefault().post(BusWrapper(BusEventId.SHOW_MSG_FROM_NEXT_ACTIVITY, "这是一条来自SecondActivity的消息"))
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2, sticky = true)
    fun onReceiveMessage(msg: BusWrapper) {
        Log.d("BusTest", "SecondActivity 收到事件：${msg.id}  事件内容：${msg.getContent()?.toString()}")
        if (msg.verifyMessage(BusEventId.SHOW_MSG_FROM_NEXT_ACTIVITY)) {
            findViewById<TextView>(R.id.tv_event).text = msg.getContent()?.toString()
            //EventBus.getDefault().cancelEventDelivery(msg)
        }

        if (msg.verifyMessage(BusEventId.SHOW_STICK_MSG_FROM_NEXT_ACTIVITY)) {
            findViewById<TextView>(R.id.tv_event).text = msg.getContent()?.toString()
            //Log.d("BusTest", "黏性事件内容 ${msg.getContent()?.toString()}")
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        //EventBus.getDefault().unregister(this)

    }


    private fun requestSplashPerm(): Boolean {
        val splashPerms = arrayListOf<String>()
        splashPerms.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        splashPerms.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        splashPerms.add(Manifest.permission.READ_PHONE_STATE)
        splashPerms.add(Manifest.permission.ACCESS_FINE_LOCATION)
        splashPerms.add(Manifest.permission.INTERACT_ACROSS_PROFILES)

        if (hasAllPermissions(splashPerms.toArray(arrayOf("")))) {
            Log.d(TAG, "已经获得所有权限 不再请求")
            return false
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            Log.d(TAG, "SDK版本小于Android M 不需要请求")
            return false
        }
        requestPermissions(splashPerms.toArray(arrayOf("")), 1)
        return true

    }


    private fun hasAllPermissions(grantResults: Array<String>): Boolean {
        for (perm in grantResults) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d(TAG, "权限请求完毕 requestCode->$requestCode  permissions-->$permissions  grantResults-->$grantResults")
    }


}