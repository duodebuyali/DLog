package com.duode.loglibrary.utils

import android.os.Handler
import android.os.Looper
import android.os.Message
import java.io.File
import java.io.FileWriter
import java.io.IOException

/**
 * @author hekang
 * @des 用来处理文件读写操作
 * @date 2020/9/9 15:42
 */
internal open class WriteHandler(looper: Looper, private val filePath: String) : Handler(looper) {

  override fun handleMessage(msg: Message) {
    val content = msg.obj as String
    var fileWriter: FileWriter? = null
    //判断文件是否存在或文件的上层文件夹是否已经创建，属于外部判断；是否有权限也是外部授权
    val logFile = File(filePath)
    try {
      fileWriter = FileWriter(logFile, true)
      fileWriter.append(content)

      fileWriter.flush()
      fileWriter.close()
    } catch (e: Exception) {
      e.printStackTrace()
      if (fileWriter != null) {
        try {
          fileWriter.flush()
          fileWriter.close()
        } catch (e1: IOException) {
          e1.printStackTrace()
        }
      }
    }
  }
}