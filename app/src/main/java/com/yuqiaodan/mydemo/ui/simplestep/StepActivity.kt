package com.yuqiaodan.mydemo.ui.simplestep

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R

class StepActivity : AppCompatActivity() {

    private val TAG = "step_tag"

    private val STEP_PERM_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)

        if (checkAndRequestStepPerm()) {
            initView()
        }
    }


    private fun initView() {


    }


    private fun checkAndRequestStepPerm(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            return if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    STEP_PERM_CODE
                )
                false
            } else {
                true
            }
        } else {
            return true
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STEP_PERM_CODE) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    initView()
                } else {
                    Toast.makeText(this, "请允许获取健身运动信息，不然不能为你计步", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


}