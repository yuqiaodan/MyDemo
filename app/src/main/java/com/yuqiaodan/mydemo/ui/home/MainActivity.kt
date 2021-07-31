package com.yuqiaodan.mydemo.ui.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import com.yuqiaodan.mydemo.utils.MD5Utils
import java.security.SecureRandom

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }


    private fun initView() {
        findViewById<View>(R.id.btn_send_notify).setOnClickListener(this)
        findViewById<View>(R.id.btn_step_page).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send_notify -> {
                startActivity(Intent(this, NotifyActivity::class.java))


                var list= arrayListOf<String>()

                list.add("111")
                list.add("www")
                list.remove("222")

            }
            R.id.btn_step_page->{
                startActivity(Intent(this, StepActivity::class.java))
            }


        }
    }


    private fun androidIdTest() {
        Log.i("AndroidIdDemo", "原值:861868045944855  MD5加密:${MD5Utils.md5("861868045944855")}")
        Log.i("AndroidIdDemo", "原值:861868045944863  MD5加密:${MD5Utils.md5("861868045944863")}")
        val a = Settings.System.getString(App.context.contentResolver, Settings.Secure.ANDROID_ID)
        Log.i("AndroidIdDemo", "方式一原值:${a}  MD5加密:${MD5Utils.md5(a)}")
        val b = java.lang.Long.toHexString(SecureRandom().nextLong())

        Log.i("AndroidIdDemo", "方式二原值:${b}  MD5加密:${MD5Utils.md5(b)}")

    }


}