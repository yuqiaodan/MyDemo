package com.yuqiaodan.mydemo.ui.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import com.yuqiaodan.mydemo.view.JSnackBar
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"
    val splashPerms= arrayListOf<String>()

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

                val calendar=Calendar.getInstance()

                Log.d(TAG, "DAY_OF_WEEK  ${calendar.get(Calendar.DAY_OF_WEEK)}")
                Log.d(TAG, "DAY_OF_MONTH  ${calendar.get(Calendar.DAY_OF_MONTH)}")
                Log.d(TAG, "DAY_OF_YEAR  ${calendar.get(Calendar.DAY_OF_YEAR)}")
                Log.d(TAG, "DAY_OF_WEEK_IN_MONTH  ${calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH)}")


                //requestSplashPerm()
            }


        }
    }



    private fun showTestToast(){





    }




    private fun hasAllPermissions(grantResults: Array<String>): Boolean {
        for (perm in grantResults) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }


    private fun requestSplashPerm(): Boolean {
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


    private fun showTestSnackBar(view:View){

        JSnackBar().make(view,"test",100000,R.layout.test_jsnakvae).show()

    }


}