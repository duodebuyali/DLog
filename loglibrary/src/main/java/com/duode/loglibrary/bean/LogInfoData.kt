package com.duode.loglibrary.bean

import android.os.Parcel
import android.os.Parcelable
import com.duode.loglibrary.consts.LogConst

/**
 * @author hekang
 * @des 用来描述需要处理的日志信息的对象
 *
 * @param flag 日志的标识，可以用来确定此条日志生效的logAdapter
 * @param globalTag 全局tag
 * @param selfTag 当前tga
 * @param msg 日志信息
 * @param logLevel 日志等级
 * @param fileName 日志打印方法被调用的文件名
 * @param className 日志打印方法被调用的类名
 * @param methodName 日志打印方法被调用的方法名
 * @param threadName 日志打印方法被调用的线程名
 * @param logPosition 日志打印方法被调用的行数
 * @param mills 日志打印方法被调用的毫秒数
 *
 * @date 2020/8/13 17:49
 */
data class LogInfoData(var flag: Int, var globalTag: String, var selfTag: String, var msg: String, var logLevel: Int,
                       var fileName: String, var className: String, var methodName: String, var threadName: String,
                       var logPosition: String, var mills: Long) : Parcelable {
  constructor(source: Parcel) : this(
      source.readInt(),
      source.readString() ?: LogConst.DEFAULT_TAG_GLOBAL,
      source.readString() ?: LogConst.DEFAULT_TAG_SELF,
      source.readString() ?: "",
      source.readInt(),
      source.readString() ?: "",
      source.readString() ?: "",
      source.readString() ?: "",
      source.readString() ?: "",
      source.readString() ?: "",
      source.readLong()
  )

  override fun describeContents() = 0

  override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
    writeInt(flag)
    writeString(globalTag)
    writeString(selfTag)
    writeString(msg)
    writeInt(logLevel)
    writeString(fileName)
    writeString(className)
    writeString(methodName)
    writeString(threadName)
    writeString(logPosition)
    writeLong(mills)
  }

  override fun toString(): String {
    return "LogInfoData(flag=$flag, globalTag='$globalTag', selfTag='$selfTag', msg='$msg', logLevel=$logLevel, fileName='$fileName', className='$className', methodName='$methodName', threadName='$threadName', logPosition='$logPosition', mills=$mills)"
  }

  companion object {
    @JvmField val CREATOR: Parcelable.Creator<LogInfoData> = object : Parcelable.Creator<LogInfoData> {
      override fun createFromParcel(source: Parcel): LogInfoData = LogInfoData(source)
      override fun newArray(size: Int): Array<LogInfoData?> = arrayOfNulls(size)
    }
  }
}