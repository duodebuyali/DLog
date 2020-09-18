package com.duode.loglibrary.listener

import com.duode.loglibrary.bean.LogInfoData

/**
 * @author hekang
 * @des 日志适配器需要实现的方法
 * @date 2020/8/5 11:22
 */
interface LogAdapter {

  /**
   * @param logLevel 用来确认日志的等级
   * @param selfTag 用来确认日志的本次的tag，可以用来辨识是否让此条日志生效
   * @param flag 用来确认日志的本次的flag，可以用来辨识是否让此条日志生效
   * 是否生效，用来配置在发布时是否生效
   * */
  fun isEnable(logLevel: Int, selfTag: String, flag: Int): Boolean

  /**
   * @param logInfo 日志打印的信息
   * */
  fun log(logInfo: LogInfoData)
}