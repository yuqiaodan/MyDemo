package com.yuqiaodan.mydemo.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView
import com.yuqiaodan.mydemo.R

/**
 * 吸顶效果测试
 * **/
class TopStickActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_stick)
        initView()
    }


    private fun initView() {
        val scrollView = findViewById<NestedScrollView>(R.id.scroll_view)
        val viewContent1 = findViewById<TextView>(R.id.view_1)
        val tabTop = findViewById<TextView>(R.id.view_tab_top)
        val tab = findViewById<TextView>(R.id.view_tab)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener {
                override fun onScrollChange(v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
                    Log.d("TopStickTag", "onScrollChange: scrollX:$scrollX scrollY:$scrollY  oldScrollX:$oldScrollX  oldScrollY:$oldScrollY")
                    if(scrollY> viewContent1.height){
                        tabTop.visibility=View.VISIBLE
                    }else{
                        tabTop.visibility=View.GONE
                    }
                }
            })
        }


    }
}