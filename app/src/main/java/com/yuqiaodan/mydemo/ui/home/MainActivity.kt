package com.yuqiaodan.mydemo.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.eventbus.BusTag
import com.yuqiaodan.mydemo.eventbus.BusWrapper
import com.yuqiaodan.mydemo.study.ThreadStudy
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()


        EventBus.getDefault().register(this)


    }


    private fun initView() {
        findViewById<View>(R.id.btn_send_notify).setOnClickListener(this)
        findViewById<View>(R.id.btn_step_page).setOnClickListener(this)
        findViewById<View>(R.id.btn_update).setOnClickListener(this)
        findViewById<View>(R.id.rq_prem).setOnClickListener(this)
        findViewById<View>(R.id.btn_test).setOnClickListener(this)
        findViewById<View>(R.id.btn_event_bus_test).setOnClickListener(this)
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send_notify -> {
                Log.d(TAG, "btn_send_notify")
                startActivity(Intent(this, NotifyActivity::class.java))
            }


            R.id.btn_step_page -> {
                startActivity(Intent(this, StepActivity::class.java))
            }

            R.id.rq_prem -> {
                Log.d(TAG, "rq_prem")
                requestSplashPerm()
            }

            R.id.btn_update -> {

            }
            R.id.btn_test -> {
                ThreadStudy().runCountDown(findViewById(R.id.tv_text_test))
                EventBus.getDefault().postSticky(BusWrapper(BusTag.SHOW_STICK_MSG_FROM_NEXT_ACTIVITY, "这是一条来自MainActivity的黏性事件"))
            }

            R.id.btn_event_bus_test -> {
                startActivity(Intent(this, SecondActivity::class.java))
            }

        }
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


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 1, sticky = true)
    fun onReceiveMessage(msg: BusWrapper) {
        Log.d("BusTest", "MainActivity onReceiveMessage:${msg.tag} ")
        if (msg.verifyMessage(BusTag.SHOW_MSG_FROM_NEXT_ACTIVITY)) {
            findViewById<TextView>(R.id.tv_event_bus_test).text = msg.getContent()?.toString()
        }
        if (msg.verifyMessage(BusTag.SHOW_STICK_MSG_FROM_NEXT_ACTIVITY)) {
            EventBus.getDefault().removeStickyEvent(msg)
            Log.d("BusTest", "MainActivity 移除黏性事件")
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }


}