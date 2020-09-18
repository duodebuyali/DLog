package com.duode.loglibrary.adapter

import android.util.Log
import com.duode.loglibrary.bean.LogInfoData
import com.duode.loglibrary.consts.AndroidLogConst
import com.duode.loglibrary.listener.LogAdapter
import com.duode.loglibrary.utils.LogInfoUtils

/**
 * @author hekang
 * @des 默认的Android风格的日志打印
 * @date 2020/8/27 15:58
 */
class AndroidLogAdapter(private val isDebug: Boolean = true) : LogAdapter {

  override fun isEnable(logLevel: Int, selfTag: String, flag: Int): Boolean {
    return isDebug
  }

  override fun log(logInfo: LogInfoData) {
    val tag = LogInfoUtils.formatTag(logInfo.globalTag, logInfo.selfTag)
    val level = logInfo.logLevel
    logTopBorder(level, tag)
    logThreadInfo(level, tag)
    logContentInfo(tag, logInfo)

    //判断需要打印的信息的字节长度
    val bytes: ByteArray = logInfo.msg.toByteArray()
    val length = bytes.size
    var i = 0
    while (i < length) {
      val count: Int = Math.min(length - i, AndroidLogConst.CHUNK_SIZE)
      //创建一个新的字符串，使用android系统默认的UTF_8格式
      logMsg(level, tag, String(bytes, i, count))
      i += AndroidLogConst.CHUNK_SIZE
    }

    logBottomBorder(level, tag)
  }


  private fun logTopBorder(logType: Int, tag: String) {
    logChunk(logType, tag, AndroidLogConst.TOP_BORDER)
  }

  private fun logDivider(logType: Int, tag: String) {
    logChunk(logType, tag, AndroidLogConst.MIDDLE_BORDER)
  }

  private fun logThreadInfo(logType: Int, tag: String) {
    logChunk(logType, tag, AndroidLogConst.HORIZONTAL_LINE.toString() + " Thread: " + Thread.currentThread().name)
    logDivider(logType, tag)
  }

  /**
   * 打印 文件名 类名 方法名 位置 等信息
   * */
  private fun logContentInfo(tag: String, info: LogInfoData) {
    val builder = StringBuilder()
    builder.append(AndroidLogConst.HORIZONTAL_LINE)
        .append(' ')
        //这里的类名 是否只显示 简称
        .append(info.className)
        .append(".")
        .append(info.methodName)
        .append(" ")
        .append(" (")
        .append(info.fileName)
        .append(":")
        .append(info.logPosition)
        .append(")")
    logChunk(info.logLevel, tag, builder.toString())
    logDivider(info.logLevel, tag)
  }

  /**
   * 以系统的 System.getProperty("line.separator") 作为终止，打印msg
   * */
  private fun logMsg(logType: Int, tag: String, msg: String) {
    // 获取操作系统对应的换行符
    val splitStr = System.getProperty("line.separator") ?: "\n"
    val lines = msg.split(splitStr)
    for (line in lines) {
      logChunk(logType, tag, AndroidLogConst.HORIZONTAL_LINE.toString() + " " + line)
    }
  }

  private fun logBottomBorder(logType: Int, tag: String) {
    logChunk(logType, tag, AndroidLogConst.BOTTOM_BORDER)
  }

  /**
   * 打印日志
   * */
  private fun logChunk(level: Int, tag: String, message: String) {
    Log.println(level, tag, message)
  }
}