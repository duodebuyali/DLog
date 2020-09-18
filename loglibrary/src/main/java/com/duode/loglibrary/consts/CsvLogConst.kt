package com.duode.loglibrary.consts

/**
 * @author hekang
 * @des
 * @date 2020/9/9 15:15
 */
object CsvLogConst {
  /**
   * 对应系统的换行符
   * */
  val NEW_LINE = System.getProperty("line.separator") ?: "\n"

  /**
   * 用来替换换行符的字符串
   * */
  const val NEW_LINE_REPLACEMENT = " <br> "

  /**
   * 一条数据之间的分隔符
   * */
  const val SEPARATOR = ","

  /**
   * 日期的展示格式
   * */
  const val DATE_FORMAT = "yyyy/MM/dd HH:mm:ss.SSS"
}