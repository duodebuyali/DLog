package com.duode.log.logconsole.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.duode.log.logconsole.consts.LogInfoDbConst
import com.duode.loglibrary.bean.LogInfoData

/**
 * @author hekang
 * @des 日志信息表；
 * 数据的表单以table结尾，用来和服务器数据(以bean结尾)以及仅本地数据使用(以data结尾)作区分
 * @date 2020/9/9 19:16
 *
 * @param id 用来作为唯一键；其他数据的含义参考:
 * @see LogInfoData
 */
@Entity(tableName = LogInfoDbConst.TABLE_NAME_LOG_INFO)
data class LogInfoTable(@PrimaryKey(autoGenerate = true)
                        @ColumnInfo(name = "_id", index = true)
                        var id: Long,
                        @ColumnInfo(name = "flag")
                        var flag: Int,
                        var globalTag: String,
                        var selfTag: String,
                        var msg: String,
                        var logLevel: Int,
                        var fileName: String,
                        var className: String,
                        var methodName: String,
                        var threadName: String,
                        var logPosition: String,
                        var mills: Long) {

  /**
   * _id默认值给0，表示数据库中不存在
   * */
  constructor(flag: Int,
              globalTag: String,
              selfTag: String,
              msg: String,
              logLevel: Int,
              fileName: String,
              className: String,
              methodName: String,
              threadName: String,
              logPosition: String,
              mills: Long) : this(0, flag, globalTag, selfTag, msg, logLevel, fileName, className, methodName, threadName, logPosition, mills)
}