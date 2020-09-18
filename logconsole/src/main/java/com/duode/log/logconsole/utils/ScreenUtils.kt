package com.duode.log.logconsole.utils

import android.app.Activity
import android.util.DisplayMetrics


/**
 * @author hekang
 * @des
 * @date 2020/9/17 13:46
 */
object ScreenUtils {
    fun getScreenHeight(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.heightPixels
    }

    fun getScreenWidth(activity: Activity): Int {
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        return dm.widthPixels
    }
}