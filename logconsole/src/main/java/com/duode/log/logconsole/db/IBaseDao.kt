package com.duode.log.logconsole.db

import androidx.room.*

/**
 * @author hekang
 * @des 封装所有dao层都应该实现的基础方法
 * @date 2020/9/10 10:02
 *
 * 需要确认是否支持该写法，查看
 * @see EntityInsertionAdapter
 * @see EntityDeletionOrUpdateAdapter
 * TODO：2020年09月14日  如果是使用自动生成的代码和rxJava绑定：返回值为行数的只能使用Single；为插入多个的结果的只能使用Completable，如果需要返回插入的id，使用Maybe
 */
interface IBaseDao<T> {

    /**
     * onConflict：表示当插入有冲突的时候的处理策略。OnConflictStrategy封装了Room解决冲突的相关策略：
     * OnConflictStrategy.REPLACE：冲突策略是取代旧数据同时继续事务。
     * OnConflictStrategy.ROLLBACK：冲突策略是回滚事务。
     * OnConflictStrategy.ABORT：冲突策略是终止事务。默认策略
     * OnConflictStrategy.FAIL：冲突策略是事务失败。
     * OnConflictStrategy.IGNORE：冲突策略是忽略冲突。
     *
     * @return 返回插入的结果
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg element: T)

    /**
     * @return 返回插入的数据的_id
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReturnId(vararg element: T): LongArray


    /**
     * @return  表示删除是否成功
     * */
    @Delete
    suspend fun delete(vararg element: T)

    /**
     * @return  表示删除成功了多少行
     * */
    @Delete
    suspend fun deleteReturnNum(vararg element: T): Int


    /**
     * @return  表示更新是否成功
     * */
    @Update
    suspend fun update(vararg element: T)

    /**
     * @return  表示更新成功了多少行
     * */
    @Update
    suspend fun updateReturnNum(vararg element: T): Int

}