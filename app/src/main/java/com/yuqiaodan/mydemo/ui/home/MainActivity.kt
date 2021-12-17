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
import com.yuqiaodan.mydemo.study.ThreadStudy
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

            R.id.rq_prem -> {
                Log.d(TAG, "rq_prem")
                requestSplashPerm()
            }

            R.id.btn_update -> {

            }
            R.id.btn_test -> {
                ThreadStudy().runCountDown(findViewById(R.id.tv_text_test))
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