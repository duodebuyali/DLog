package com.duode.loglibrary.consts

import android.util.Log

/**
 * @author hekang
 * @des 有关日志打印的常量
 * @date 2020/8/5 10:50
 */
object LogConst {

  /**
   * 调用打印日志时的打印等级,所有的值来源于Android系统自身的定义
   * @see Log.VERBOSE
   * */
  const val LEVEL_VERBOSE = Log.VERBOSE
  const val LEVEL_DEBUG = Log.DEBUG
  const val LEVEL_INFO = Log.INFO
  const val LEVEL_WARN = Log.WARN
  const val LEVEL_ERROR = Log.ERROR
  const val LEVEL_ASSERT = Log.ASSERT

  /**
   * 默认的全局的tag值
   * */
  const val DEFAULT_TAG_GLOBAL = "LogUtils"

  /**
   * 默认的自身tag
   * */
  const val DEFAULT_TAG_SELF = "Tag"


  /**
   * 用来标识日志的特殊性，可以用来区分本条日志 只在哪些logAdapter中生效
   * */
  //在所有adapter中都生效
  const val FLAG_DEFAULT = 0

  /**
   * 日志方法被调用时，在Stack中的下标(即 LogUtils中的日志方法经过了多次层才真正被执行)
   * 以 LogUtils.d("xx") 为例，依次为:
   * dalvik.system.VMStack.getThreadStackTrace(Native Method)
   * java.lang.Thread.getStackTrace(Thread.java:1565)
   * com.duode.loglibrary.utils.LogInfoUtils.buildLogInfo(LogInfoUtils.kt:46)
   * com.duode.loglibrary.LogPrintHelper.log(LogPrintHelper.kt:37)
   * com.duode.loglibrary.LogUtils.log(LogUtils.kt:53)
   * com.duode.loglibrary.LogUtils.d(LogUtils.kt:33)
   * ...(之后紧跟着的第一个即 日志方法被调用的地方)
   */
  const val MIN_STACK_OFFSET = 5
}