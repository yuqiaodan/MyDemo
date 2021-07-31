package com.yuqiaodan.mydemo.base

import android.app.Application
import android.content.Context
import android.util.Log


/**
 * Created by qiaodan on 2020/11/19
 * Description:
 *
 */
class App : Application() {
    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext


        ActivityLifeCycle().track(this, object : ActivityLifeCycle.AppStateChangeListener {
            override fun appTurnIntoForeground() {
                Log.d("ActivityLifeCycle", "Application从后台返回前台")

            }

            override fun appTurnIntoBackGround() {
                Log.d("ActivityLifeCycle", "Application退出到前台")

            }
        })

        startTimeWork()
    }


    private fun startTimeWork() {
        Log.d("TimeWorker", "startTimeWork")


/*        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(TimeWorker::class.java) //一次性Work请求
            .setInputData(data)
            .build()*/

       // val periodicWorkRequest= PeriodicWorkRequest.Builder(TimeWorker::class.java,10,TimeUnit.MILLISECONDS).build()


        //WorkManager.getInstance(this).enqueue(periodicWorkRequest)
    }
}