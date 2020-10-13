package com.duode.log.logconsole.annotation

import android.view.Gravity
import androidx.annotation.IntDef

/**
 * @author hekang
 * @des 限制指定console的位置
 * @date 2020/9/16 15:40
 */
@IntDef(
    Gravity.TOP, Gravity.TOP or Gravity.CENTER_HORIZONTAL, Gravity.TOP or Gravity.RIGHT,
    Gravity.CENTER,
    Gravity.BOTTOM, Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL, Gravity.BOTTOM or Gravity.RIGHT
)
@Retention(AnnotationRetention.SOURCE)
annotation class ConsoleGravity {
    //这里可以申明自己的常量类型
}