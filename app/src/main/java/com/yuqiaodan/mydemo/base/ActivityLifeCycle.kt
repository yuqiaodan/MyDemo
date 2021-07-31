package com.yuqiaodan.mydemo.base

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log

/**
 * Created by qiaodan on 2020/11/19
 * Description:
 *
 */
class ActivityLifeCycle: Application.ActivityLifecycleCallbacks {


    val STATE_FOREGROUND = 0

    val STATE_BACKGROUND = 1

    var currentState = 0

    var resumeActivityCount = 0
    private var appStateChangeListener: AppStateChangeListener? = null

    fun track(application: Application, appStateChangeListener: AppStateChangeListener) {
        application.registerActivityLifecycleCallbacks(this)
        this.appStateChangeListener = appStateChangeListener

    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.d("ActivityLifeCycle", "onCreated ${activity.javaClass.simpleName}")
    }

    override fun onActivityStarted(activity: Activity) {
        Log.d("ActivityLifeCycle", "onStarted ${activity.javaClass.simpleName}")
        if (resumeActivityCount == 0) {
            currentState = STATE_FOREGROUND;
            Log.d("ActivityLifeCycle", "从后台返回前台")
            appStateChangeListener?.appTurnIntoForeground()
        }
        resumeActivityCount++
    }

    override fun onActivityResumed(activity: Activity) {
        Log.d("ActivityLifeCycle", "onResumed ${activity.javaClass.simpleName}")

    }

    override fun onActivityPaused(activity: Activity) {
        Log.d("ActivityLifeCycle", "onPaused ${activity.javaClass.simpleName}")

    }

    override fun onActivityStopped(activity: Activity) {
        Log.d("ActivityLifeCycle", "Stopped ${activity.javaClass.simpleName}")
        resumeActivityCount--

        if (resumeActivityCount == 0) {
            currentState = STATE_BACKGROUND
            Log.d("ActivityLifeCycle", "退出到后台")
            appStateChangeListener?.appTurnIntoBackGround()
        }


    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.d("ActivityLifeCycle", "onSaveState ${activity.javaClass.simpleName}")

    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.d("ActivityLifeCycle", "onDestroyed ${activity.javaClass.simpleName}")

    }

    interface AppStateChangeListener {
        fun appTurnIntoForeground()

        fun appTurnIntoBackGround()
    }


}