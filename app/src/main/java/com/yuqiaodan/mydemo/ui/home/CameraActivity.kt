package com.yuqiaodan.mydemo.ui.home

import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import com.otaliastudios.cameraview.CameraView
import com.yuqiaodan.mydemo.R

/**
 * CameraView 测试，框架文档:
 * https://natario1.github.io/CameraView/home
 * */

class CameraActivity : AppCompatActivity() {
    val TAG="CameraTest"

    lateinit var cameraView: CameraView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        cameraView = findViewById(R.id.view_camera)
        cameraView.setLifecycleOwner(this)
        initView()
    }


    private fun initView(){

      val seekBar=  findViewById<SeekBar>(R.id.seekbar)

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                cameraView.zoom=(progress.toFloat()/100f)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }



}