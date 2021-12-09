package com.yuqiaodan.mydemo.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.base.AppStorage
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import com.yuqiaodan.mydemo.utils.EncryptUtils
import com.yuqiaodan.mydemo.utils.MD5Utils

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
        findViewById<View>(R.id.btn_init_sdk).setOnClickListener(this)
        findViewById<View>(R.id.btn_update).setOnClickListener(this)
        findViewById<View>(R.id.rq_prem).setOnClickListener(this)
        findViewById<View>(R.id.btn_test).setOnClickListener(this)
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

            R.id.btn_init_sdk -> {
                App.instance.initThirdSDK()
            }

            R.id.rq_prem -> {
                Log.d(TAG, "rq_prem")

                requestSplashPerm()
            }

            R.id.btn_update -> {
                Log.d(TAG, "update")
                AppStorage.userLtv = 0f
                AppStorage.lastSendStep = -1
            }
            R.id.btn_test -> {
                //deletApp("com.android.light.cow",App.context)
                //ApkUtils.uninstall("com.android.light.cow")
                //testTrackJson()
                //devIdTest()
                /*Log.d(TAG, "是否打开VPN ${ProxyUtil.isVpnUsed(App.context)}")
                Log.d(TAG, "是否使用网络代理 ${ProxyUtil.isWifiProxy(App.context)}")*/
                //Log.d(TAG, "电池总容量 ${ BatteryUtils.getBatteryCapacity(App.context)}")

                /* for (i in 0..100) {
                     UserLtvUtils.flushLtv("4500.0")
                 }*/

                //testList()

                /*val str="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"

                val key="perfect"

                EncryptUtils.decodeString(key,str)*/


                val str1="d1dc40c9dc2ef5cc"

                Log.d(TAG, "android md5---> ${MD5Utils.md5(str1)}")


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

    private fun testList() {
        val list = arrayListOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 9)
        while (true) {
            val removeNum = list.removeAt(0)
            if (removeNum == 1 || removeNum == 2) {
                list.add(removeNum)
                Log.d(TAG, "不能移除1或2 重新加回去 当前list-->$list")
            } else {
                Log.d(TAG, "移除$removeNum")
                break
            }
        }


    }


}