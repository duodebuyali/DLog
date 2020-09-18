package com.duode.loglibrary

import com.duode.loglibrary.listener.LogAdapter
import com.duode.loglibrary.utils.LogInfoUtils

/**
 * @author hekang
 * @des 执行日志的策略
 * @date 2020/8/27 14:48
 */
internal class LogPrintHelper {

  private val mLogAdapters = ArrayList<LogAdapter>()

  /**
   * 添加更多的日志适配器
   * */
  fun addAdapters(vararg adapter: LogAdapter) {
    adapter.forEach {
      mLogAdapters.add(it)
    }
  }

  /**
   * 清空日志适配器
   * */
  fun clearAdapters() {
    mLogAdapters.clear()
  }

  /**
   * 日志系统内部的使用方法
   * */
  fun log(globalTag: String, tag: String, flag: Int, logLevel: Int, msg: String) {
    //防止日志的打印顺序错乱
    synchronized(this) {
      val logInfo = LogInfoUtils.buildLogInfo(System.currentTimeMillis(), globalTag, tag, flag, logLevel, msg)
      mLogAdapters.forEach {
        if (it.isEnable(logLevel, tag, flag)) {
          it.log(logInfo)
        }
      }
    }
  }

}