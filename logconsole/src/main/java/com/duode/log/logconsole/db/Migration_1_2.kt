package com.duode.log.logconsole.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.duode.log.logconsole.consts.LogInfoDbConst

/**
 * @author hekang
 * @des 数据升级时需要添加的配置
 * @date 2020/9/10 14:24
 *
 * 数据库版本 1->2 LogInfoTable新增了remarkDes列,类型TEXT(字符串)：备注信息
 */
internal object Migration_1_2 : Migration(1, 2) {
  override fun migrate(database: SupportSQLiteDatabase) {
    database.execSQL("ALTER TABLE ${LogInfoDbConst.TABLE_NAME_LOG_INFO} ADD COLUMN remarkDes TEXT")
  }
}

