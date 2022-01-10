package com.yuqiaodan.mydemo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.eventbus.BusEventId
import com.yuqiaodan.mydemo.eventbus.BusWrapper
import com.yuqiaodan.mydemo.utils.EncryptUtils
import com.yuqiaodan.mydemo.utils.time.CloudTimeUtils
import com.yuqiaodan.mydemo.utils.time.SNTPClient
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()


        EventBus.getDefault().register(this)


    }


    private fun initView() {

        findViewById<View>(R.id.btn_test).setOnClickListener(this)
        findViewById<View>(R.id.btn_event_bus_test).setOnClickListener(this)


    }

    @SuppressLint("InvalidWakeLockTag")
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onClick(v: View?) {
        when (v?.id) {


            R.id.btn_event_bus_test -> {
                //getFilePath()
                startActivity(Intent(this, FuncModeActivity::class.java))
                val str =
                    "C0cTCAERGxkBLQ8BQU5SUENXVAASFQdCB1JVF0JdEERJQRUAFS0KDBAAUl8pHUcCBAA6HAcIBlZKRwUPAwrmmI7ogo3ovrvmj5dESUEdAzoBHxYXER1HSAAEDwcVSVAWBAAfEQIXOQsCGRVHSEQGDBleBAgHCwcGHwwWFwoVWgIGFwUREBIRBgYJF00OBAJQG0kYVhEVAjkLAhkVR0hEV1BARea5oOeRtOePrUdPVhkWLRUcEAAVCFBcAwIYAwBeRBUCFxsEFQM6DRUdAFBcRwAbHUsIFUsAGBUEHEQYTw9SBAIWOg0VHQBQXEfmrYHkuKTnjZvlvo7ovqwxDCUdUklQDxY8BwkWBgMIQU4WBB4VAE9WAAQRDQQEES8LEwsAQU5SBh0LSwsXXgQcAhcMHRRLFAcWFwMZAxtEGE8PUgQCFjoNFR0AUFxH5qyF5p+j5omo5LusUEpHCgcvFgsVEQYZUl8UBwkQEVxHAgcGCBUXAC0IBA4RUl9QBQoOWjwAGx8QAhpeEh4cH00aFQQACwBNExEIFwUADQAVF1AbSRhWERUCOQsCGRVHSETlr6zlvqDmubHnkbZHXkQMECsDHAESAA5WSgMTChYGWFIVEwUOAhMVOhwHCAZWSkcRCQhNBhUJGwMDTQcABBEDSw4VAxEXFEsAGBUEHAMXQQlcHlAHFRMrHgQfA0dZVumcouaZgOWEmuiCmxIKEhnkubblr4RESUEdAzoBHxYXER1HSAAEDwcVSVAWBAAfEQIXOQsCGRVHSEQGDVoRCgQHCwcGHwwWFQ4EWhQRBQUREBcfCh5IERcGUhheHUcCBAA6HAcIBlZKR+ikjeeSuuimo+mjslZcRxsVOhANAxEXC0dZEhEJAQNJQQQRBhkHAgYrHgQfA0dZVhMKH0gWEFoRCxYUCgoQXgQAEgwAGBVLBA8BBhtSGF4dRwIEADocBwgGVkpH5YSa5rG35rmg55Gl5Lmn5a+GR15EDBArAxwBEgAOVkoDEwoWBlhSFRMFDgITFTocBwgGVkpHEQkITQUFBBwLDA1aFR0CAxcXVg04XkQHERUeAVBcRwwEAApQSkcAHBELHAMJQU5SHRsHCg4dUklQBQoOBBURGxIMDBpSX1BQR09WFAAEAwkMBBUXUFxHFwYFAFBKRwcRBgwWRF9BRxJRRQJcVRVFAUYEVAJEQ10XAwRSTBRQQgdUAUdHUUVESUEdHQAbRF9BVlxHHgkGAgAZChxEXxhWEQEWFAAQB1JfUERJQRUfDBwHCAZWSkdQSkcAHQQcUFxHQVhSARsVEREdExFQXEdBWFIJExIMQU5SR15ECQwaUl9QRElBGgUIEAMXQU5SR15EFQwdHgQfA0dZVlJJUBYXDAIZCxEDR1lWUklQFAoCEFJfUERJQQcEFxcDEUFOUkcPSkcOFRNHSERRV05GU0ggJllFRl9HVl9WNlJJUAsKBxEcR0hEChMEH0UTXlZBWFIKEw8BQU5SR15EChArBgAARF9BQ15UXFdHT1YADhVEX0EXHwhcBwsHBh8MFkgXBhBeCBMIAgxWXEcCFAobDVJfUAAEDwcVR15EFwwZUl9QBQoPGwIKAURJQQYfCC0QABFWSkdHSFdNRVJJUBQKDABSX1AABA8HFUdeRBYAFR46ExYVPBgZFgZEX0FFUklQFQwOVkpHFAcJEBFSSVAVFgoQUl9QRElBAAIEEQ06ABwRCxwDCUFOUgsHCglBWFITFxQ6ABsUAFBcR1JEQFdHRElBAhUXLQgEDhFSX1BXS1NaQlBQSkcVBB5HSEQDAhgDAFAb"
                EncryptUtils.decodeString("perfect", str)
            }

            R.id.btn_test -> {
                val time1=CloudTimeUtils.getInstance().cloudTimeNoAccurate
                Log.d(TAG, "直接使用同步方法获取：${ SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time1)}")


               /* GlobalScope.launch(Dispatchers.IO) {
                    delay(5000L)
                    val time3=CloudTimeUtils.getInstance().cloudTimeNoAccurate
                    Log.d(TAG, "5秒后重试 直接使用同步方法获取：${ SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time3)}")

                }*/

                Log.d(TAG, "---start request time---")
                CloudTimeUtils.getInstance().requestAsyncCloudTime(object : SNTPClient.Listener {
                    override fun onTimeReceived(rawDate: String?, timeStamp: Long) {
                        Log.d(TAG, "onTimeReceived: rawDate: $rawDate  timeStamp: $timeStamp")
                        Log.d(TAG, "onTimeReceived: 格式化: ${ SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timeStamp)}")
                        val time2=CloudTimeUtils.getInstance().cloudTimeNoAccurate
                        Log.d(TAG, "成功一次后再使用同步方法获取： ${ SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time2)}")
                    }

                    override fun onError(ex: Exception?) {
                        Log.d(TAG, "onError: ${ex?.message}")
                    }
                })




            }

        }
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


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)

    }


}