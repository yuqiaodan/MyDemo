package com.yuqiaodan.mydemo.ui

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yuqiaodan.mydemo.R


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"
    val str = "测试字符串 打包时被混淆"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()


    }


    private fun initView() {
        findViewById<View>(R.id.btn_test).setOnClickListener(this)
        findViewById<View>(R.id.btn_demo).setOnClickListener(this)
    }


    @SuppressLint("InvalidWakeLockTag")

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_demo -> {
                startActivity(Intent(this, FuncModeActivity::class.java))
            }

            R.id.btn_test -> {
                /*val key = "perfect"
                val str = "C0cTCAERGxkBLQ8BQU5SBxNXBlIRSVwXUlABQxIBRERJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYYChwJF0FYUgYaBwsNERxHSEQKEwQfR15EB\n" +
                        "gwZAAAGDxEKGx5HSERVQVhSARcQAA8bAAAARF9BAAIQF0RJQRAVExsCR1lWQwQQA1BVQ0IHRgVWBkZHU0BSBlcRE1RKU1YGFURVEARHT1YZCBc\n" +
                        "PR1lWUklQCgoAFQQMHQhHWQ9SBBYCFwYHA0dIROWavuW2vuedteaJoOmCmOW5sOasgOS/iuWNmeS5oue7muWeq+WMpei2iemchei+suWlneW7r\n" +
                        "Oi8iuS6hOWai0dPVhEKGwgEDhFSX1DlpY/lu7novIzkuoLlmp0m5Y2IRElBFxkRC0RfQeaJpOmCjeW5p1BKRwcdAxEADwYXVkpH5qyU5L+J5Y2\n" +
                        "fQVhSCRMSDEFOUlZCSFBXRUBXUEpHDxseR0hEVFNAXlVFVFNaR1JJUAgQDhYVF1BcR1ZNSOWOklBKRxMbGQsTCwBBTlLlpYzlu67ovInkupPlm\n" +
                        "o5WXEcCFAoVHR4GF0RfQeWar+W2reedpFBKRxEbEQFQXEfkubXnu57lnr7ljLLotp1ESUEHBBcXAxFBTlLkubPnu5jlnqjljLLotoxWDUlQCwQ\n" +
                        "AVkpHRl5fUDJKIEtcUlpOSFFIVFFBWFIIHQIAD1ZKRxoUHE4VHFVCEgRBWFIKEw8BQU5SVUJWVVNEQFVfVlVTRF1VQlZVTkRAVUJLVVNEQFVCV\n" +
                        "lVTREBVUEpHDAcvExcUR1lWQVVQSkcTHxdHSEQGDBleBBwCFwwdFEsBHxZNFhUAAERJQQQCCgofR1lWFgQeFQBBWFIXHQtHWVYVCAcPR09WAgo\n" +
                        "fORMGBlJfUFdUTUVeVVBKRxEbHxFQXEcFFRwWF0RJQQcTBBw5BBMELwkbFRFBTlJVUEpHEB0dR0hEAwIYAwBQSkcQBxkBUFxHMjpdMCI5UCRWX\n" +
                        "EcGFAQAHy8GGgcLDREcR0hECxYYHEdeRBMGBi8GHQIAQU5SVEJWVFNWXEcEAxc8GhEIF0RfQUVeVVxXVUFYUhMCCEdZVhYEHhUAQQk="
                EncryptUtils.decodeString(key, str)*/
                Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
                //RxjavaStudy().start()


                startShakeByPropertyAnim(findViewById(R.id.btn_demo))
            }

        }
    }


    private fun startShakeByPropertyAnim(
        view: View
    ) {
        //缩放幅度
        val scaleSmall = 0.9f
        //方法幅度
        val scaleLarge = 1.1f
        //抖动幅度
        val shakeDegrees = 10f
        //动画时长
        val duration: Long = 1000
        //先变小后变大
        val scaleXValuesHolder = PropertyValuesHolder.ofKeyframe(
            View.SCALE_X,
            Keyframe.ofFloat(0f, 1.0f),
            Keyframe.ofFloat(0.25f, scaleSmall),
            Keyframe.ofFloat(0.5f, scaleLarge),
            Keyframe.ofFloat(0.75f, scaleLarge),
            Keyframe.ofFloat(1.0f, 1.0f)
        )
        val scaleYValuesHolder = PropertyValuesHolder.ofKeyframe(
            View.SCALE_Y,
            Keyframe.ofFloat(0f, 1.0f),
            Keyframe.ofFloat(0.25f, scaleSmall),
            Keyframe.ofFloat(0.5f, scaleLarge),
            Keyframe.ofFloat(0.75f, scaleLarge),
            Keyframe.ofFloat(1.0f, 1.0f)
        )
        //先往左再往右
        val rotateValuesHolder = PropertyValuesHolder.ofKeyframe(
            View.ROTATION,
            Keyframe.ofFloat(0f, 0f),
            Keyframe.ofFloat(0.1f, -shakeDegrees),
            Keyframe.ofFloat(0.2f, shakeDegrees),
            Keyframe.ofFloat(0.3f, -shakeDegrees),
            Keyframe.ofFloat(0.4f, shakeDegrees),
            Keyframe.ofFloat(0.5f, -shakeDegrees),
            Keyframe.ofFloat(0.6f, shakeDegrees),
            Keyframe.ofFloat(0.7f, -shakeDegrees),
            Keyframe.ofFloat(0.8f, shakeDegrees),
            Keyframe.ofFloat(0.9f, -shakeDegrees),
            Keyframe.ofFloat(1.0f, 0f)
        )
        val objectAnimator = ObjectAnimator.ofPropertyValuesHolder(view, scaleXValuesHolder, scaleYValuesHolder, rotateValuesHolder)
        objectAnimator.duration = duration
        objectAnimator.start()
    }


}








