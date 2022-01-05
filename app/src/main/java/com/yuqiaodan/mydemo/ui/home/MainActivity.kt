package com.yuqiaodan.mydemo.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.eventbus.BusEventId
import com.yuqiaodan.mydemo.eventbus.BusWrapper
import com.yuqiaodan.mydemo.study.ThreadStudy
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import com.yuqiaodan.mydemo.utils.EncryptUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        findViewById<View>(R.id.btn_camera_test).setOnClickListener(this)
    }

    var eventId=1
    @SuppressLint("InvalidWakeLockTag")
    @RequiresApi(Build.VERSION_CODES.Q)
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
                val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
                val keyguardManager = App.context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                GlobalScope.launch(Dispatchers.Main) {
                    while (true) {
                        delay(5000L)
                        Log.d(TAG, "屏幕是否点亮 --> ${pm.isInteractive}")
                        Log.d(TAG, "用户是否上锁 --> ${keyguardManager.isKeyguardLocked}")
                    }

                }
            }
            R.id.btn_test -> {
                //ThreadStudy().runCountDown(findViewById(R.id.tv_text_test))
                val time=System.currentTimeMillis()
               val id="StickEvent-$eventId"
                Log.d("BusTest", "MainActivity 发送黏性事件 id: $id  时间戳：$time")
                EventBus.getDefault().postSticky(BusWrapper(id, "这是一条来自MainActivity的黏性事件 时间戳： $time"))
               // EventBus.getDefault().re
                eventId++
            }

            R.id.btn_event_bus_test -> {
                //getFilePath()
                startActivity(Intent(this, SecondActivity::class.java))

                val str =
                    "C0cTCAERGxkBLQ8BQU5SUENXVAASFQdCB1JVF0JdEERJQRUAFS0KDBAAUl8pHUcCBAA6HAcIBlZKRwUPAwrmmI7ogo3ovrvmj5dESUEdAzoBHxYXER1HSAAEDwcVSVAWBAAfEQIXOQsCGRVHSEQGDBleBAgHCwcGHwwWFwoVWgIGFwUREBIRBgYJF00OBAJQG0kYVhEVAjkLAhkVR0hEV1BARea5oOeRtOePrUdPVhkWLRUcEAAVCFBcAwIYAwBeRBUCFxsEFQM6DRUdAFBcRwAbHUsIFUsAGBUEHEQYTw9SBAIWOg0VHQBQXEfmrYHkuKTnjZvlvo7ovqwxDCUdUklQDxY8BwkWBgMIQU4WBB4VAE9WAAQRDQQEES8LEwsAQU5SBh0LSwsXXgQcAhcMHRRLFAcWFwMZAxtEGE8PUgQCFjoNFR0AUFxH5qyF5p+j5omo5LusUEpHCgcvFgsVEQYZUl8UBwkQEVxHAgcGCBUXAC0IBA4RUl9QBQoOWjwAGx8QAhpeEh4cH00aFQQACwBNExEIFwUADQAVF1AbSRhWERUCOQsCGRVHSETlr6zlvqDmubHnkbZHXkQMECsDHAESAA5WSgMTChYGWFIVEwUOAhMVOhwHCAZWSkcRCQhNBhUJGwMDTQcABBEDSw4VAxEXFEsAGBUEHAMXQQlcHlAHFRMrHgQfA0dZVumcouaZgOWEmuiCmxIKEhnkubblr4RESUEdAzoBHxYXER1HSAAEDwcVSVAWBAAfEQIXOQsCGRVHSEQGDVoRCgQHCwcGHwwWFQ4EWhQRBQUREBcfCh5IERcGUhheHUcCBAA6HAcIBlZKR+ikjeeSuuimo+mjslZcRxsVOhANAxEXC0dZEhEJAQNJQQQRBhkHAgYrHgQfA0dZVhMKH0gWEFoRCxYUCgoQXgQAEgwAGBVLBA8BBhtSGF4dRwIEADocBwgGVkpH5YSa5rG35rmg55Gl5Lmn5a+GR15EDBArAxwBEgAOVkoDEwoWBlhSFRMFDgITFTocBwgGVkpHEQkITQUFBBwLDA1aFR0CAxcXVg04XkQHERUeAVBcRwwEAApQSkcAHBELHAMJQU5SHRsHCg4dUklQBQoOBBURGxIMDBpSX1BQR09WFAAEAwkMBBUXUFxHFwYFAFBKRwcRBgwWRF9BRxJRRQJcVRVFAUYEVAJEQ10XAwRSTBRQQgdUAUdHUUVESUEdHQAbRF9BVlxHHgkGAgAZChxEXxhWEQEWFAAQB1JfUERJQRUfDBwHCAZWSkdQSkcAHQQcUFxHQVhSARsVEREdExFQXEdBWFIJExIMQU5SR15ECQwaUl9QRElBGgUIEAMXQU5SR15EFQwdHgQfA0dZVlJJUBYXDAIZCxEDR1lWUklQFAoCEFJfUERJQQcEFxcDEUFOUkcPSkcOFRNHSERRV05GU0ggJllFRl9HVl9WNlJJUAsKBxEcR0hEChMEH0UTXlZBWFIKEw8BQU5SR15EChArBgAARF9BQ15UXFdHT1YADhVEX0EXHwhcBwsHBh8MFkgXBhBeCBMIAgxWXEcCFAobDVJfUAAEDwcVR15EFwwZUl9QBQoPGwIKAURJQQYfCC0QABFWSkdHSFdNRVJJUBQKDABSX1AABA8HFUdeRBYAFR46ExYVPBgZFgZEX0FFUklQFQwOVkpHFAcJEBFSSVAVFgoQUl9QRElBAAIEEQ06ABwRCxwDCUFOUgsHCglBWFITFxQ6ABsUAFBcR1JEQFdHRElBAhUXLQgEDhFSX1BXS1NaQlBQSkcVBB5HSEQDAhgDAFAb"

                EncryptUtils.decodeString("perfect", str)

            }

            R.id.btn_camera_test->{
                startActivity(Intent(this, CameraActivity::class.java))

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
        //Log.d("BusTest", "MainActivity onReceiveMessage:${msg.id} ")

       // EventBus.getDefault().removeAllStickyEvents()

        if (msg.verifyMessage(BusEventId.SHOW_MSG_FROM_NEXT_ACTIVITY)) {
            findViewById<TextView>(R.id.tv_event_bus_test).text = msg.getContent()?.toString()
        }
        if (msg.verifyMessage(BusEventId.SHOW_STICK_MSG_FROM_NEXT_ACTIVITY)) {
            //EventBus.getDefault().removeStickyEvent(msg)
            //Log.d("BusTest", "MainActivity 移除黏性事件")
        }


    }


    private fun getFilePath() {
        val name = javaClass.name.split(".")


        val path = "${name[0]}.${name[1]}.${name[2]}"
        Log.d(TAG, "path  $path  ${name.size}")


        Log.d(TAG, "MainActivity 移除黏性事件")
    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }


}