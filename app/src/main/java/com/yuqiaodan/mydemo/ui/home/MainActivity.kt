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
import com.bytedance.msdk.api.v2.GMMediationAdSdk
import com.bytedance.msdk.api.v2.GMPrivacyConfig
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
                val config = object : GMPrivacyConfig() {
                    override fun isCanUseLocation(): Boolean {
                        return true
                    }

                    override fun isCanUsePhoneState(): Boolean {
                        return true
                    }

                    override fun isCanUseWriteExternal(): Boolean {
                        return true
                    }

                }
                GMMediationAdSdk.updatePrivacyConfig(config)

            }
            R.id.btn_test->{
                entest()
            }

        }
    }


    val cacheList = arrayListOf(
        EventBean("a", "1"),
        EventBean("b", "1"),
        EventBean("c", "1"),
        EventBean("d", "1")
    )

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
        Log.d(TAG, "权限请求完毕 requestCode->$requestCode  permissions-->$permissions  grantResults-->$grantResults")
    }

    fun entest(){
        val string1 =
            "C0cTCAERGxkBLQ8BQU5SUxZXUVRBFlFLVANbRkFXRURJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYfFQIJR09WEw0TCAsGGFJfUAkVExtSSVACABURHAoCAxdBTlIDEwoWBlZcRxYDEwoQUl9QVAAHEBVdR1QDUERAUUUCAVRMRQEQVldbFRRdS1NQU0JSSVAPCAYdUl9QXlNWQEVRQlJQWkBHXUtTR09WHAoRBxEKGx5HSB1HAhAUFxcVFkFOUua4s+WMpeedp+axneW2veW5tuiSreWwlOWPjVVXUeedtemAo+mchei+o+WSoOacgOWTieiIjuadn+W4meWFjeWai0dPVhEKGwgEDhFSX1BESUEXGRELRF9B5rGM5bau5bmnUEpHBx0DEQAPBhdWSkfokq/lsJflj5pBWFIJExIMQU5SV0dIUVtERVdKRElBGB8LUFxHUkVCS0NTXFdFRUdeRAsWGRIAAERfQVZcRwIJDA0VHQBQXEflkqXmnJHlk5roiJ/mnZ3luJrlhZrlmo5WXEcCFAoVHR4GF0RfQea4ouWMp+edpFBKRxEbEQFQXEdQRkLnnaTpgKFESUEHBBcXAxFBTlJWQFTnnaTpgLBWDUlQCwQAVkpHM1JfUkZKVkBcUlZOMVdIVyFBWFIIHQIAD1ZKRwIEBA5EQEdeRAoCHRRHSEQhUzdAUTRVJFs1SFdGUldWTElQQSRcUzVIV0MjVVsyRwBEVQNUEkYDEFVQVEZIVxNWVFJCFFdCUl0BRkEBEFBWQVhSCgE5EwYGUl9QXktSWkBHXkQVCBNSX1AFCg5aEQsWFAoKEF4JGwENF1oTCgVESUEGHwhQXEcMBAAKUEpHERsdOgQDF0FOUlBcVEtSVlxHAAkKF1ZKRxQHCRARUklQFQwOVkpHBhQQBlZcRwEVDAdWSkfwo7OtRUMHGBATCAJBWFITFxQ6ABsUAFBcR1JEQFRHRElBAhUXLQgEDhFSX1BXS1NaQVBQGw=="
        val string2="C0cTCAERGxkBLQ8BQU5SXBQCUgBHQgdHUAZTERFUR0RJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYfFQIJR09WEw0TCAsGGFJfUAkVExtSSVACABURHAoCAxdBTlIDEwoWBlZcRxYDEwoQUl9QUQZSFkRWQF4EVBISURBQVAVDRVcUXgEBEkRWS1BRUEFSSVAPCAYdUl9QXlNRQkBcQlVRU0xCXEdVR09WHAoRBxEKGx5HSB1HAhAUFxcVFkFOUuazluWNpeedp+W6r+WcqeW5tuaxiOa5oOWPjeWavee7iemAsOmclOi+oeaanOW7tuact0dPVhEKGwgEDhFSX1BESUEXGRELRF9B5bq+5Zy65bmnUEpHBx0DEQAPBhdWSkfmsYrmuaPlj5pBWFIJExIMQU5SVktIVlVERFRKRElBGB8LUFxHUkVGS0dVXVdNUklQCBAOFhUXUFxHUUbljodHXkQVDB0eBB8DR1lW5pqJ5buh5pyjRElBBAIKBA8LABFSX1Dms5XljbLnnaJWXEcACQQHVkpH5Zqp57uK6YC2QVhSFgYUAAYAUl9Q5Zq957uJ6YCwVg1JUAsEAFZKRzFWX1oySlVHXFFUTjRcSFFWQVhSCB0CAA9WSkcdFhUMVAJcH0RJQRsRDBZEX0FWXEcdFToVEQJHSERQTUVSSVAWDgRWSkcRCQhNFR4BAAkMB1ocDBUOEU0XHxJQSkcRGx1HSEQKEwQfR15EFwwZLxMXFEdZVkNLQkRJQQYfCgZEX0ESEQkBA0dPVgMMH0RfQRIRCQEDR09WAxYbAkdZVuWlmumZlvCjuKRHT1YGAAA5BgwQFUdIRFRTREFSUEpHFRECOhwHCAZWSkdDSFVNRUdHDw=="
        val key = "perfect"

        EncryptUtils.decodeString(key, string1)
        EncryptUtils.decodeString(key, string2)
    }


    fun emojiTest(){





    }

}