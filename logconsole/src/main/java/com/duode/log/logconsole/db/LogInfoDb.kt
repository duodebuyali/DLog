package com.duode.log.logconsole.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.duode.log.logconsole.consts.LogInfoDbConst


/**
 * @author hekang
 * @des 实现日志数据库
 * 参考: https://blog.csdn.net/u014620028/article/details/90719716
 * @date 2020/9/9 19:06
 *
 * exportSchema 表示是否将数据库配置导出为json文件，导出的地址在build.gradle中配置
 */
@Database(
    entities = [LogInfoTable::class],
    version = LogInfoDbConst.DB_VERSION_CODE,
    exportSchema = false
)
abstract class LogInfoDb : RoomDatabase() {

    /**
     * 用来实现单例
     * */
    private object SingleInstance {
        fun getInstance(ctx: Context): LogInfoDb {
            return Room.databaseBuilder(
                ctx.applicationContext,
                LogInfoDb::class.java,
                LogInfoDbConst.DB_NAME
            )
//          .allowMainThreadQueries()//允许主线程进行查询操作
//          .addMigrations(Migration_1_2)//配置数据库升级
                .build()
        }

    }

    companion object {
        /**
         * 暴露方法给外部调用
         * */
        fun getInstance(ctx: Context): LogInfoDb {
            return SingleInstance.getInstance(ctx)
        }
    }

    /**
     * 返回日志信息表的操作dao层
     * */
    abstract fun logInfoDao(): LogInfoDao

}
