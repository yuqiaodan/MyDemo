package com.yuqiaodan.mydemo.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yuqiaodan.mydemo.R


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
                val str1 = "MTIzZTEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMw=="
                Log.d(TAG, (String(Base64.decode(str1, Base64.NO_WRAP)) + "\n"))
                val str2 = "MTIzZTEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMzEyMw"
                Log.d(TAG, String(Base64.decode(str2, Base64.NO_WRAP)) + "\n")
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()

    }


}