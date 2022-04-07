package com.yuqiaodan.mydemo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.utils.EncryptUtils


class MainActivity : AppCompatActivity(), View.OnClickListener {
    val TAG = "MainLog"


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
                /*val str1 = "MTIzZTEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMw=="
                Log.d(TAG, (String(Base64.decode(str1, Base64.NO_WRAP)) + "\n"))
                val str2 = "MTIzZTEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMw"
                Log.d(TAG, String(Base64.decode(str2, Base64.NO_WRAP)) + "\n")
*/

                val key="perfect"
                val str="C0cTCAERGxkBLQ8BQU5SBxNXBlIRSVwXUlABQxIBRERJQRUAFS0KDBAAUl8pO0lBFgIEHAJHWVYYChwJF0FYUgYaBwsNERxHSEQKEwQfR15EB\n" +
                        "gwZAAAGDxEKGx5HSERVQVhSARcQAA8bAAAARF9BAAIQF0RJQRAVExsCR1lWQwQQA1BVQ0IHRgVWBkZHU0BSBlcRE1RKU1YGFURVEARHT1YZCBc\n" +
                        "PR1lWUklQCgoAFQQMHQhHWQ9SBBYCFwYHA0dIROWavuW2vuedteaJoOmCmOW5sOasgOS/iuWNmeS5oue7muWeq+WMpei2iemchei+suWlneW7r\n" +
                        "Oi8iuS6hOWai0dPVhEKGwgEDhFSX1DlpY/lu7novIzkuoLlmp0m5Y2IRElBFxkRC0RfQeaJpOmCjeW5p1BKRwcdAxEADwYXVkpH5qyU5L+J5Y2\n" +
                        "fQVhSCRMSDEFOUlZCSFBXRUBXUEpHDxseR0hEVFNAXlVFVFNaR1JJUAgQDhYVF1BcR1ZNSOWOklBKRxMbGQsTCwBBTlLlpYzlu67ovInkupPlm\n" +
                        "o5WXEcCFAoVHR4GF0RfQeWar+W2reedpFBKRxEbEQFQXEfkubXnu57lnr7ljLLotp1ESUEHBBcXAxFBTlLkubPnu5jlnqjljLLotoxWDUlQCwQ\n" +
                        "AVkpHRl5fUDJKIEtcUlpOSFFIVFFBWFIIHQIAD1ZKRxoUHE4VHFVCEgRBWFIKEw8BQU5SVUJWVVNEQFVfVlVTRF1VQlZVTkRAVUJLVVNEQFVCV\n" +
                        "lVTREBVUEpHDAcvExcUR1lWQVVQSkcTHxdHSEQGDBleBBwCFwwdFEsBHxZNFhUAAERJQQQCCgofR1lWFgQeFQBBWFIXHQtHWVYVCAcPR09WAgo\n" +
                        "fORMGBlJfUFdUTUVeVVBKRxEbHxFQXEcFFRwWF0RJQQcTBBw5BBMELwkbFRFBTlJVUEpHEB0dR0hEAwIYAwBQSkcQBxkBUFxHMjpdMCI5UCRWX\n" +
                        "EcGFAQAHy8GGgcLDREcR0hECxYYHEdeRBMGBi8GHQIAQU5SVEJWVFNWXEcEAxc8GhEIF0RfQUVeVVxXVUFYUhMCCEdZVhYEHhUAQQk="
                EncryptUtils.decodeString(key,str)
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }


}