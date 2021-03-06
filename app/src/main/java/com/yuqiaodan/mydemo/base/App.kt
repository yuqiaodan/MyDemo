package com.yuqiaodan.mydemo.base

import android.app.Application
import android.content.Context
import android.util.Log
import com.tencent.mmkv.MMKV
import com.yuqiaodan.mydemo.greendao.DaoManager
import com.yuqiaodan.mydemo.greendao.DaoMaster


/**
 * Created by qiaodan on 2020/11/19
 * Description:
 *
 */
class App : Application() {
    companion object {
        lateinit var context: Context
        lateinit var instance: App
    }

    init {
        instance = this
    }


    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        //初始化数据库
        //DaoManager.initGreenDao(this)
        //初始化MMKV
        MMKV.initialize(this)
        ActivityLifeCycle().track(this, object : ActivityLifeCycle.AppStateChangeListener {
            override fun appTurnIntoForeground() {
                Log.d("ActivityLifeCycle", "Application从后台返回前台")

            }

            override fun appTurnIntoBackGround() {
                Log.d("ActivityLifeCycle", "Application退出到前台")

            }
        })
    }




}