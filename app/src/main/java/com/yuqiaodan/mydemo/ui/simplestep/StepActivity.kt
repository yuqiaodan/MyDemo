package com.yuqiaodan.mydemo.ui.simplestep

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.yuqiaodan.mydemo.R
import com.yuqiaodan.mydemo.base.AppStorage

class StepActivity : AppCompatActivity(), SensorEventListener {

    private val TAG = "stepTag"

    //当前步数
    private var currentStep: Int = 0


    //传感器
    private var sensorManager: SensorManager? = null

    //计步传感器类型 0-counter 1-detector
    private var stepSensor = -1

    private val STEP_PERM_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_step)

        if (checkAndRequestStepPerm()) {
            initView()
        }
    }


    private fun initView() {

        sensorManager = this.getSystemService(android.content.Context.SENSOR_SERVICE) as SensorManager
        currentStep = AppStorage.todayStep
        refreshStepUi()
        //android4.4以后可以使用计步传感器
        val countSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val detectorSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        if (countSensor != null) {
            stepSensor = 0
            sensorManager?.registerListener(
                this@StepActivity,
                countSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        } else if (detectorSensor != null) {
            stepSensor = 1
            sensorManager?.registerListener(
                this@StepActivity,
                detectorSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
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

    override fun onSensorChanged(event: SensorEvent) {
        //Log.d(TAG, "onSensorChanged:event--> ${event} ")
        val tempStep = event.values[0].toInt()
        Log.d(TAG, "当前Sensor类型-->${stepSensor}  当前步数${tempStep}")

        if (AppStorage.systemStep == 0) {
            AppStorage.systemStep = tempStep
        } else {
            //中途关机导致被重置？
            //todo 重置后步骤又超过记录的步数怎么处理？ 不能单纯的判断大小来确定是否关机重置系统步数

            currentStep += tempStep - AppStorage.systemStep
            AppStorage.systemStep = tempStep

        }
        refreshStepUi()
    }


    private fun refreshStepUi() {

        findViewById<TextView>(R.id.movement_total_steps_tv).text = "$currentStep"

        findViewById<TextView>(R.id.movement_total_km_tv).text = "${(currentStep * 0.7).toInt()}"


        AppStorage.todayStep = currentStep
    }


    override fun onDestroy() {
        super.onDestroy()


    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


}