package com.yuqiaodan.mydemo.ui.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.App
import com.yuqiaodan.mydemo.ui.notify.NotifyActivity
import com.yuqiaodan.mydemo.ui.simplestep.StepActivity
import com.yuqiaodan.mydemo.utils.ApkUtils
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


            }
            R.id.btn_test -> {
                //deletApp("com.android.light.cow",App.context)
                //ApkUtils.uninstall("com.android.light.cow")
                entest()
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

    fun entest() {
        val string1 =
            "C0cTCAERGxkBLQ8BQU5SUxZXUVRBFlFLVANbRkFXRURJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYfFQIJR09WEw0TCAsGGFJfUAkVExtSSVACABURHAoCAxdBTlIDEwoWBlZcRxYDEwoQUl9QVAAHEBVdR1QDUERAUUUCAVRMRQEQVldbFRRdS1NQU0JSSVAPCAYdUl9QXlNWQEVRQlJQWkBHXUtTR09WHAoRBxEKGx5HSB1HAhAUFxcVFkFOUua4s+WMpeedp+axneW2veW5tuiSreWwlOWPjVVXUeedtemAo+mchei+o+WSoOacgOWTieiIjuadn+W4meWFjeWai0dPVhEKGwgEDhFSX1BESUEXGRELRF9B5rGM5bau5bmnUEpHBx0DEQAPBhdWSkfokq/lsJflj5pBWFIJExIMQU5SV0dIUVtERVdKRElBGB8LUFxHUkVCS0NTXFdFRUdeRAsWGRIAAERfQVZcRwIJDA0VHQBQXEflkqXmnJHlk5roiJ/mnZ3luJrlhZrlmo5WXEcCFAoVHR4GF0RfQea4ouWMp+edpFBKRxEbEQFQXEdQRkLnnaTpgKFESUEHBBcXAxFBTlJWQFTnnaTpgLBWDUlQCwQAVkpHM1JfUkZKVkBcUlZOMVdIVyFBWFIIHQIAD1ZKRwIEBA5EQEdeRAoCHRRHSEQhUzdAUTRVJFs1SFdGUldWTElQQSRcUzVIV0MjVVsyRwBEVQNUEkYDEFVQVEZIVxNWVFJCFFdCUl0BRkEBEFBWQVhSCgE5EwYGUl9QXktSWkBHXkQVCBNSX1AFCg5aEQsWFAoKEF4JGwENF1oTCgVESUEGHwhQXEcMBAAKUEpHERsdOgQDF0FOUlBcVEtSVlxHAAkKF1ZKRxQHCRARUklQFQwOVkpHBhQQBlZcRwEVDAdWSkfwo7OtRUMHGBATCAJBWFITFxQ6ABsUAFBcR1JEQFRHRElBAhUXLQgEDhFSX1BXS1NaQVBQGw=="
        val string2 =
            "C0cTCAERGxkBLQ8BQU5SXBQCUgBHQgdHUAZTERFUR0RJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYfFQIJR09WEw0TCAsGGFJfUAkVExtSSVACABURHAoCAxdBTlIDEwoWBlZcRxYDEwoQUl9QUQZSFkRWQF4EVBISURBQVAVDRVcUXgEBEkRWS1BRUEFSSVAPCAYdUl9QXlNRQkBcQlVRU0xCXEdVR09WHAoRBxEKGx5HSB1HAhAUFxcVFkFOUuazluWNpeedp+W6r+WcqeW5tuaxiOa5oOWPjeWavee7iemAsOmclOi+oeaanOW7tuact0dPVhEKGwgEDhFSX1BESUEXGRELRF9B5bq+5Zy65bmnUEpHBx0DEQAPBhdWSkfmsYrmuaPlj5pBWFIJExIMQU5SVktIVlVERFRKRElBGB8LUFxHUkVGS0dVXVdNUklQCBAOFhUXUFxHUUbljodHXkQVDB0eBB8DR1lW5pqJ5buh5pyjRElBBAIKBA8LABFSX1Dms5XljbLnnaJWXEcACQQHVkpH5Zqp57uK6YC2QVhSFgYUAAYAUl9Q5Zq957uJ6YCwVg1JUAsEAFZKRzFWX1oySlVHXFFUTjRcSFFWQVhSCB0CAA9WSkcdFhUMVAJcH0RJQRsRDBZEX0FWXEcdFToVEQJHSERQTUVSSVAWDgRWSkcRCQhNFR4BAAkMB1ocDBUOEU0XHxJQSkcRGx1HSEQKEwQfR15EFwwZLxMXFEdZVkNLQkRJQQYfCgZEX0ESEQkBA0dPVgMMH0RfQRIRCQEDR09WAxYbAkdZVuWlmumZlvCjuKRHT1YGAAA5BgwQFUdIRFRTREFSUEpHFRECOhwHCAZWSkdDSFVNRUdHDw=="
        val key = "perfect"

        val string3="C0cTCAERGxkBLQ8BQU5SUUQEBwVNFVQWUFVSRUhdQkRJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYIDBMJCApWXEcRDgQNGhUJUFxHDhEZHwdESUEQFRMXCgoTEQJHSEQREQEVR15EAQYCGQFQXEdbERUGF1NVARZCXUNVXVBHRVNBXlFQR0kDSwNdV0ESAVBKRwoZFQxQXEdbQklSQFZVUEVFUkNWVFdWXEceCQYCABkKHERfGFYRARYUABAHUl9Q5Zq95ba4552i5omk6YKN5bmn5qyU5L+J5Y2f5Lm157ue5Z6+5Yyy6Lad6ZyG6L605bmg6KCX5a+S5qGtWuabpOWAueiRhuS5rllHXkQEDB0eBB8DR1lW5aWZ5bu56Lyd5LqQ5ZqIIOWNjlJJUAUMFw1SX1DmibbpgpjluaFWXEcWDxYXBhkGBkRfQeaskuS/n+WNn1BKRw8VBAxQXEdQRF5QRldVVkNSSVAKCg1WSkdDVlFNREdXRVJTQVhSCwcLBwYGUl9QU1xb5Y6DUklQFgoKGhEIF0RfQeW5t+igk+Wvh+ahuk7mm6flgL/okZHkuapMUEpHEwYfExsIBgZWSkflmqnltrvnnaRBWFIXHQcBQU5S5Lmz57uY5Z6o5Yyy6LaMVlxHARIXBhEER0hE5Lmz57uJ5Z665Yyn6LaKUBtJQRkRBlBcR1tESlZHXCZSTkNXSF8nWUA2R15ECAwQFQlQXEcRERQIG0YLDAAVRUdESUEbEQwWRF9BQEFTQgRVVUMTBEVfVAcQUklQCRY8AhUXUFxHWlZcRwINAkFOUgYdC0sCGhQXHQ8BTRgZAhoSSwAbB0deRBcMGVJfUAsMFh1SSVAUCg4rBgAARF9BRUJLQkhWTURSSVAUCgwAUl9QAAQPBxVHXkQWChlSX1AABA8HFUdeRBYQHRRHSEQxIVkkABMLNEFYUhMXFDoAGxQAUFxHUkRAV0BESUECFRctCAQOEVJfUFdLU1pCV1Ab"
        //EncryptUtils.decodeString(key, string1)
        //EncryptUtils.decodeString(key, string2)
        EncryptUtils.decodeString(key, string3)

        val str4="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"
        EncryptUtils.decodeString("8f9953cb9f51c3c5", str4)

    }




    fun deletApp(pkg:String,context:Context){
        val intent = Intent(Intent.ACTION_DELETE)
        intent.data = Uri.parse("package:$pkg")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

}