package com.duode.loglibrary

import com.duode.loglibrary.consts.LogConst
import com.duode.loglibrary.listener.LogAdapter

/**
 * @author hekang
 * @des 提供给外部使用的日志打印入口类
 * @date 2020/8/4 18:58
 */
object LogUtils {

  /**
   * 配置全局的tag值
   * */
  var mGlobalTag = LogConst.DEFAULT_TAG_GLOBAL

  private val mLogHelper by lazy {
    LogPrintHelper()
  }

  /**
   * @see LogConst.LEVEL_VERBOSE 级别的日志输出到控制台
   * @param tag 当前日志的tag
   * @param flag 当前日志的flag，默认全局生效
   * @param msg 当前需要打印的日志信息
   * */
  fun v(tag: String = LogConst.DEFAULT_TAG_SELF, flag: Int = LogConst.FLAG_DEFAULT, msg: String) {
    log(LogConst.LEVEL_VERBOSE, flag, tag, msg)
  }

  fun d(tag: String = LogConst.DEFAULT_TAG_SELF, flag: Int = LogConst.FLAG_DEFAULT, msg: String) {
    log(LogConst.LEVEL_DEBUG, flag, tag, msg)
  }

  fun i(tag: String = LogConst.DEFAULT_TAG_SELF, flag: Int = LogConst.FLAG_DEFAULT, msg: String) {
    log(LogConst.LEVEL_INFO, flag, tag, msg)
  }

  fun w(tag: String = LogConst.DEFAULT_TAG_SELF, flag: Int = LogConst.FLAG_DEFAULT, msg: String) {
    log(LogConst.LEVEL_WARN, flag, tag, msg)
  }

  fun e(tag: String = LogConst.DEFAULT_TAG_SELF, flag: Int = LogConst.FLAG_DEFAULT, msg: String) {
    log(LogConst.LEVEL_ERROR, flag, tag, msg)
  }

  fun asset(tag: String = LogConst.DEFAULT_TAG_SELF, flag: Int = LogConst.FLAG_DEFAULT, msg: String) {
    log(LogConst.LEVEL_ASSERT, flag, tag, msg)
  }

  private fun log(level: Int, flag: Int, tag: String, msg: String) {
    mLogHelper.log(mGlobalTag, tag, flag, level, msg)
  }

  fun addLogAdapters(vararg adapter: LogAdapter) {
    mLogHelper.addAdapters(*adapter)
  }

  fun clearAdapters() {
    mLogHelper.clearAdapters()
  }
}