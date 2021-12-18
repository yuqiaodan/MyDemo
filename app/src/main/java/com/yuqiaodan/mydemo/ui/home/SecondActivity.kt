package com.yuqiaodan.mydemo.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.eventbus.BusTag
import com.yuqiaodan.mydemo.eventbus.BusWrapper
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
            EventBus.getDefault().register(this)
        findViewById<View>(R.id.btn_send_event).setOnClickListener {
            EventBus.getDefault().post(BusWrapper(BusTag.SHOW_MSG_FROM_NEXT_ACTIVITY, "这是一条来自SecondActivity的消息"))
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN, priority = 2, sticky = true)
    fun onReceiveMessage(msg: BusWrapper) {
        Log.d("BusTest", "SecondActivity onReceiveMessage:${msg.tag} ")
        if (msg.verifyMessage(BusTag.SHOW_MSG_FROM_NEXT_ACTIVITY)) {
            findViewById<TextView>(R.id.tv_event).text = msg.getContent()?.toString()
            //EventBus.getDefault().cancelEventDelivery(msg)
        }

        if (msg.verifyMessage(BusTag.SHOW_STICK_MSG_FROM_NEXT_ACTIVITY)) {
            findViewById<TextView>(R.id.tv_event).text = msg.getContent()?.toString()
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        //EventBus.getDefault().unregister(this)

    }


}