package com.yuqiaodan.mydemo.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bytedance.msdk.api.TTAdConstant
import com.bytedance.msdk.api.TTMediationAdSdk
import com.yuqiaodan.mydemo.BuildConfig
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.http.bean.EventBean
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import com.yuqiaodan.mydemo.utils.EncryptUtils

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
        findViewById<View>(R.id.btn_test).setOnClickListener(this)

    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_send_notify -> {
                startActivity(Intent(this, NotifyActivity::class.java))
            }
            R.id.btn_step_page -> {
                startActivity(Intent(this, StepActivity::class.java))
            }

            R.id.btn_test -> {
                App.instance.initThirdSDK()
            }


        }
    }


/*    val cacheList = arrayListOf(
        EventBean("a", "1"),
        EventBean("b", "1"),
        EventBean("c", "1"),
        EventBean("d", "1")
    )*/

/*
    private fun requestSplashPerm(): Boolean {
        val splashPerms = arrayListOf<String>()
        splashPerms.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        splashPerms.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        splashPerms.add(Manifest.permission.READ_PHONE_STATE)
        splashPerms.add(Manifest.permission.ACCESS_FINE_LOCATION)


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
        Log.d(TAG, "权限请求完毕 requestCode->$requestCode")
    }


    var permissionLocation = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

    fun hasPermissionLocation(context: Context): Boolean {
        for (perm in permissionLocation) {
            if (ContextCompat.checkSelfPermission(context, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
*/


}