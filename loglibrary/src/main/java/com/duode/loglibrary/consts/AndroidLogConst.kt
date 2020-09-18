package com.duode.loglibrary.consts

/**
 * @author hekang
 * @des 有关 ${AndroidLogAdapter} 的常量
 * @date 2020/8/27 16:31
 */
internal object AndroidLogConst {

   const val TOP_LEFT_CORNER = '┌'
   const val BOTTOM_LEFT_CORNER = '└'
   const val MIDDLE_CORNER = '├'
   const val HORIZONTAL_LINE = '│'
   const val DOUBLE_DIVIDER = "────────────────────────────────────────────────────────"
   const val SINGLE_DIVIDER = "┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄"
   const val TOP_BORDER = TOP_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
   const val BOTTOM_BORDER = BOTTOM_LEFT_CORNER.toString() + DOUBLE_DIVIDER + DOUBLE_DIVIDER
   const val MIDDLE_BORDER = MIDDLE_CORNER.toString() + SINGLE_DIVIDER + SINGLE_DIVIDER

   /**
    * Android默认的日志最大长度是4076字节，这里我们直接使用4000字节来作为最大长度
    */
   const val CHUNK_SIZE = 4000

}