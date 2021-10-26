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
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.http.bean.EventBean
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import com.yuqiaodan.mydemo.utils.EncryptUtils

class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"


    val cacheList = arrayListOf(
        EventBean("a", "1"),
        EventBean("b", "1"),
        EventBean("c", "1"),
        EventBean("d", "1")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()

    }


    private fun initView() {
        findViewById<View>(R.id.btn_send_notify).setOnClickListener(this)
        findViewById<View>(R.id.btn_step_page).setOnClickListener(this)
        findViewById<View>(R.id.btn_test).setOnClickListener(this)
        requestSplashPerm()
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
                val key="perfect"

                val srting1="C0cTCAERGxkBLQ8BQU5SVhFSBlNESFZGXwRTQElXF0RJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYfFQIJR09WEw0TCAsGGFJfUAkVExtSSVACABURHAoCAxdBTlIRABMAQVhSARcQDAdWSkdQSkcKGRUMUFxHQVhSCR0FBBcdHwtQXB5BFRQBAAMWEFZKR1BKRwIbGQsTCwBBTlJHXkQGCgAJR0hER09WFAwBEhcKFwRHSERHT1YcBAYPR1lWUklQCgoNVkpHUEpHDQEdBxcUR1lWUklQFgoKGhEIF0RfQVZcRwIUChUdHgYXRF9BVlxHAAkEB1ZKR1BKRxAAAgAXEkdZVlIYXkQIAhdSX1BESUEZHwEXCkdZVh8VAglFEU0DEVBKRwwVGQFQXEdBWFIKATkTBgZSX1BQS1NaQUdeRBUIE1JfUAUKDloRCxYUCgoQXgkbAQ0XWhMKBURJQQYfCFBcRwwEAApQSkcRGx06BAMXQU5SVlxWR09WAgodEkdZVhYEHhUAQVhSFhsLR1lWFgQeFQBBWFIWAQ8BQU5SR15EEwYGLwYdAgBBTlJUQlZUVFZcRwQDFzwaEQgXRF9BRV5VXFdSQQk="

                val srting2="C0cTCAERGxkBLQ8BQU5SUBADXFoWQwYWVgcFQRJRQ0RJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYYChwJF0FYUgYaBwsNERxHSEQdChUfCBtESUEQFRMXCgoTEQJHSEQREQEVR15EAQYCGQFQXEcHRkdSRFFVBxBHBkdXAwVAR1UTVV1aFhZTEQAGU0ETAFBKRwoZFQxQXEdBWFIJHQUEFx0fC1BcHkEVFAEAAxYQVkpH5Zqp5ba7552k5omz6YKJ5bmy5qyD5L+d5Y2c5Lmz57uJ5Z665Yyn6LaK6ZyS6L635bmm6KCA5a+W5qG4TeabsOWAuuiRgOS5uV1SSVAHCgoaEQgXRF9B5aWd5bus6LyK5LqE5ZqLJuWNmVZcRxEPERpWSkfmiaLpgpvluadBWFIBGxURER0TEVBcR+asheS/m+WNikdeRAkCABlHSERWU1pFUUNWUVFWXEceCQtBTlJUQlJLU0NCUkVVR09WHhAfBAARVkpHR19d5Y6UVlxHAgkMDRUdAFBcR+W5oOigl+WvkuahrVrmm6TlgLnokYbkua5ZR15EFREbBgwcBQBBTlLlmr7ltq/nnadHT1YCChMCR1lW5Lmm57uP5Z685Yyx6LaKQVhSFgYUAAYAUl9Q5Lmw57uP5Z6t5Yyj6LafRw9KRw4VE0dIRFFbTkMjSCNcWUNJX0pSX1FAUklQCwoHERxHSEQNEQ1dBB5WVRcVUklQCQQKEFJfUAUDBxBGUkdfSFQWFgZfX1FXEV0DEAAAThBHA0tRAAASQwYUB0dPVh8WLRAAEVZKR0NWR09WAA4VRF9BFx8IXAcLBwYfDBZICQoTGBFcBQoUVlxHAAkIQU5SAB8TDEFYUhcdCzoVEQJHSERUU1pAS0JESUEGHwoGRF9BEhEJAQNHT1YDDB9EX0ESEQkBA0dPVgMWGwJHWVYkJ18yAAIZIUdeRBMGBi8GHQIAQU5SVEJWVFRWXEcEAxc8GhEIF0RfQUVeVVxXUkEJ"
                EncryptUtils.decodeString(key,srting1)
                EncryptUtils.decodeString(key,srting2)

                hasPermissionLocation(App.context)

            }


        }
    }


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


}