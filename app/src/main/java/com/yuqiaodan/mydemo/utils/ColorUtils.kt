package com.yuqiaodan.mydemo.utils

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import androidx.core.content.ContextCompat

/**
 * Note：
 *
 * 作者：fxx028
 */

object ColorUtils {
    fun getColor(context: Context, id: Int): Int {
        val version = Build.VERSION.SDK_INT
        return if (version >= 23) {
            ContextCompat.getColor(context, id)
        } else {
            context.resources.getColor(id)
        }
    }

    fun createColorStateList(context: Context, checkedColor: Int, normalColor: Int): ColorStateList? {
        val colors = intArrayOf(
            ContextCompat.getColor(context, checkedColor),
            ContextCompat.getColor(context, normalColor)
        )
        val states = arrayOfNulls<IntArray>(2)
        states[0] = intArrayOf(android.R.attr.state_checked)
        states[1] = intArrayOf(-android.R.attr.state_checked)
        return ColorStateList(states, colors)
    }

}