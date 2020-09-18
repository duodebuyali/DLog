package com.duode.log.logconsole.db

import androidx.room.Dao
import androidx.room.Query
import com.duode.log.logconsole.consts.LogInfoDbConst
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * @author hekang
 * @des 用来操作日志信息表
 * @date 2020/9/10 10:01
 *
 */
@Dao
interface LogInfoDao : IBaseDao<LogInfoTable> {

    /**
     * @return 查询所有日志，以 mills 进行倒序
     * */
    @Query(value = "select * from ${LogInfoDbConst.TABLE_NAME_LOG_INFO} order by mills desc")
    fun queryAll(): Flowable<MutableList<LogInfoTable>>

    @Query(value = "select * from ${LogInfoDbConst.TABLE_NAME_LOG_INFO} where _id in (:ids) order by _id")
    fun queryListById(vararg ids: Long): Flowable<MutableList<LogInfoTable>>

    /**
     * @param num 限制需要查询的条数
     * */
    @Query(value = "select * from ${LogInfoDbConst.TABLE_NAME_LOG_INFO} where globalTag is :globalTag order by _id limit :num")
    fun queryListByTag(globalTag: String, num: Int): Flowable<MutableList<LogInfoTable>>

    /**
     * @return 查询 指定 globalTag 和 selfTag 的日志，以 mills 进行倒序
     * */
    @Query(value = "select * from ${LogInfoDbConst.TABLE_NAME_LOG_INFO} where globalTag is :globalTag AND selfTag like :selfTag order by mills desc")
    fun queryListByTag(globalTag: String, selfTag: String): Flowable<MutableList<LogInfoTable>>

    /**
     * @param classNameSuffix 类名的后缀，一般是不含包名的简单类名；需要在前面添加通配符 %
     *
     * @return 查询 指定 className 的日志，以 mills 进行倒序
     * */
    @Query(value = "select * from ${LogInfoDbConst.TABLE_NAME_LOG_INFO} where className like :classNameSuffix order by mills desc")
    fun queryListByClassName(classNameSuffix: String): Flowable<MutableList<LogInfoTable>>

    /**
     * @return 查询 指定 fileName 的日志，以 mills 进行倒序
     * */
    @Query(value = "select * from ${LogInfoDbConst.TABLE_NAME_LOG_INFO} where fileName == :fileName order by mills desc")
    fun queryListByFileName(fileName: String): Flowable<MutableList<LogInfoTable>>


    /**
     * 将配置信息中的 所有参数都写上，这样即可以满足所有的组合条件
     * @return 返回指定条件的数据 以 mills 进行倒序
     * */
    @Query(
        value = "select * from ${LogInfoDbConst.TABLE_NAME_LOG_INFO} where flag like :queryFlag and globalTag like :queryGlobalTag " +
                "and selfTag like :querySelfTag and fileName like :queryFileName and className like :queryClassName  and methodName like :queryMethodName order by mills desc"
    )
    fun queryList(
        queryFlag: String,
        queryGlobalTag: String,
        querySelfTag: String,
        queryFileName: String,
        queryClassName: String,
        queryMethodName: String
    ): Flowable<MutableList<LogInfoTable>>

    /**
     * 删除所有数据
     * @return 返回删除的行数
     */
    @Query("DELETE FROM ${LogInfoDbConst.TABLE_NAME_LOG_INFO}")
    fun deleteAll(): Single<Int>

    @Query("DELETE FROM ${LogInfoDbConst.TABLE_NAME_LOG_INFO} WHERE selfTag IS :selfTag")
    fun deleteByTag(selfTag: String): Single<Int>

    /**
     * 删除 指定 className 的日志，以 mills 进行倒序
     * @param classNameSuffix 类名的后缀，一般是不含包名的简单类名；需要在前面添加通配符 %
     * @return 删除的行数
     * */
    @Query("DELETE FROM ${LogInfoDbConst.TABLE_NAME_LOG_INFO} WHERE className like :classNameSuffix")
    fun deleteByClassName(classNameSuffix: String): Single<Int>

}