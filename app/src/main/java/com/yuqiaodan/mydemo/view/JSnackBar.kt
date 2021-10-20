package com.yuqiaodan.mydemo.view

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.yuqiaodan.mydemo.R

/**
 * Created by qiaodan on 2021/9/27
 * Description:
 */
class JSnackBar {
    private lateinit var snackbar: Snackbar
    private lateinit var msgView:TextView
    private lateinit var actBtn:Button
    fun make(view: View, msg:String, duration: Int, layout:Int):Snackbar{
        snackbar = Snackbar.make(view, msg, duration)
        val parent = snackbar.view as Snackbar.SnackbarLayout
        //Layout depend on your custon layout file
        parent.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
        parent.setBackgroundColor(Color.TRANSPARENT)
        parent.setPadding(0, 0,0, 0)
        val contentView = parent.getChildAt(0) as SnackbarContentLayout
        val newContentView = LayoutInflater.from(view.context).inflate(layout, contentView, false) as SnackbarContentLayout
        //Reset Id
        newContentView.id = contentView.id
        msgView = newContentView.findViewById(R.id.jepack_snack_bar_msg)
        actBtn  = newContentView.findViewById(R.id.jepack_snack_bar_action)

        msgView.id = com.google.android.material.R.id.snackbar_text
        actBtn.id = com.google.android.material.R.id.snackbar_action
        //Reflect find views。
        try {

            val method = newContentView::class.java.getDeclaredMethod("onFinishInflate")
            method.isAccessible = true
            method.invoke(newContentView)
            //Reset msg text
            msgView.text = msg
            val index = parent.indexOfChild(contentView)
            //替换布局
            parent.removeViewAt(index)
            parent.addView(newContentView, index)
        }catch (e:Exception){
            e.printStackTrace()
        }
        return snackbar
    }
}